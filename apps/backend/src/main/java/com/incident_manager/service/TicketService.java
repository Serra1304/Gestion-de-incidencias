package com.incident_manager.service;

import com.incident_manager.Exception.BadRequestException;
import com.incident_manager.Exception.ResourceNotFoundException;
import com.incident_manager.entity.*;
import com.incident_manager.repository.AuthUserRepository;
import com.incident_manager.repository.TicketRepository;
import com.incident_manager.repository.WorkGroupRepository;
import com.incident_manager.service.command.CreateTicketCommand;
import com.incident_manager.service.command.UpdateTicketCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Application service responsible for managing incident tickets.
 *
 * <p>This service coordinates the creation, update, retrieval and deletion of tickets,
 * handling ticket lifecycle, status transitions, and user assignments.
 *
 * <p>Main responsibilities:
 * <ul>
 *   <li>Enforce business rules (valid status transitions, assignee group membership)</li>
 *   <li>Handle ticket creation with reporter and assignee tracking</li>
 *   <li>Manage transactional consistency for ticket operations</li>
 *   <li>Send notifications for ticket events</li>
 * </ul>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final AuthUserRepository userRepository;
    private final WorkGroupRepository workGroupRepository;
    private final TicketRepository ticketRepository;
    private final NotificationService notificationService;

    /**
     * Creates a new incident ticket.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Reporter email must exist</li>
     *   <li>Work group must exist</li>
     *   <li>If assigned, assignee must belong to the specified work group</li>
     *   <li>Ticket is created with OPEN status</li>
     * </ul>
     *
     * @param cmd command containing ticket data
     * @param reporterEmail email of the user reporting the ticket
     * @return persisted {@link Ticket}
     *
     * @throws ResourceNotFoundException if reporter or work group does not exist
     * @throws BadRequestException if assignee does not belong to the work group
     */
    /* CREATE */

    public Ticket create(CreateTicketCommand cmd, String reporterEmail) {

        AuthUser reporter = userRepository.findByEmail(reporterEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        WorkGroup workGroup = workGroupRepository.findById(cmd.workGroupId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("WorkGroup not found"));

        AuthUser assignee = resolveAssignee(cmd.assigneeId(), workGroup);

        Ticket ticket = new Ticket();
        ticket.setTitle(cmd.title());
        ticket.setDescription(cmd.description());
        ticket.setPriority(cmd.priority());
        ticket.setReporter(reporter);
        ticket.setAssignee(assignee);
        ticket.setWorkGroup(workGroup);
        ticket.setStatus(TicketStatus.OPEN);

        Ticket savedTicket = ticketRepository.save(ticket);

        notificationService.notifyTicketCreated(savedTicket);

        return savedTicket;
    }

    /**
     * Updates an existing ticket.
     *
     * <p>Only non-null fields in the command are applied.
     *
     * <p>Business rules:
     * <ul>
     *   <li>If assignee is updated, must belong to the ticket's work group</li>
     * </ul>
     *
     * @param cmd command containing fields to update
     * @return updated {@link Ticket}
     *
     * @throws ResourceNotFoundException if the ticket does not exist
     * @throws BadRequestException if new assignee does not belong to the work group
     */
    /* UPDATE */

    public Ticket update(UpdateTicketCommand cmd) {

        Ticket ticket = ticketRepository.findById(cmd.ticketId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ticket not found"));

        if (cmd.title() != null)
            ticket.setTitle(cmd.title());

        if (cmd.description() != null)
            ticket.setDescription(cmd.description());

        if (cmd.priority() != null)
            ticket.setPriority(cmd.priority());

        if (cmd.assigneeId() != null) {
            AuthUser assignee =
                    resolveAssignee(cmd.assigneeId(), ticket.getWorkGroup());
            ticket.setAssignee(assignee);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);

        notificationService.notifyTicketUpdated(updatedTicket);

        return updatedTicket;
    }

    /**
     * Changes the status of a ticket.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Closed tickets cannot change status</li>
     *   <li>Status cannot change to its current value</li>
     * </ul>
     *
     * @param ticketId ticket identifier
     * @param status new status
     * @return updated {@link Ticket}
     *
     * @throws ResourceNotFoundException if the ticket does not exist
     * @throws BadRequestException if status transition is invalid
     */
    /* STATUS */

    public Ticket changeStatus(UUID ticketId, TicketStatus status) {

        Ticket ticket = getTicket(ticketId);

        validateStatusTransition(ticket.getStatus(), status);

        ticket.setStatus(status);

        Ticket updatedTicket = ticketRepository.save(ticket);

        notificationService.notifyTicketStatusChanged(updatedTicket);

        return updatedTicket;
    }

    /**
     * Assigns or reassigns a ticket to a user.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Assignee must belong to the ticket's work group</li>
     * </ul>
     *
     * @param ticketId ticket identifier
     * @param userId user identifier
     * @return updated {@link Ticket}
     *
     * @throws ResourceNotFoundException if ticket or user does not exist
     * @throws BadRequestException if user does not belong to the work group
     */
    /* ASSIGNMENT */

    public Ticket assignTicket(UUID ticketId, UUID userId) {

        Ticket ticket = getTicket(ticketId);

        AuthUser assignee =
                resolveAssignee(userId, ticket.getWorkGroup());

        ticket.setAssignee(assignee);

        Ticket updatedTicket = ticketRepository.save(ticket);

        notificationService.notifyTicketAssigned(updatedTicket);

        return updatedTicket;
    }

    /**
     * Deletes a ticket by id.
     *
     * @param ticketId ticket identifier
     *
     * @throws ResourceNotFoundException if the ticket does not exist
     */
    /* DELETE */

    public void deleteTicket(UUID ticketId) {
        Ticket ticket = getTicket(ticketId);
        ticketRepository.delete(ticket);
    }

    /**
     * Retrieves a ticket by its identifier.
     *
     * @param ticketId ticket identifier
     * @return {@link Ticket}
     *
     * @throws ResourceNotFoundException if the ticket does not exist
     */
    /* QUERIES */

    @Transactional(readOnly = true)
    public Ticket getTicket(UUID ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ticket not found"));
    }

    /**
     * Retrieves all tickets in the system.
     *
     * @return list of {@link Ticket}
     */
    @Transactional(readOnly = true)
    public List<Ticket> listTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Resolves the assignee for a ticket within a work group.
     *
     * <p>Business rules:
     * <ul>
     *   <li>If assigneeId is null, returns null (unassigned)</li>
     *   <li>Assignee must belong to the specified work group</li>
     * </ul>
     *
     * @param assigneeId user identifier or null
     * @param group work group where user must belong
     * @return {@link AuthUser} or null if assigneeId is null
     *
     * @throws ResourceNotFoundException if user does not exist
     * @throws BadRequestException if user does not belong to the work group
     */
    /* PRIVATE HELPERS */

    private AuthUser resolveAssignee(UUID assigneeId, WorkGroup group) {

        if (assigneeId == null) return null;

        AuthUser assignee = userRepository.findById(assigneeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Assignee not found"));

        if (!group.getUsers().contains(assignee)) {
            throw new BadRequestException(
                    "User does not belong to the work group");
        }

        return assignee;
    }

    /**
     * Validates a ticket status transition.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Closed tickets cannot change status</li>
     *   <li>Status cannot remain the same</li>
     * </ul>
     *
     * @param current current ticket status
     * @param next desired ticket status
     *
     * @throws BadRequestException if transition is invalid
     */
    private void validateStatusTransition(
            TicketStatus current,
            TicketStatus next) {

        if (current == TicketStatus.CLOSED) {
            throw new BadRequestException(
                    "Closed tickets cannot change status");
        }

        if (current == next) {
            throw new BadRequestException(
                    "Ticket already has this status");
        }
    }
}
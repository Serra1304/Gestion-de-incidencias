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

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final AuthUserRepository userRepository;
    private final WorkGroupRepository workGroupRepository;
    private final TicketRepository ticketRepository;

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

        return ticketRepository.save(ticket);
    }

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

        return ticketRepository.save(ticket);
    }

    /* STATUS */

    public Ticket changeStatus(UUID ticketId, TicketStatus status) {

        Ticket ticket = getTicket(ticketId);

        validateStatusTransition(ticket.getStatus(), status);

        ticket.setStatus(status);

        return ticketRepository.save(ticket);
    }

    /* ASSIGNMENT */

    public Ticket assignTicket(UUID ticketId, UUID userId) {

        Ticket ticket = getTicket(ticketId);

        AuthUser assignee =
                resolveAssignee(userId, ticket.getWorkGroup());

        ticket.setAssignee(assignee);

        return ticketRepository.save(ticket);
    }

    /* DELETE */

    public void deleteTicket(UUID ticketId) {
        Ticket ticket = getTicket(ticketId);
        ticketRepository.delete(ticket);
    }

    /* QUERIES */

    @Transactional(readOnly = true)
    public Ticket getTicket(UUID ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ticket not found"));
    }

    @Transactional(readOnly = true)
    public List<Ticket> listTickets() {
        return ticketRepository.findAll();
    }

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
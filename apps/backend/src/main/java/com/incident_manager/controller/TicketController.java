package com.incident_manager.controller;

import com.incident_manager.DTO.ticket.TicketCreateDTO;
import com.incident_manager.DTO.ticket.TicketResponseDTO;
import com.incident_manager.DTO.ticket.TicketUpdateDTO;
import com.incident_manager.entity.Ticket;
import com.incident_manager.mapper.TicketMapper;
import com.incident_manager.service.TicketService;
import com.incident_manager.service.command.CreateTicketCommand;
import com.incident_manager.service.command.UpdateTicketCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(
            @Valid @RequestBody TicketCreateDTO dto,
            Authentication authentication
    ) {

        String userEmail = authentication.getName();

        CreateTicketCommand command =
                ticketMapper.toCreateTicketCommand(dto);

        Ticket ticket = ticketService.create(command, userEmail);

        TicketResponseDTO response =
                ticketMapper.toTicketResponseDTO(ticket);

        return ResponseEntity
                .created(URI.create("/api/tickets/" + response.id()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAllTicket () {
        List<Ticket> tickets = ticketService.listTickets();

        return ResponseEntity.ok(tickets
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList()
        );
    }

    @GetMapping(value = "/{ticketId}")
    public ResponseEntity<TicketResponseDTO> getTicket (
            @PathVariable UUID ticketId
            ) {
        Ticket ticket = ticketService.getTicket(ticketId);

        return ResponseEntity
                .ok(ticketMapper.toTicketResponseDTO(ticket));
    }

    @PutMapping(value = "/{ticketId}")
    public ResponseEntity<TicketResponseDTO> updateTicket(
            @PathVariable UUID ticketId,
            @Valid @RequestBody TicketUpdateDTO dto
    ) {
        UpdateTicketCommand cmd = ticketMapper.toUpdateTicketCommand(ticketId, dto);
        Ticket ticket = ticketService.update(cmd);

        return ResponseEntity.ok(ticketMapper.toTicketResponseDTO(ticket));
    }

    @DeleteMapping(value = "/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID ticketId) {
        ticketService.deleteTicket(ticketId);

        return ResponseEntity.noContent().build();
    }
}

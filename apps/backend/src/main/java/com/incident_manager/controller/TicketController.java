package com.incident_manager.controller;

import com.incident_manager.DTO.ticket.TicketCreateDTO;
import com.incident_manager.DTO.ticket.TicketResponseDTO;
import com.incident_manager.DTO.ticket.TicketUpdateDTO;
import com.incident_manager.entity.Ticket;
import com.incident_manager.mapper.TicketMapper;
import com.incident_manager.service.TicketService;
import com.incident_manager.service.command.CreateTicketCommand;
import com.incident_manager.service.command.UpdateTicketCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(
        name = "Tickets",
        description = "Incident ticket management"
)
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @Operation(
            summary = "Create new ticket",
            description = "Creates a new incident ticket in the system using the provided data"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Ticket created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    "application/problem+json"
            }
    )
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

    @Operation(
            summary = "Get all tickets",
            description = "Returns a list of all incident tickets in the system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tickets retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<TicketResponseDTO>> getAllTicket () {
        List<Ticket> tickets = ticketService.listTickets();

        return ResponseEntity.ok(tickets
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList()
        );
    }

    @Operation(
            summary = "Get ticket by ID",
            description = "Returns full ticket details for the given ticket identifier"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameter",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping(
            value = "/{ticketId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TicketResponseDTO> getTicket (
            @Parameter(
                    description = "Unique identifier of the ticket",
                    example = "c1a7a3d2-9e42-4b9f-bf61-9e3c6c0d9b21",
                    required = true
            )
            @PathVariable UUID ticketId
            ) {
        Ticket ticket = ticketService.getTicket(ticketId);

        return ResponseEntity
                .ok(ticketMapper.toTicketResponseDTO(ticket));
    }

    @Operation(
            summary = "Update ticket",
            description = "Updates an existing ticket. Only the provided fields will be modified."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PutMapping(
            value = "/{ticketId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    "application/problem+json"
            }
    )
    public ResponseEntity<TicketResponseDTO> updateTicket(
            @Parameter(
                    description = "Unique identifier of the ticket",
                    required = true
            )
            @PathVariable UUID ticketId,
            @Valid @RequestBody TicketUpdateDTO dto
    ) {
        UpdateTicketCommand cmd = ticketMapper.toUpdateTicketCommand(ticketId, dto);
        Ticket ticket = ticketService.update(cmd);

        return ResponseEntity.ok(ticketMapper.toTicketResponseDTO(ticket));
    }

    @Operation(
            summary = "Delete ticket",
            description = "Deletes a ticket identified by the given identifier"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Ticket deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ticket identifier",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(
            @Parameter(
                    description = "Unique identifier of the ticket to delete",
                    example = "c1a7a3d2-9e42-4b9f-bf61-9e3c6c0d9b21",
                    required = true
            )
            @PathVariable UUID ticketId
    ) {
        ticketService.deleteTicket(ticketId);

        return ResponseEntity.noContent().build();
    }
}

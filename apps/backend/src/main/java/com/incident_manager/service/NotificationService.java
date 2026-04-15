package com.incident_manager.service;

import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;
    private final SimpMessagingTemplate messagingTemplate;

    @Async
    public void sendEmailNotification(UserProfile user, String subject, String message) {
        if (user.getEmailNotificationsEnabled() != null && user.getEmailNotificationsEnabled()) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getAuthUser().getEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailSender.send(mailMessage);
        }
    }

    @Async
    public void sendInAppNotification(UserProfile user, String message) {
        if (user.getInAppNotificationsEnabled() != null && user.getInAppNotificationsEnabled()) {
            messagingTemplate.convertAndSendToUser(user.getAuthUser().getEmail(), "/queue/notifications", message);
        }
    }

    @Async
    public void sendNotification(UserProfile user, String subject, String message) {
        sendEmailNotification(user, subject, message);
        sendInAppNotification(user, message);
    }

    @Async
    public void notifyTicketCreated(Ticket ticket) {
        String subject = "Nuevo ticket creado: " + ticket.getTitle();
        String message = "Se ha creado un nuevo ticket: " + ticket.getTitle() + ". Descripción: " + ticket.getDescription();

        // Notify reporter
        if (ticket.getReporter() != null && ticket.getReporter().getProfile() != null) {
            sendNotification(ticket.getReporter().getProfile(), subject, message);
        }

        // Notify assignee
        if (ticket.getAssignee() != null && ticket.getAssignee().getProfile() != null) {
            sendNotification(ticket.getAssignee().getProfile(), "Ticket asignado: " + ticket.getTitle(), "Se te ha asignado el ticket: " + ticket.getTitle());
        }

        // Notify work group members
        if (ticket.getWorkGroup() != null) {
            for (AuthUser user : ticket.getWorkGroup().getUsers()) {
                if (user.getProfile() != null && !user.equals(ticket.getReporter()) && !user.equals(ticket.getAssignee())) {
                    sendNotification(user.getProfile(), subject, message);
                }
            }
        }
    }

    @Async
    public void notifyTicketUpdated(Ticket ticket) {
        String subject = "Ticket actualizado: " + ticket.getTitle();
        String message = "El ticket '" + ticket.getTitle() + "' ha sido actualizado.";

        // Notify assignee
        if (ticket.getAssignee() != null && ticket.getAssignee().getProfile() != null) {
            sendNotification(ticket.getAssignee().getProfile(), subject, message);
        }

        // Notify reporter
        if (ticket.getReporter() != null && ticket.getReporter().getProfile() != null) {
            sendNotification(ticket.getReporter().getProfile(), subject, message);
        }
    }

    @Async
    public void notifyTicketStatusChanged(Ticket ticket) {
        String subject = "Estado del ticket cambiado: " + ticket.getTitle();
        String message = "El estado del ticket '" + ticket.getTitle() + "' ha cambiado a " + ticket.getStatus();

        // Notify assignee
        if (ticket.getAssignee() != null && ticket.getAssignee().getProfile() != null) {
            sendNotification(ticket.getAssignee().getProfile(), subject, message);
        }

        // Notify reporter
        if (ticket.getReporter() != null && ticket.getReporter().getProfile() != null) {
            sendNotification(ticket.getReporter().getProfile(), subject, message);
        }
    }

    @Async
    public void notifyTicketAssigned(Ticket ticket) {
        String subject = "Ticket asignado: " + ticket.getTitle();
        String message = "Se te ha asignado el ticket: " + ticket.getTitle();

        // Notify new assignee
        if (ticket.getAssignee() != null && ticket.getAssignee().getProfile() != null) {
            sendNotification(ticket.getAssignee().getProfile(), subject, message);
        }
    }
}

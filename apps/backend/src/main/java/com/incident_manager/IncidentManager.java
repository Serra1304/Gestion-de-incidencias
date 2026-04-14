package com.incident_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application entry point for the Incident Manager system.
 *
 * <p>A Spring Boot application for managing incident tickets, users, and work groups.
 * Provides RESTful API endpoints for ticket lifecycle management and user administration.
 *
 * <p>Features:
 * <ul>
 *   <li>JWT-based authentication and authorization</li>
 *   <li>Comprehensive incident ticket management</li>
 *   <li>User and work group administration</li>
 *   <li>Audit trail with timestamps</li>
 *   <li>RESTful API with OpenAPI/Swagger documentation</li>
 * </ul>
 */
@SpringBootApplication
public class IncidentManager {

	/**
	 * Main application entry point.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(IncidentManager.class, args);
	}
}

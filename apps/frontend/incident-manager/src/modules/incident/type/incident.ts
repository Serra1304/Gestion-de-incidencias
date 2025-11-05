import { IncidentPriorityOption } from "./incidentPriority";
import { IncidentStatusOption } from "./incidentStatus";

/**
 * Representa una incidencia del sistema.
 */
export type Incident = {
  /** Identificador único de la incidencia */
  id: string;

  /** Título breve o resumen del problema */
  title: string;

  /** Descripción detallada del problema reportado */
  description: string;

  /** Usuario que reportó la incidencia */
  reportedBy: string;

  /** Fecha y hora en que se registró la incidencia */
  reportedAt: Date;

  /** Prioridad de la incidencia */
  priority: IncidentPriorityOption["value"];

  /** Estado actual de la incidencia */
  status: IncidentStatusOption["value"];

  /** Usuario asignado para resolver la incidencia (opcional) */
  assignedTo?: string;

  /** Fecha y hora de última actualización (opcional) */
  updatedAt?: Date;

  /** Fecha y hora de resolución (opcional) */
  resolvedAt?: Date;
};

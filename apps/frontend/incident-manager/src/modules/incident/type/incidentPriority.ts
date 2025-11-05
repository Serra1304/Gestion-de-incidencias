// src/modules/incident/constants/incidentPriority.ts

/**
 * Representa una opción seleccionable en el ComboBox de prioridad de incidencias.
 */
export type IncidentPriorityOption = {
  value: string;
  label: string;
};

/**
 * Lista estática de prioridades de incidencia.
 * Cada elemento tiene un valor interno (`value`) y una etiqueta visible (`label`).
 */
export const INCIDENT_PRIORITY_OPTIONS: IncidentPriorityOption[] = [
  { value: "urgent", label: "Urgente" },
  { value: "high", label: "Alta" },
  { value: "medium", label: "Media" },
  { value: "low", label: "Baja" },
];

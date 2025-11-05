/**
 * Representa una opción seleccionable en el ComboBox de estado de incidencias.
 */
export type IncidentStatusOption = {
  value: string;
  label: string;
};

/**
 * Lista estática de estados de incidencia.
 * Cada elemento tiene un valor interno (`value`) y una etiqueta visible (`label`).
 */
export const INCIDENT_STATUS_OPTIONS: IncidentStatusOption[] = [
  { value: "open", label: "Abierta" },
  { value: "in_progress", label: "En progreso" },
  { value: "resolved", label: "Resuelta" },
  { value: "closed", label: "Cerrada" },
  { value: "cancelled", label: "Cancelada" },
];
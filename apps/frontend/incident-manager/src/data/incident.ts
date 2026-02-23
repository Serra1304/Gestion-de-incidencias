import type { Incident } from "@/modules/incident/type/incident";

// ------------------------------
// Datos simulados
// ------------------------------
const mockIncidents: Incident[] = [
  {
    id: "INC-001",
    title: "Fallo en el servidor principal",
    description:
      "El servidor principal dejó de responder tras la última actualización del sistema operativo.",
    reportedBy: "Juan Pérez",
    reportedAt: new Date("2025-10-01T09:15:00"),
    priority: "urgent",
    status: "open",
    assignedTo: "Laura Gómez",
  },
  {
    id: "INC-002",
    title: "Error en el módulo de autenticación",
    description:
      "Los usuarios no pueden iniciar sesión con credenciales válidas desde la app móvil.",
    reportedBy: "María López",
    reportedAt: new Date("2025-10-03T14:40:00"),
    priority: "high",
    status: "in_progress",
    assignedTo: "Carlos Díaz",
    updatedAt: new Date("2025-10-04T08:00:00"),
  },
  {
    id: "INC-003",
    title: "Problemas de rendimiento en el dashboard",
    description:
      "El dashboard tarda más de 10 segundos en cargar los gráficos después del último despliegue.",
    reportedBy: "Ana Torres",
    reportedAt: new Date("2025-10-05T10:20:00"),
    priority: "medium",
    status: "resolved",
    assignedTo: "Pedro Sánchez",
    updatedAt: new Date("2025-10-07T16:45:00"),
    resolvedAt: new Date("2025-10-07T16:30:00"),
  },
  {
    id: "INC-004",
    title: "Notificaciones por correo no se envían",
    description:
      "Los correos de confirmación no están llegando a los usuarios después de completar un formulario.",
    reportedBy: "Lucía Fernández",
    reportedAt: new Date("2025-10-09T12:00:00"),
    priority: "low",
    status: "closed",
    assignedTo: "Daniel Ruiz",
    updatedAt: new Date("2025-10-10T09:10:00"),
  },
  {
    id: "INC-005",
    title: "Incidencia de prueba cancelada",
    description:
      "Esta incidencia fue creada para pruebas internas y cancelada posteriormente.",
    reportedBy: "Administrador",
    reportedAt: new Date("2025-10-10T11:11:00"),
    priority: "low",
    status: "cancelled",
  },
];

// ------------------------------
// Funciones simuladas
// ------------------------------

/**
 * Simula una llamada asíncrona al backend para obtener todas las incidencias.
 * @returns Promesa con la lista de incidencias.
 */
export async function fetchIncidents(): Promise<Incident[]> {
  await new Promise((resolve) => setTimeout(resolve, 500)); // Simula retardo
  return mockIncidents;
}

/**
 * Simula la obtención de una incidencia por su ID.
 * @param id ID de la incidencia a obtener.
 * @returns Promesa con la incidencia o null si no existe.
 */
export async function fetchIncidentById(id: string): Promise<Incident | null> {
  const incidents = await fetchIncidents();
  const incident = incidents.find((i) => String(i.id) === String(id));
  await new Promise((r) => setTimeout(r, 300)); // Simula delay adicional
  return incident ?? null;
}

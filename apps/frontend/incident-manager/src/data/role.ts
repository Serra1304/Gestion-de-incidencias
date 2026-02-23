import type { Role } from "@/modules/role/type/role";

// ------------------------------
// Datos simulados
// ------------------------------
const mockRoles: Role[] = [
  {
    id: "1",
    name: "Administrador",
    description: "Tiene acceso total al sistema.",
    createdAt: "2024-10-01T10:00:00Z",
    updatedAt: "2024-10-05T14:32:00Z",
  },
  {
    id: "2",
    name: "Editor",
    description: "Puede modificar contenido y gestionar usuarios limitados.",
    createdAt: "2024-10-02T09:15:00Z",
    updatedAt: "2024-10-07T11:00:00Z",
  },
  {
    id: "3",
    name: "Visor",
    description: "Solo puede visualizar datos sin realizar cambios.",
    createdAt: "2024-10-03T08:30:00Z",
    updatedAt: "2024-10-08T17:20:00Z",
  },
];

// ------------------------------
// Funciones simuladas
// ------------------------------

/**
 * Simula una llamada asíncrona al backend para obtener todos los roles.
 * @returns Promesa con la lista de roles.
 */
export async function fetchRoles(): Promise<Role[]> {
  // simulamos un pequeño retardo como si viniera de una API
  await new Promise((resolve) => setTimeout(resolve, 500));
  return mockRoles;
}

/**
 * Simula la obtención de un rol por su ID.
 * @param id ID del rol a obtener.
 * @returns Promesa con el rol o null si no existe.
 */
export async function fetchRoleById(id: string): Promise<Role | null> {
  const roles = await fetchRoles();
  const role = roles.find((r) => String(r.id) === String(id));
  await new Promise((r) => setTimeout(r, 300)); // simula delay
  return role ?? null;
}

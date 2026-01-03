import type { GroupFull } from "@/modules/workGroup/type/groupFull";
import type { GroupListItem } from "@/modules/workGroup/type/groupListItem";
import type { UserInfo } from "@/modules/user/type/userInfo";
import { GroupSave } from "@/modules/workGroup/type/groupSave";

const API_BASE_URL =
  process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";

/* ============================================================
 * Helpers
 * ============================================================ */

async function apiRequest<T>(
  endpoint: string,
  options?: RequestInit
): Promise<T> {
  const res = await fetch(`${API_BASE_URL}${endpoint}`, {
    headers: {
      "Content-Type": "application/json",
    },
    cache: "no-store",
    ...options,
  });

  if (!res.ok) {
    throw new Error(`Error ${res.status}: ${res.statusText}`);
  }

  return res.json() as Promise<T>;
}

/* ============================================================
 * Groups
 * ============================================================ */

/** Obtener listado de grupos */
export function fetchGroups(): Promise<GroupListItem[]> {
  return apiRequest<GroupListItem[]>("/groups");
}

/** Obtener información básica de un grupo */
export function fetchGroup(groupId: string): Promise<GroupListItem> {
  return apiRequest<GroupListItem>(`/groups/${groupId}`);
}

/** Obtener información completa de un grupo */
export function fetchFullGroup(groupId: string): Promise<GroupFull> {
  return apiRequest<GroupFull>(`/groups/${groupId}/full`);
}

/** Crear grupo */
export function createGroup(
  payload: GroupSave
): Promise<GroupFull> {
  return apiRequest("/groups", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

/** Actualizar grupo */
export function updateGroup(
  groupId: string,
  payload: GroupSave
): Promise<GroupFull> {
  return apiRequest(`/groups/${groupId}`, {
    method: "PUT",
    body: JSON.stringify(payload),
  });
}

/** Eliminar grupo */
export function deleteGroup(groupId: string): Promise<void> {
  return apiRequest<void>(`/groups/${groupId}`, {
    method: "DELETE",
  });
}

/* ============================================================
 * Group users
 * ============================================================ */

/** Usuarios pertenecientes al grupo */
export function fetchGroupUsers(groupId: string): Promise<UserInfo[]> {
  return apiRequest<UserInfo[]>(`/groups/${groupId}/users`);
}

/** Usuarios disponibles para añadir al grupo */
export function fetchAvailableGroupUsers(
  groupId: string
): Promise<UserInfo[]> {
  return apiRequest<UserInfo[]>(`/groups/${groupId}/users/available`);
}

/** Actualizar lista de usuarios del grupo */
export function updateGroupUsers(
  groupId: string,
  userIds: string[]
): Promise<GroupFull> {
  return apiRequest<GroupFull>(`/groups/${groupId}/users`, {
    method: "POST",
    body: JSON.stringify({ userIds }),
  });
}

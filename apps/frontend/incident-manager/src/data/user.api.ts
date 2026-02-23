import { UserFull } from "@/modules/user/type/userFull";
import { UserInfo } from "@/modules/user/type/userInfo";
import { UserUpsertDTO } from "@/modules/user/type/userUpsertDTO";

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
 * Users
 * ============================================================ */

/** Obtener todos los usuarios */
export function fetchUsers(): Promise<UserInfo[]> {
  return apiRequest<UserInfo[]>("/users");
}

/** Obtener un usuario por ID */
export function fetchUserById(id: string): Promise<UserFull> {
  return apiRequest<UserFull>(`/users/${id}`);
}

/** Crear un nuevo usuario */
export function createUser(userData: UserUpsertDTO): Promise<UserFull> {
  return apiRequest("/users", {
    method: "POST",
    body: JSON.stringify(userData),
  });
}

/** Actualizar un usuario */
export function updateUser(
  id: string, 
  userData: UserUpsertDTO
): Promise<UserFull> {
  return apiRequest(`/users/${id}`, {
    method: "PUT",
    body: JSON.stringify(userData),
  });
}
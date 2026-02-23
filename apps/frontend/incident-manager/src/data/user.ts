// import type { User } from "@/modules/user/type/user";

// // ------------------------------
// // Datos simulados
// // ------------------------------
// const mockUsers: User[] = [
//   {
//     id: "1",
//     name: "Juan",
//     lastName: "Padilla",
//     secondLastName: "Garcia",
//     email: "jpg@ejemplo.com"
//   },
//   {
//     id: "2",
//     name: "Antonio",
//     lastName: "Torres",
//     secondLastName: "Parejo",
//     email: "atp@ejemplo.com"
//   },
//   {
//     id: "3",
//     name: "Andres",
//     lastName: "Lucas",
//     secondLastName: "Garcia",
//     email: "alg@ejemplo.com"
//   },
// ];

// // ------------------------------
// // Funciones simuladas
// // ------------------------------

// /**
//  * Simula una llamada asíncrona al backend para obtener todos los Usuarios.
//  * @returns Promesa con la lista de usuarios.
//  */
// export async function fetchUsers(): Promise<User[]> {
//   // simulamos un pequeño retardo como si viniera de una API
//   await new Promise((resolve) => setTimeout(resolve, 500));
//   return mockUsers;
// }

// /**
//  * Simula la obtención de un usuario por su ID.
//  * @param id ID del usuario a obtener.
//  * @returns Promesa con el usuario o null si no existe.
//  */
// export async function fetchUserById(id: string): Promise<User | null> {
//   const users = await fetchUsers();
//   const user = users.find((r) => String(r.id) === String(id));
//   await new Promise((r) => setTimeout(r, 300)); // simula delay
//   return user ?? null;
// }

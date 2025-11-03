// Simulacion de backend

export type LoginCredentials = {
  username: string;
  password: string;
  code2FA: string;
};

export async function login({ username, password, code2FA }: LoginCredentials): Promise<boolean> {
  // Simula retardo de red
  await new Promise((r) => setTimeout(r, 700));

  // Validación simulada
  const isValidUser = username === "admin" && password === "1234";
  const isValid2FA = code2FA === "000000"; // valor simulado (debería venir del generador TOTP)

  return isValidUser && isValid2FA;
}

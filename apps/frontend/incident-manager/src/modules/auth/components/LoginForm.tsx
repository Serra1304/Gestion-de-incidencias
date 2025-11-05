"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import FormField from "@/components/ui/FormFiled";
import { login } from "@/modules/auth/services/authService";
import Button from "@/components/ui/Button";
import { THEME } from "@/components/ui/theme/theme";

export default function LoginForm() {
    const router = useRouter();

    // Estado de los campos
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [code2FA, setCode2FA] = useState("");

    // Estado de carga y error
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    // Manejo del envío del formulario
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const success = await login({ username, password, code2FA });

            if (success) {
                router.push("/incidents/list");
            } else {
                setError("Credenciales o código 2FA incorrectos.");
            }
        } catch (err) {
            setError("Error al conectar con el servidor.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <form
            onSubmit={handleSubmit}
            className="flex flex-col gap-4 p-6 w-full"
        >

            <FormField
                label="Usuario"
                name="username"
                value={username}
                onChange={setUsername}
                placeholder="User"
                className="w-full"
            />

            <FormField
                label="Contraseña"
                name="password"
                type="password"
                value={password}
                onChange={setPassword}
                placeholder="Password"
                className="w-full"
            />

            <FormField
                label="Código 2FA"
                name="code2FA"
                value={code2FA}
                onChange={setCode2FA}
                placeholder="2FA"
                className="w-full"
            />

            {error && (
                <p className={`text-sm text-center rounded p-2 ${THEME.danger.text.base} ${THEME.danger.bg.base} ${THEME.danger.border.base}`}>
                    {error}
                </p>
            )}

            <Button
                type="submit"
                theme="standard"
                disabled={loading}
                className="mt-5 h-10"
            >
                {loading ? "Accediendo..." : "Entrar"}
            </Button>

            <button
                type="button"
                onClick={() => router.push("/forgot-password")}
                className={`${THEME.standard.text.base} text-sm text-center hover:underline focus:underline focus:outline-none`}
            >
                ¿Olvidaste tu contraseña?
            </button>
        </form>
    );
}

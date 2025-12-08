"use server";

import { cookies } from "next/headers";

const API_URL = "http://backend:8080/api/auth/login";

export async function loginAction(username: string, password: string) {
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                email: username,
                password: password
            }),
            cache: "no-store"
        });

        if (!response.ok) {
            return { success: false };
        }

        const data = await response.json();

        const cookieStore = await cookies();

        cookieStore.set("auth_token", data.token, {
            httpOnly: true,
            secure: true,
            sameSite: "strict",
            path: "/"
        });

        cookieStore.set("user_info", JSON.stringify(data.userInfoDTO), {
            httpOnly: false,  // accesible desde client
            secure: true,
            sameSite: "strict",
            path: "/"
        });

        return { success: true };

    } catch (error) {
        console.error("Error login:", error);
        return { success: false };
    }
}

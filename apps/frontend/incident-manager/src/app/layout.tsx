import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Gestor de incidencias",
  description: "App para la gestion de incidencias basada en tickets",
};

export default function RootLayout({children,}: Readonly<{children: React.ReactNode;}>) {
  return (
    <html lang="es">
      <body className="min-h-screen bg-gradient-to-br from-darkemerald from-30% to-emerald-900 to-150% ">
        {children}
      </body>
    </html>
  );
}

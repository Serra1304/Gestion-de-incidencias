"use client";

import React from "react";
import { THEME, Theme } from "./theme/theme";

/**
 * Props del componente Button.
 *
 * Extiende las propiedades nativas de un botón HTML, permitiendo
 * personalizar la variante visual, clases adicionales y contenido.
 */
type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
	/** Variante visual del botón (define color y estilo base) */
	theme?: Theme;
};

/**
 * Componente de botón reutilizable y estilizado con Tailwind CSS.
 *
 * Aplica automáticamente estilos según la variante seleccionada
 * y permite extender las clases con `className`. Soporta todas las
 * propiedades estándar de un botón HTML.
 *
 * @example
 * ```tsx
 * <Button variant="danger" onClick={handleDelete}>
 *   Eliminar
 * </Button>
 * ```
 */
export default function Button({
	theme = "standard",
	className = "",
	children,
	...props
}: ButtonProps) {

	return (
		<button
			className={`px-6 py-1 rounded-md font-semibold transition-colors 
				${THEME[theme].bg.button} ${THEME[theme].hover.base} ${THEME[theme].text.base} ${THEME[theme].focus.base} ${className}`}
			{...props}
		>
			{children}
		</button>
	);
}
"use client";

import React from "react";
import { VARIANTS, Variant } from "@/components/ui/theme/variants";

/**
 * Props del componente Button.
 *
 * Extiende las propiedades nativas de un botón HTML, permitiendo
 * personalizar la variante visual, clases adicionales y contenido.
 */
type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
	/** Variante visual del botón (define color y estilo base) */
	variant?: Variant;
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
	variant = "standard",
	className = "",
	children,
	...props
}: ButtonProps) {

	return (
		<button
			className={`px-6 py-1 rounded-md font-semibold transition-colors 
				${VARIANTS[variant].base} ${VARIANTS[variant].hover} ${VARIANTS[variant].text} ${className}`}
			{...props}
		>
			{children}
		</button>
	);
}
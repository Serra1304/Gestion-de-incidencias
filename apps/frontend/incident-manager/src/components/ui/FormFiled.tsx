"use client";

import React from "react";
import { VARIANTS, Variant } from "@/components/ui/theme/variants"

/** Tipo de entradas de datos permitidas para el FormField */
type textInputType = "text" | "password" | "email" | "number";

/**
 * Props para el componente FormField.
 */
type FormFieldProps = {
	/** Etiqueta visible encima del input */
	label: string;

	/** Nombre único del input (también usado como id para el label) */
	name: string;

	/** Tipo del input (por defecto: "text") */
	type?: textInputType;

	/** Valor actual del input */
	value?: string;

	/** Texto placeholder que se muestra cuando no hay valor */
	placeholder?: string;

	/** Si `true`, el input aparece deshabilitado */
	disable?: boolean;

	/** Variante visual del botón (define color y estilo base) */
	variant?: Variant;

	/** Callback que se dispara cuando el valor del input cambia */
	onChange?: (value: string) => void;

	/** Clases adicionales para el contenedor del input */
	className?: string;
};

/**
 * Componente de campo de formulario reutilizable.
 *
 * Renderiza una etiqueta y un input estilizado con Tailwind.
 * Soporta diferentes tipos de entrada (`text`, `password`, `email`, `number`).
 */
export default function FormField({
	label,
	name,
	type = "text",
	value,
	placeholder,
	disable = false,
	variant = "standard",
	onChange,
	className = "",
}: FormFieldProps) {

	return (
		<div className={`w-55 flex flex-col gap-1 ${className}`}>

			{/* Etiqueta */}
			<label htmlFor={name} className={`${VARIANTS[variant].text} ml-1`}>
				{label}
			</label>

			{/* Input */}
			<input
				id={name}
				name={name}
				type={type}
				value={value}
				placeholder={placeholder}
				disabled={disable}
				onChange={(e) => onChange?.(e.target.value)}
				className={`${disable ? VARIANTS[variant].textDisable : VARIANTS[variant].text} 
				rounded-form border ${VARIANTS[variant].border} ${VARIANTS[variant].bg}
				${VARIANTS[variant].focus} focus:outline-none focus:ring-2 py-1 px-2`}
			/>
		</div>
	);
}

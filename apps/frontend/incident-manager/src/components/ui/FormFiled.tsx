"use client";

import React from "react";
import clsx from "clsx";
import { THEME, Theme } from "./theme/theme";

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
	theme?: Theme;

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
	theme = "standard",
	onChange,
	className = "",
}: FormFieldProps) {
	
	return (
		<div className={clsx("flex flex-col gap-1", !className?.includes("w-") && "w-55", className)}>

			{/* Etiqueta */}
			<label htmlFor={name} className={`${THEME[theme].text.base} ml-1`}>
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
				className={`${disable ? THEME[theme].text.field : THEME[theme].text.base} 
				rounded-form border ${THEME[theme].border.base} ${THEME[theme].bg.field}
				${THEME[theme].focus.base} py-1 px-2`}
			/>
		</div>
	);
}

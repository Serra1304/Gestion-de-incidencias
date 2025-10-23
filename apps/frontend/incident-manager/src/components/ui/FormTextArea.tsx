"use client";

import { VARIANTS, Variant } from "@/components/ui/theme/variants"

/**
 * Props para el componente TextAreaField.
 */
type TextAreaFieldProps = {
	/** Etiqueta visible encima del textarea */
	label: string;

	/** Nombre único del textarea (también usado como id para el label) */
	name: string;

	/** Valor actual del textarea */
	value?: string;

	/** Texto placeholder que se muestra cuando no hay valor */
	placeholder?: string;

	/** Si `true`, el textarea aparece deshabilitado */
	disable?: boolean;

	/** Variante visual del botón (define color y estilo base) */
	variant?: Variant;

	/** Callback que se dispara cuando el valor del textarea cambia */
	onChange?: (value: string) => void;

	/** Clases adicionales para el contenedor del textarea */
	className?: string;

	/** Número de filas visibles del textarea (por defecto: 4) */
	rows?: number;
};

/**
 * Componente de campo de texto multilínea reutilizable.
 *
 * Renderiza una etiqueta y un `<textarea>` estilizado con Tailwind.
 * Permite definir número de filas, estados de habilitado/deshabilitado
 * y personalizar estilos mediante `className`.
 */
export default function TextAreaField({
	label,
	name,
	value,
	placeholder,
	disable,
	variant = "standard",
	onChange,
	className = "",
	rows = 4,
}: TextAreaFieldProps) {

	return (
		<div className={`flex flex-col gap-1 ${className}`}>

			{/* Etiqueta */}
			<label htmlFor={name} className={`${VARIANTS[variant].text} ml-1`}>
				{label}
			</label>

			{/* Area de texto */}
			<textarea
				id={name}
				name={name}
				rows={rows}
				value={value}
				placeholder={placeholder}
				onChange={(e) => onChange?.(e.target.value)}
				className={`${disable ? VARIANTS[variant].textDisable : VARIANTS[variant].text}
				border rounded-form ${VARIANTS[variant].border} ${VARIANTS[variant].bg}
				${VARIANTS[variant].focus} focus:outline-none focus:ring-2
        		scrollbar scrollbar-track-white/00 ${VARIANTS[variant].scroll} resize-none py-1 px-2`}
			/>
		</div>
	);
}

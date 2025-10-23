"use client";

import { VARIANTS, Variant } from "@/components/ui/theme/variants";

/**
 * Props para el componente CheckboxField.
 */
type CheckboxFieldProps = {
	/** Texto visible al lado del checkbox */
	label: string;

	/** Nombre único del checkbox (también usado como id para el label) */
	name: string;

	/** Estado actual del checkbox (marcado o no) */
	checked: boolean;

	/** Callback que se dispara cuando el valor del checkbox cambia */
	onChange?: (checked: boolean) => void;

	/** Clases adicionales para el contenedor */
	className?: string;

	/** Variante visual del checkbox */
	variant?: Variant;
};

/**
 * Componente de campo tipo checkbox reutilizable.
 *
 * Renderiza un input oculto de tipo checkbox y un label personalizado
 * que simula el cuadro marcado/no marcado. Admite variantes de estilo
 * y callback para manejar cambios en el estado.
 *
 * @example
 * ```tsx
 * <CheckboxField
 *   label="Aceptar términos"
 *   name="terms"
 *   checked={isChecked}
 *   onChange={setIsChecked}
 *   variant="danger"
 * />
 * ```
 */
export default function CheckboxField({
	label,
	name,
	checked,
	onChange,
	className = "",
	variant = "standard"
}: CheckboxFieldProps) {

	return (
		<div className={`flex items-center gap-2 ${className}`}>

			{/* Check nativo oculto*/}
			<input
				id={name}
				name={name}
				type="checkbox"
				checked={checked}
				onChange={(e) => onChange?.(e.target.checked)}
				className="hidden peer"
			/>

			{/* Check personalizado */}
			<label
				htmlFor={name}
				className={`w-5 h-5 border-2 rounded-md flex items-center justify-center cursor-pointer 
					${VARIANTS[variant].border} ${VARIANTS[variant].bg} ${VARIANTS[variant].text}`}
			>
				{checked ? "✓" : ""}
			</label>

			{/* Etiqueta */}
			<span className= {`${VARIANTS[variant].text}`}>
				{label}
			</span>
		</div>
	);
}

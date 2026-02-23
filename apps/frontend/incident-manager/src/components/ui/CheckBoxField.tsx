"use client";

import { THEME, Theme } from "./theme/theme";

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
	theme?: Theme;
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
	theme = "standard"
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
					${THEME[theme].border.base} ${THEME[theme].bg.field} ${THEME[theme].text.base}`}
			>
				{checked ? "✓" : ""}
			</label>

			{/* Etiqueta */}
			<span className= {`${THEME[theme].text.base}`}>
				{label}
			</span>
		</div>
	);
}

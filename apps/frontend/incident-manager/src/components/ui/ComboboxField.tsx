"use client";

/**
 * `ComboBox` es un componente reutilizable que permite seleccionar una opción de una lista desplegable.
 * 
 * Está diseñado para integrarse con el sistema de variantes visuales (`VARIANTS`), ofreciendo una apariencia
 * coherente con el tema general de la interfaz. Se puede personalizar su estilo mediante la prop `variant`,
 * y manejar su valor de forma controlada o no controlada.
 * 
 * ---
 * **Características principales:**
 * - Desplegable con lista de opciones personalizable.
 * - Cierre automático al hacer clic fuera del componente.
 * - Soporte para temas visuales mediante `VARIANTS`.
 * - Modo controlado a través de las props `value` y `onChange`.
 * 
 * ---
 * **Ejemplo de uso:**
 * ```tsx
 * <ComboBox
 *   label="País"
 *   name="country"
 *   options={[
 *     { value: "es", label: "España" },
 *     { value: "mx", label: "México" },
 *   ]}
 *   value={selectedCountry}
 *   onChange={(val) => setSelectedCountry(val)}
 *   variant="outlined"
 * />
 * ```
 */

import { useState, useRef, useEffect } from "react";
import { ChevronDownIcon } from "@heroicons/react/24/solid";
import { THEME, Theme } from "./theme/theme";

/** 
 * Representa una opción seleccionable dentro del ComboBox. 
 */
type Option = { value: string; label: string };

/**
 * Props del componente `ComboBox`.
 */
type ComboBoxProps = {
	/** Etiqueta visible encima del campo. */
	label: string;

	/** Nombre del campo (usado como `id` y referencia para formularios). */
	name: string;

	/** Lista de opciones disponibles. */
	options: Option[];

	/** Valor actualmente seleccionado. */
	value?: string;

	/** Función llamada al seleccionar una nueva opción. */
	onChange?: (value: string) => void;

	/** Clases adicionales para el contenedor principal. */
	className?: string;

	/** Variante visual que define el estilo del componente (`standard`, `light`, etc.). */
	theme?: Theme;
};

export default function ComboBox({
	label,
	name,
	options,
	value,
	onChange,
	className = "",
	theme = "standard",
}: ComboBoxProps) {

	const containerRef = useRef<HTMLDivElement | null>(null);				// Detectar clics fuera del componente
	const [isOpen, setIsOpen] = useState(false);							// Estado local del desplegable
	const selectedOption = options.find((opt) => opt.value === value);		// Opcion actual

	/** Maneja la selección de una opción */
	const handleSelect = (opt: Option) => {
		onChange?.(opt.value);
		setIsOpen(false);
	};

	/** Detecta clics fuera del ComboBox para cerrarlo automáticamente */
	useEffect(() => {
		function handleClickOutside(event: MouseEvent) {
			if (
				containerRef.current &&
				!containerRef.current.contains(event.target as Node)
			) {
				setIsOpen(false);
			}
		}
		document.addEventListener("mousedown", handleClickOutside);
		return () => {
			document.removeEventListener("mousedown", handleClickOutside);
		};
	}, []);

	return (
		<div ref={containerRef} className={`flex flex-col gap-1 ${className}`}>
			{/* Etiqueta */}
			<label htmlFor={name} className={`ml-1 ${THEME[theme].text.base}`}>
				{label}
			</label>

			<div className="relative">
				{/* Botón principal */}
				<button
					type="button"
					onClick={() => setIsOpen((prev) => !prev)}
					className={`w-55 flex justify-between items-center px-3 py-1 
					rounded-lg border ${THEME[theme].border.base} ${THEME[theme].bg.field} ${THEME[theme].text.base}
					focus:outline-none focus:ring-2 ${THEME[theme].focus.base}`}
				>
					<span>{selectedOption ? selectedOption.label : "Selecciona..."}</span>
					<ChevronDownIcon
						className={`h-5 w-5 transition-transform ${isOpen ? "rotate-180" : ""
							}`}
					/>
				</button>

				{/* Lista desplegable */}
				{isOpen && (
					<ul className={`absolute mt-1 w-full ${THEME[theme].bg.button} 
					rounded-lg border-2 ${THEME[theme].border.base} shadow-lg z-10 max-h-60 overflow-auto`}>
						{options.map((opt) => (
							<li
								key={opt.value}
								onClick={() => handleSelect(opt)}
								className={`px-4 py-2 cursor-pointer ${THEME[theme].hover.base} 
								${opt.value === value ? `${THEME.standard.text.focus} font-bold` : THEME[theme].text.base}`}
							>
								{opt.label}
							</li>
						))}
					</ul>
				)}
			</div>
		</div>
	);
}

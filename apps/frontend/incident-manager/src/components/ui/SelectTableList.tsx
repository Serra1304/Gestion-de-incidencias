"use client";

import { th } from "react-day-picker/locale";
import { THEME, Theme } from "./theme/theme";

// ===============================================================
// 📘 SelectableList
// ===============================================================
// Descripción general:
// --------------------
// `SelectableList` es un componente de lista visual simple con título,
// diseñado para mostrar elementos seleccionables (sin checkbox).
//
// Ideal para casos como gestión de usuarios, selección de elementos,
// o listas de asignación entre grupos.
//
// --------------------
// Principales características:
// - Muestra una lista con título fijo.
// - Permite seleccionar elementos individuales.
// - El estilo de los elementos seleccionados cambia automáticamente.
// - No usa checkbox; el color de fondo indica selección.
// - Controlado externamente mediante `selected` y `onToggle`.
//
// --------------------
// Ejemplo de uso:
// ```tsx
// <SelectableList
//   title="Usuarios del rol"
//   items={[
//     { id: "1", label: "Juan Pérez" },
//     { id: "2", label: "María López" },
//   ]}
//   selected={selectedUserIds}
//   onToggle={toggleUser}
//   variant="standard"
// />
// ```
//
// ===============================================================

export type SelectableItem = {
	id: string;
	label: string;
};

type SelectableListProps = {
	title: string;
	items: SelectableItem[];
	selected: string[];
	onToggle: (id: string) => void;
	theme?: Theme;
	className?: string;
};


export default function SelectableList({
	title,
	items,
	selected,
	onToggle,
	theme = "standard",
	className,
}: SelectableListProps) {

	return (
		<div className={`rounded-lg border overflow-hidden ${THEME[theme].border.base} ${THEME[theme].text.base} ${className}`}>

			{/* Encabezado */}
			<div className={`px-3 py-2 ${THEME[theme].bg.calendar} rounded-t-md`}>
				<span className="font-semibold">{title}</span>
			</div>

			{/* Lista */}
			<ul className="">
				{items.length === 0 ? (
					<li className={`px-4 py-2 text-sm ${THEME[theme].text.disble}`}>
						Sin elementos
					</li>
				) : (
					items.map((item) => {
						const isSelected = selected.includes(item.id);
						return (
							<li
								key={item.id}
								onClick={() => onToggle(item.id)}
								className={`
									px-4 py-2 cursor-pointer transition-colors duration-150
									${isSelected ? THEME[theme].bg.base : THEME[theme].hover.base}
								`}
							>
								<span className={`block text-sm font-medium`}>
									{item.label}
								</span>
							</li>
						);
					})
				)}
			</ul>
		</div>
	);
}

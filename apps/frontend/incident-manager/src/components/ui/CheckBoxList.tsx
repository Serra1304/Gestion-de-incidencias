"use client";

import { useState } from "react";
import { ChevronDownIcon, ChevronRightIcon } from "@heroicons/react/24/outline";
import { VARIANTS, Variant } from "@/components/ui/theme/variants";
import Button from "@/components/ui/Button";
import CheckboxField from "@/components/ui/CheckBoxField";

/**
 * Representa un elemento seleccionable dentro de la lista.
 */
export type CheckboxItem = {
    id: string;
    label: string;
};

/**
 * Props del componente CheckboxList.
 */
type CheckboxListProps = {
    // Título de la lista (encabezado de sección)
    title: string;

    // Lista de elementos a mostrar
    items: CheckboxItem[];

    // IDs actualmente seleccionados
    selected: string[];

    // Función para alternar selección de un ítem individual
    onToggle: (id: string) => void;

    // Función para alternar la selección de todos los ítems
    onToggleAll: () => void;

    // Estado inicial (expandido o colapsado)
    defaultOpen?: boolean;

    // Variante visual (basada en el tema)
    variant?: Variant;
};

// `CheckboxList` es un componente reutilizable que muestra una lista
// de elementos seleccionables mediante checkboxes, agrupados bajo un
// encabezado con opción de "seleccionar todo" y capacidad de expandir/contraer.
//
// Permite representar un grupo de permisos, configuraciones u opciones
// en una interfaz coherente y visualmente unificada según el `variant`.
//
// --------------------
// Principales características:
// - Soporta selección individual y global ("Seleccionar todo").
// - Permite mostrar u ocultar los elementos de la lista.
// - Integra un sistema de variantes visuales para adaptar estilos.
// - Completamente controlado a través de props externas (`selected`, `onToggle`, etc.).
//
// --------------------
// Ejemplo de uso:
// ```tsx
// <CheckboxList
//   title="Gestión de usuarios"
//   items={[
//     { id: "create_user", label: "Crear usuario" },
//     { id: "delete_user", label: "Eliminar usuario" },
//   ]}
//   selected={selectedPermissions}
//   onToggle={togglePermission}
//   onToggleAll={toggleAllPermissions}
//   variant="outlined"
// />
// ```
export default function CheckboxList({
    title,
    items,
    selected,
    onToggle,
    onToggleAll,
    defaultOpen = true,
    variant = "standard"
}: CheckboxListProps) {

    // Estado local (expandido / colapsado)
    const [isOpen, setIsOpen] = useState(defaultOpen);

    // Determina si todos los ítems están seleccionados
    const allSelected = items.length > 0 && items.every((item) => selected.includes(item.id));

    return (
        <div className={`rounded-lg border ${VARIANTS[variant].border} ${VARIANTS[variant].text}`}>
            {/* Encabezado */}
            <div
                className={`flex justify-between items-center px-3 py-2 ${VARIANTS[variant].base}
				${isOpen ? "rounded-t-md" : "rounded-md"}`}
            >
                {/* Botón de desplegar */}
                <button
                    onClick={() => setIsOpen((prev) => !prev)}
                    className="flex items-center gap-2"
                >
                    {isOpen ? (
                        <ChevronDownIcon className={`w-5 h-5 ${VARIANTS[variant].textFocus}`} />
                    ) : (
                        <ChevronRightIcon className={`w-5 h-5 ${VARIANTS[variant].textFocus}`} />
                    )}
                    <span className="font-semibold">{title}</span>
                </button>

                {/* Botón seleccionar todo */}
                <Button
                    onClick={onToggleAll}
                    variant="light"
                    className="text-xs"
                >
                    {allSelected ? "Quitar todo" : "Seleccionar todo"}
                </Button>
            </div>

            {/* Lista de ítems */}
            {isOpen && (
                <ul>
                    {items.map((item) => (
                        <li
                            key={item.id}
                            className={`px-4 py-2 flex items-center gap-2 cursor-pointer ${VARIANTS[variant].hover}`}
                            onClick={() => onToggle(item.id)}
                        >
                            <CheckboxField
                                label={item.label}
                                name={item.label}
                                checked={selected.includes(item.id)}
                                onChange={() => onToggle(item.id)}
                            />
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

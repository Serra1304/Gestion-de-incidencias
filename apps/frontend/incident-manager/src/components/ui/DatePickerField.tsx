"use client";

import { useState, useRef, useEffect } from "react";
import { DayPicker } from "react-day-picker";
import "react-day-picker/dist/style.css";
import { CalendarDaysIcon } from "@heroicons/react/24/outline";
import { VARIANTS } from "./theme/variants";

/**
 * Props para el componente DatePickerField.
 */
type DatePickerFieldProps = {
    /** Etiqueta visible encima del input */
    label: string;

    /** Nombre único del input (también usado como id) */
    name: string;

    /** Fecha actualmente seleccionada */
    value?: Date;

    /** Función llamada al seleccionar una fecha */
    onChange?: (date: Date) => void;

    /** Clases CSS adicionales para el contenedor principal */
    className?: string;
};


/**
 * DatePickerField
 * --------------------
 * Componente de selección de fecha con calendario desplegable.
 * Integra `react-day-picker` y un botón que muestra la fecha seleccionada.
 */
export default function DatePickerField({
    label,
    name,
    value,
    onChange,
    className = "",
}: DatePickerFieldProps) {

    /** Ref para detectar clicks fuera del componente y cerrar el calendario */
    const containerRef = useRef<HTMLDivElement | null>(null);

    /** Estado local para controlar si el calendario está abierto */
    const [isOpen, setIsOpen] = useState(false);

    /** Formatea la fecha seleccionada o muestra texto por defecto */
    const formattedValue = value
        ? value.toLocaleDateString("es-ES")
        : "Selecciona una fecha";

    /**
     * Hook para cerrar el calendario al hacer clic fuera del componente
     */
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
            {/* Etiqueta del campo */}
            <label htmlFor={name} className={`ml-1 ${VARIANTS.standard.text}`}>
                {label}
            </label>

            <div className="relative">
                {/* Botón que abre el calendario */}
                <button
                    type="button"
                    onClick={() => setIsOpen((prev) => !prev)}
                    className={`w-55 flex justify-between items-center px-3 py-1 ${VARIANTS.standard.text}
                        rounded-lg border ${VARIANTS.standard.border} ${VARIANTS.standard.bg} 
                        focus:outline-none focus:ring-2 ${VARIANTS.standard.focus}`}
                >
                    <span>{formattedValue}</span>
                    <CalendarDaysIcon className={`h-5 w-5 ${VARIANTS.standard.text}`} />
                </button>

                {/* Calendario */}
                {isOpen && (
                    <div className={`absolute mt-1 ${VARIANTS.standard.bg} 
                        border ${VARIANTS.standard.border} rounded-lg shadow-lg z-20 p-3`}
                    >
                        <DayPicker
                            mode="single"
                            selected={value}
                            onSelect={(date) => {
                                if (date) {
                                    onChange?.(date);
                                    setIsOpen(false);
                                }
                            }}
                            classNames={{
                                chevron: "fill-white",
                                day: `${VARIANTS.standard.hover} ${VARIANTS.standard.text} w-80 h-10`,
                                caption_label: `${VARIANTS.standard.text}`,
                                today: `font-bold ${VARIANTS.standard.textFocus}`,
                                weekday: `${VARIANTS.standard.textFocus}`,
                                selected: `${VARIANTS.standard.base} text-white`
                            }}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}


"use client";

import { useState, useRef, useEffect } from "react";
import { ChevronDownIcon, CheckIcon } from "@heroicons/react/24/solid";
import { THEME } from "./theme/theme";

type Option = { value: string; label: string };

type MultiSelectFieldProps = {
  label: string;
  name: string;
  options?: Option[];
  value?: string[];
  onChange?: (values: string[]) => void;
  className?: string;
};

export default function MultiSelectField({ label, name, options = [], value = [], onChange, className = "" }: MultiSelectFieldProps) {

    const containerRef = useRef<HTMLDivElement | null>(null);
    const [isOpen, setIsOpen] = useState(false);

    const handleToggle = (val: string) => {
        let newValues: string[];
        
        if (value.includes(val)) {
            newValues = value.filter((v) => v !== val);
        } else {
            newValues = [...value, val];
        }
        onChange?.(newValues);
    };

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
    <div ref={containerRef} className={`flex flex-col w-55 gap-1 ${className}`}>
      <label htmlFor={name} className= {`ml-1 ${THEME.standard.text.base}`}>
        {label}
      </label>

      <div className="relative">
        {/* Botón principal */}
        <button
          type="button"
          onClick={() => setIsOpen((prev) => !prev)}
          className={`w-full flex justify-between items-center px-3 py-1 rounded-lg border ${THEME.standard.border.base}  ${THEME.standard.text.base}
            ${THEME.standard.focus.base} ${THEME.standard.bg.field}`}
        >
          <span className="truncate overflow-hidden text-ellipsis whitespace-nowrap">
            {value.length > 0
              ? options
                  .filter((opt) => value.includes(opt.value))
                  .map((opt) => opt.label)
                  .join(", ")
              : "Selecciona..."}
          </span>
          <ChevronDownIcon
            className={`h-5 w-5 transition-transform flex-shrink-0 ${
              isOpen ? "rotate-180" : ""}`}
          />
        </button>

        {/* Lista desplegable */}
        {isOpen && (
          <ul className= {`absolute mt-1 w-full ${THEME.standard.bg.button} rounded-lg shadow-lg z-10 max-h-60 overflow-auto`}>
            {options.map((opt) => {
              const isSelected = value.includes(opt.value);
              return (
                <li
                  key={opt.value}
                  onClick={() => handleToggle(opt.value)}
                  className= {`px-4 py-2 cursor-pointer flex items-center gap-2 ${THEME.standard.hover.base} ${THEME.standard.text.base}`}
                >
                  <span className={`w-5 h-5 flex items-center justify-center border rounded 
                    ${THEME.standard.border.second} ${THEME.standard.text.base}`} >
                    {isSelected && <CheckIcon className="w-4 h-4" />}
                  </span>
                  {opt.label}
                </li>
              );
            })}
          </ul>
        )}
      </div>
    </div>
  );
}

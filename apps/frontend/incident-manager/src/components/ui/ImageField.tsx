"use client";

import { ChevronLeftIcon, ChevronRightIcon, PlusIcon, TrashIcon } from "@heroicons/react/24/outline";
import { useRef, useState } from "react";
import { THEME } from "./theme/theme";

type ImageUploadFieldProps = {
  label: string;
  name: string;
  images: string[];
  onAdd?: (urls: string[]) => void;
  onDelete?: (index: number) => void;
  className?: string;
};

export default function ImageUploadField({
  label,
  name,
  images,
  onAdd,
  onDelete,
  className = "",
}: ImageUploadFieldProps) {
  const [currentIndex, setCurrentIndex] = useState(0);
  const inputRef = useRef<HTMLInputElement>(null);

  const handlePrev = () => {
    if (images.length === 0) return;
    setCurrentIndex((prev) => (prev - 1 + images.length) % images.length);
  };

  const handleNext = () => {
    if (images.length === 0) return;
    setCurrentIndex((prev) => (prev + 1) % images.length);
  };

  const handleClickOnImage = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (images.length === 0) return;
    const { left, width } = e.currentTarget.getBoundingClientRect();
    const x = e.clientX - left;
    if (x < width / 2) handlePrev();
    else handleNext();
  };

  const handleOpenFilePicker = () => {
    inputRef.current?.click();
  };

  const handleFilesSelected = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && onAdd) {
      /* onAdd(e.target.files);
      e.target.value = ""; */ // reset input

    if (!e.target.files || e.target.files.length === 0) return;

    // Convertimos los archivos en URLs temporales
    const urls = Array.from(e.target.files).map((file) => URL.createObjectURL(file));

    // Llamamos al callback con las URLs
    onAdd?.(urls);

    // Limpiamos el input para poder volver a seleccionar las mismas imágenes
    e.target.value = "";

    }
  };

  return (
    <div className={`flex flex-col gap-1 ${className}`}>
      <label className={`ml-1 ${THEME.standard.text.base}`}>{label}</label>

      <div
        className={`relative h-100 w-120 overflow-hidden items-center justify-center
          rounded-form border ${THEME.standard.border.base} ${THEME.standard.bg.field}`}
        onClick={handleClickOnImage}
      >
        {images.length > 0 ? (
          <>
            <img
              src={images[currentIndex]}
              alt={`Imagen ${currentIndex + 1}`}
              className="flex object-contain w-full h-full"
            />

            {/* Flechas */}
            <button
              onClick={(e) => { e.stopPropagation(); handlePrev(); }}
              className={`absolute left-2 top-1/2 -translate-y-1/2 ${THEME.standard.text.base} rounded-full size-10 ${THEME.standard.hover.base}`}
            >
              <ChevronLeftIcon />
            </button>
            <button
              onClick={(e) => { e.stopPropagation(); handleNext(); }}
              className={`absolute right-2 top-1/2 -translate-y-1/2 ${THEME.standard.text.base} rounded-full size-10 ${THEME.standard.hover.base}`}
            >
              <ChevronRightIcon />
            </button>

            {/* Botones + y 🗑️ */}
            <div className="absolute top-2 right-2 flex gap-2">
              <button
                onClick={(e) => { e.stopPropagation(); handleOpenFilePicker(); }}
                className={`${THEME.standard.text.base} rounded-full size-8 ${THEME.standard.hover.base} flex items-center justify-center`}
              >
                <PlusIcon className="size-5" />
              </button>
              <button
                onClick={(e) => { e.stopPropagation(); onDelete?.(currentIndex); }}
                className={`${THEME.standard.text.base} rounded-full size-8 ${THEME.danger.hover.base} flex items-center justify-center`}
              >
                <TrashIcon className="size-5" />
              </button>
            </div>
          </>
        ) : (
          <span
            className={`flex h-full items-center justify-center ${THEME.standard.text.base} cursor-pointer`}
            onClick={(e) => { e.stopPropagation(); handleOpenFilePicker(); }}
          >
            Pulsa para subir una imagen
          </span>
        )}

        {/* Input oculto para seleccionar archivos */}
        <input
          ref={inputRef}
          type="file"
          accept="image/*"
          multiple
          className="hidden"
          onChange={handleFilesSelected}
        />
      </div>
    </div>
  );
}

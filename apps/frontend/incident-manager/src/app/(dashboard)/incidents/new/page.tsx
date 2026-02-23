"use client";

import { useState } from "react";
import Button from "@/components/ui/Button";
import { useRouter } from "next/navigation";
import FormField from "@/components/ui/FormFiled";
import TextAreaField from "@/components/ui/FormTextArea";
import DatePickerField from "@/components/ui/DatePickerField";
import ComboBoxField from "@/components/ui/ComboboxField";
import MultiSelectField from "@/components/ui/MultiSelectField";
import ImageUploadField from "@/components/ui/ImageField";
import { INCIDENT_PRIORITY_OPTIONS } from "@/modules/incident/type/incidentPriority";
import { THEME } from "@/components/ui/theme/theme";

const workGroupList = [
	{ value: "maintenance", label: "Mantenimiento" },
	{ value: "calidate", label: "Calidad" },
	{ value: "manager", label: "Administracion" }
]

export default function NewIncidentPage() {
	const [priority, setPriority] = useState<string>(INCIDENT_PRIORITY_OPTIONS[1].value);
	const [loading, setLoading] = useState(false);
	const router = useRouter();

	// Almacenamiento de rutas de imagenes
	const [images, setImages] = useState<string[]>([]);

	// Estado de opciones seleccionadas de grupos de trabajo
	const [workGroups, setWorkGroups] = useState<string[]>([]);

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		setLoading(true);
		try {
			const newIncident = {};
		} finally {
			setLoading(false);
		}
	};

	return (
		<div className="flex flex-col h-full">
			<header className={`flex items-center justify-between border-b ${THEME.standard.border.base} p-4`}>
				<h1 className={`text-2xl font-bold ${THEME.standard.text.base}`}>Nueva incidencia</h1>
			</header>

			<form
				onSubmit={handleSubmit}
				className={`flex flex-col gap-4 mx-auto mt-8 p-6 ${THEME.light.bg.base} rounded-2xl shadow-xl shadow-current`}
			>
				<div className="flex gap-10">
					<FormField label="Id" name="id" disable={true} placeholder="125" className="w-30" />
					<DatePickerField label="Fecha de inicio" name="initDate" />
					<DatePickerField label="Fecha limite" name="limitDate" />
					<ComboBoxField label="Prioridad" name="priority" options={INCIDENT_PRIORITY_OPTIONS} value={priority} onChange={setPriority} />
					<MultiSelectField label="Grupos de trabajo" name="workGroup" options={workGroupList} value={workGroups} onChange={setWorkGroups} />
				</div>

				<div className="flex gap-10">
					<ImageUploadField
						label="Imágenes"
						name="images"
						images={images}
						onAdd={(urls) => setImages((prev) => [...prev, ...urls])}
						onDelete={(index) =>
							setImages((prev) => prev.filter((_, i) => i !== index))
						}
					/>

					<div className="flex flex-col gap-5 w- flex-1">
						<FormField label="Titulo descriptivo" name="title" className="w-full" />
						<TextAreaField label="Descripcion" name="description" className="flex-1 " />
					</div>
				</div>

				<div className="flex justify-end gap-2 mt-6">
					<Button onClick={() => router.back()} disabled={loading}>
						Cancelar
					</Button>
					<Button type="submit" /*loading={loading}*/>
						Crear incidencia
					</Button>
				</div>
			</form>
		</div>
	);
}

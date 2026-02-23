"use client";

import { useState } from "react";
import CheckboxList from "@/components/ui/CheckBoxList";

// --------------------
// Tipos base
// --------------------
type Permission = {
	id: string;
	label: string;
};

type PermissionSection = {
	title: string;
	permissions: Permission[];
};

type RolePermissionsProps = {
	sections: PermissionSection[];
	value: string[]; // ids seleccionados
	onChange: (updatedRole: any) => void;
};

// --------------------
// Componente principal
// --------------------
export default function RolePermissions({ sections, value, onChange }: RolePermissionsProps) {
	const [openSections, setOpenSections] = useState<string[]>(sections.map((s) => s.title));

	// Alterna si una sección está abierta o cerrada
	const toggleSection = (title: string) => {
		setOpenSections((prev) =>
			prev.includes(title) ? prev.filter((s) => s !== title) : [...prev, title]
		);
	};

	// Alterna un permiso individual
	const togglePermission = (id: string) => {
		if (value.includes(id)) {
			onChange(value.filter((perm) => perm !== id));
		} else {
			onChange([...value, id]);
		}
	};

	// Alterna todos los permisos de una sección
	const toggleSectionAll = (permissions: Permission[]) => {
		const sectionIds = permissions.map((p) => p.id);
		const hasAll = sectionIds.every((id) => value.includes(id));
		if (hasAll) {
			onChange(value.filter((v) => !sectionIds.includes(v)));
		} else {
			onChange([...new Set([...value, ...sectionIds])]);
		}
	};

	// --------------------
	// Renderizado
	// --------------------
	return (
		<div className="flex flex-col gap-2">
			{sections.map((section) => {
				const isOpen = openSections.includes(section.title);
				const allSelected = section.permissions.every((p) => value.includes(p.id));

				return (
					<div key={section.title}>
						<CheckboxList
							title={section.title}
							items={section.permissions.map((p) => ({
								id: p.id,
								label: p.label,
							}))}
							selected={value}
							onToggle={togglePermission}
							onToggleAll={() => toggleSectionAll(section.permissions)}
							defaultOpen={isOpen}
						/>
					</div>
				);
			})}
		</div>
	);
}

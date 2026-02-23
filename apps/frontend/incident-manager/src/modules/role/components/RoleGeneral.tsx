"use client";

import { useEffect, useState } from "react";
import FormField from "@/components/ui/FormFiled";
import FormTextArea from "@/components/ui/FormTextArea";
import CheckboxField from "@/components/ui/CheckBoxField";
import type { Role } from "@/modules/role/type/role";

type RoleConfigurationProps = {
    role: Role,
    onChange: (updatedRole: any) => void;
};

export default function RoleConfiguration({ role, onChange }: RoleConfigurationProps) {
    const [form, setForm] = useState(role);
    useEffect(() => { setForm(role); }, [role]);

    const updateField = (field: keyof typeof form, value: any) => {
        const updated = { ...form, [field]: value };
        setForm(updated);
        onChange(updated);
    };

    return (
        <div className="flex flex-col gap-5 max-w-2xl">
            {/* ID - solo lectura */}
            <FormField
                label="ID"
                name="id"
                value={form.id}
                disable={true}
                onChange={() => {}}
                className="w-1/2"
            />

            {/* Nombre */}
            <FormField
                label="Nombre del rol"
                name="name"
                value={form.name}
                onChange={(val) => updateField("name", val)}
                className="w-full"
            />

            {/* Descripción */}
            <FormTextArea
                label="Descripción"
                name="description"
                value={form.description}
                onChange={(val) => updateField("description", val)}
                rows={4}
                className="w-full"
            />

            {/* Activo */}
            <CheckboxField
                label="Rol activo"
                name="active"
                checked={form.active ?? true}
                onChange={(val) => updateField("active", val)}
            />

            {/* Fechas */}
            <div className="grid grid-cols-2 gap-5">
                <FormField
                    label="Fecha de creación"
                    name="createdAt"
                    disable={true}
                    value={form.createdAt || ""}
                />
                <FormField
                    label="Última actualización"
                    name="updatedAt"
                    disable={true}
                    value={form.updatedAt || ""}
                />
            </div>
        </div>
    );
}

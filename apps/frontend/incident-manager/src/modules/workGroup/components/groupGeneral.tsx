"use client";

import { useEffect, useState } from "react";
import FormField from "@/components/ui/FormFiled";
import FormTextArea from "@/components/ui/FormTextArea";
import CheckboxField from "@/components/ui/CheckBoxField";
import type { GroupFull } from "@/modules/workGroup/type/groupFull";

import { useContext } from "react";
import { GroupContext } from "@/app/(dashboard)/groups/[groupId]/layout";

export default function GroupGeneral() {
  const { group, updateGroup } = useContext(GroupContext);

  if (!group) return null;

  const updateField = <K extends keyof GroupFull>(
    field: K,
    value: GroupFull[K]
  ) => {
    updateGroup({ [field]: value } as Partial<GroupFull>);
  };

    return (
        <div className="flex flex-col gap-5 max-w-2xl">
            {/* ID - solo lectura */}
            <FormField
                label="ID"
                name="id"
                value={group.id}
                disable={true}
                onChange={() => {}}
                className="w-1/2"
            />

            {/* Nombre */}
            <FormField
                label="Nombre del grupo"
                name="name"
                value={group.name}
                onChange={(val) => updateField("name", val)}
                className="w-full"
            />

            {/* Descripción */}
            <FormTextArea
                label="Descripción"
                name="description"
                value={group.description}
                onChange={(val) => updateField("description", val)}
                rows={4}
                className="w-full"
            />

            {/* Activo */}
            <CheckboxField
                label="Grupo activo"
                name="active"
                checked={group.active ?? true}
                onChange={(val) => updateField("active", val)}
            />
        </div>
    );
}

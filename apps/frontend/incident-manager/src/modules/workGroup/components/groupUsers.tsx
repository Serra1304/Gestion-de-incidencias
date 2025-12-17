"use client";

import { useState, useEffect, useContext } from "react";
import SelectableList from "@/components/ui/SelectTableList";
import IconButton from "@/components/ui/IconButton";
import { ChevronDoubleLeftIcon, ChevronDoubleRightIcon } from "@heroicons/react/24/outline";
import { GroupContext, UnsavedContext } from "@/app/(dashboard)/groups/[groupId]/layout";
import { UserInfo } from "@/modules/user/type/userInfo";

interface SelectableUser {
  id: string;
  label: string;
}

function toSelectableUser(u: UserInfo): SelectableUser {
  return {
    id: u.id,
    label: `${u.name} ${u.lastName} ${u.secondLastName}`,
  };
}

export default function GroupUsers() {
  const { group } = useContext(GroupContext);
  const { setHasUnsavedChanges } = useContext(UnsavedContext);

  const [groupUsers, setGroupUsers] = useState<SelectableUser[]>([]);
  const [availableUsers, setAvailableUsers] = useState<SelectableUser[]>([]);
  const [selected, setSelected] = useState<string[]>([]);

  // 🔄 Cargar usuarios cuando cambie el grupo
useEffect(() => {
  if (!group) return;

  setGroupUsers((group.users ?? []).map(toSelectableUser));
  setAvailableUsers((group.availableUsers ?? []).map(toSelectableUser));
}, [group]);

  const toggleUser = (id: string) => {
    setSelected((prev) =>
      prev.includes(id)
        ? prev.filter((i) => i !== id)
        : [...prev, id]
    );
  };

  // 👉 Añadir usuarios al grupo
  const addToGroup = () => {
    const toAdd = availableUsers.filter((u) => selected.includes(u.id));

    setGroupUsers((prev) => [...prev, ...toAdd]);
    setAvailableUsers((prev) => prev.filter((u) => !selected.includes(u.id)));

    setSelected([]);
    setHasUnsavedChanges(true); // avisamos al layout de cambios
  };

  // 👉 Quitar usuarios del grupo
  const removeFromGroup = () => {
    const toRemove = groupUsers.filter((u) => selected.includes(u.id));

    setAvailableUsers((prev) => [...prev, ...toRemove]);
    setGroupUsers((prev) => prev.filter((u) => !selected.includes(u.id)));

    setSelected([]);
    setHasUnsavedChanges(true);
  };

  return (
    <div className="flex h-full gap-4">
      {/* Usuarios disponibles */}
      <div className="w-1/2">
        <SelectableList
          title="Usuarios en el grupo"
          items={groupUsers}
          selected={selected}
          onToggle={toggleUser}
          className="h-full"
        />
      </div>

      {/* Botones */}
      <div className="flex flex-col gap-2 justify-center">
        <IconButton icon={<ChevronDoubleRightIcon />} onClick={removeFromGroup} />
        <IconButton icon={<ChevronDoubleLeftIcon />} onClick={addToGroup} />
      </div>

      {/* Usuarios en el grupo */}
      <div className="w-1/2">
        <SelectableList
          title="Usuarios disponibles"
          items={availableUsers}
          selected={selected}
          onToggle={toggleUser}
          className="h-full"
        />
      </div>
    </div>
  );
}

"use client";

import { useState, useContext } from "react";
import SelectableList from "@/components/ui/SelectTableList";
import IconButton from "@/components/ui/IconButton";
import { ChevronDoubleLeftIcon, ChevronDoubleRightIcon } from "@heroicons/react/24/outline";
import { GroupContext } from "@/app/(dashboard)/groups/[groupId]/layout";
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
  const { group, updateGroup } = useContext(GroupContext);
  const groupUsers = (group?.users ?? []).map(toSelectableUser);
  const availableUsers = (group?.availableUsers ?? []).map(toSelectableUser);
  const [selected, setSelected] = useState<string[]>([]);

  const toggleUser = (id: string) => {
    setSelected((prev) =>
      prev.includes(id)
        ? prev.filter((i) => i !== id)
        : [...prev, id]
    );
  };

  // 👉 Añadir usuarios al grupo
  const addToGroup = () => {
    if (!group) return;

    const toAdd = group.availableUsers.filter(u => selected.includes(u.id));

    updateGroup({
      users: [...group.users, ...toAdd],
      availableUsers: group.availableUsers.filter(u => !selected.includes(u.id)),
    });

    setSelected([]);
  };

  // 👉 Quitar usuarios del grupo
  const removeFromGroup = () => {
    if (!group) return;

    const toRemove = group.users.filter(u => selected.includes(u.id));

    updateGroup({
      users: group.users.filter(u => !selected.includes(u.id)),
      availableUsers: [...group.availableUsers, ...toRemove],
    });

    setSelected([]);
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

"use client";

import { VARIANTS } from "@/components/ui/theme/variants";
import { useContext, useState } from "react";
import { UnsavedContext, RoleContext } from "../layout";
import RolePermission from "@/modules/role/components/RolePermissions";
import { Role } from "@/modules/role/type/role"

const permissionSections = [
    {
        title: "Usuarios",
        permissions: [
            { id: "user.view", label: "Ver usuarios" },
            { id: "user.edit", label: "Editar usuarios" },
            { id: "user.delete", label: "Eliminar usuarios" },
        ],
    },
    {
        title: "Incidencias",
        permissions: [
            { id: "incident.view", label: "Ver incidencias" },
            { id: "incident.create", label: "Crear incidencias" },
            { id: "incident.close", label: "Cerrar incidencias" },
        ],
    },
];

export default function RoleGeneralPage() {
  const { role, setRole, loading } = useContext(RoleContext);
  const { setHasUnsavedChanges } = useContext(UnsavedContext);
  const [permissions, setPermissions] = useState<string[]>([]);

  const handleRoleChange = (updatedRole: Role) => {
    setRole(updatedRole);
    setHasUnsavedChanges(true);
  };

  if (loading) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Cargando rol...</div>;
  }

  if (!role) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Error al cargar el rol...</div>;
  }

  return (
    <div className="space-y-3">
      <RolePermission
        sections={permissionSections}
        value={permissions}
        onChange={setPermissions}
      />
    </div>
  );
}

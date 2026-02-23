"use client";

import { VARIANTS } from "@/components/ui/theme/variants";
import { useContext} from "react";
import { UnsavedContext, RoleContext } from "../layout";
import RoleUsers from "@/modules/role/components/RoleUsers";
import { Role } from "@/modules/role/type/role"

export default function RoleGeneralPage() {
    const { role, setRole, loading } = useContext(RoleContext);
  const { setHasUnsavedChanges } = useContext(UnsavedContext);

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
    <div className="h-full space-y-3">
      <RoleUsers  />
    </div>
  );
}


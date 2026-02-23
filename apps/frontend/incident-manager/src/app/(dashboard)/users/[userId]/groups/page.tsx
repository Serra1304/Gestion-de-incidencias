"use client";

import { VARIANTS } from "@/components/ui/theme/variants";
import { useContext} from "react";
import { UnsavedContext, UserContext } from "../layout";
import UserGroup from "@/modules/user/components/UserGroup";
import { User } from "@/modules/user/type/user"

export default function RoleGeneralPage() {
    const { user, setUser, loading } = useContext(UserContext);
  const { setHasUnsavedChanges } = useContext(UnsavedContext);

  const handleRoleChange = (updateUser: User) => {
    setUser(updateUser);
    setHasUnsavedChanges(true);
  };

    if (loading) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Cargando usuario...</div>;
  }

    if (!user) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Error al cargar el usuario...</div>;
  }

  return (
    <div className="h-full space-y-3">
      <UserGroup  />
    </div>
  );
}


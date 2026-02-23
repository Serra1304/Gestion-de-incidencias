"use client";

import { VARIANTS } from "@/components/ui/theme/variants";
import { useContext} from "react";
import { UserContext } from "../layout";
import UserGeneral from "@/modules/user/components/UserGeneral";

export default function UserGeneralPage() {
  const { user, loading } = useContext(UserContext);

    if (loading) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Cargando usuario...</div>;
  }

    if (!user) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Error al cargar el usuario...</div>;
  }

  return (
    <div className="space-y-3">
      <UserGeneral />
    </div>
  );
}

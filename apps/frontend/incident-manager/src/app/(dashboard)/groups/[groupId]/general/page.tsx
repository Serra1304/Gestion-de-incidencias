"use client";

import { VARIANTS } from "@/components/ui/theme/variants";
import { useContext} from "react";
import { GroupContext } from "../layout";
import GroupGeneral from "@/modules/workGroup/components/groupGeneral";

export default function GroupGeneralPage() {
    const { group, loading } = useContext(GroupContext);

    if (loading) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Cargando grupo...</div>;
  }

    if (!group) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Error al cargar el grupo...</div>;
  }

  return (
    <div className="space-y-3">
      <GroupGeneral />
    </div>
  );
}

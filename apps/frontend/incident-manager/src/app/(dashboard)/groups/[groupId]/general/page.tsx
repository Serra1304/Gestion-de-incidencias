"use client";

import { VARIANTS } from "@/components/ui/theme/variants";
import { useContext} from "react";
import { UnsavedContext, GroupContext } from "../layout";
import { GroupFull } from "@/modules/workGroup/type/groupFull"
import GroupGeneral from "@/modules/workGroup/components/groupGeneral";

export default function GroupGeneralPage() {
    const { group, setGroup, loading } = useContext(GroupContext);
  const { setHasUnsavedChanges } = useContext(UnsavedContext);

  const handleGroupChange = (updatedGroup: GroupFull) => {
    setGroup(updatedGroup);
    setHasUnsavedChanges(true);
  };

    if (loading) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Cargando grupo...</div>;
  }

    if (!group) {
    return <div className={`flex items-center justify-center  h-full ${VARIANTS.standard.textDisable}`}>Error al cargar el grupo...</div>;
  }

  return (
    <div className="space-y-3">
      <GroupGeneral group={group} onChange={handleGroupChange} />
    </div>
  );
}

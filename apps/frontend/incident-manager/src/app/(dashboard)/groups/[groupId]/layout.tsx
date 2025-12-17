"use client";

import React, { useEffect, useState, createContext } from "react";
import Link from "next/link";
import { useParams, usePathname } from "next/navigation";
import clsx from "clsx";
import { fetchFullGroup } from "@/data/workGroup.api";
import { GroupFull } from "@/modules/workGroup/type/groupFull"
import Button from "@/components/ui/Button";

export const GroupContext = createContext<{
  group: GroupFull | null;
  setGroup: (r: GroupFull) => void;
  refreshGroup: () => Promise<void>;
  loading: boolean;
}>({
  group: null,
  setGroup: () => { },
  refreshGroup: async () => { },
  loading: true,
});

export const UnsavedContext = createContext<{ setHasUnsavedChanges: (v: boolean) => void }>({
  setHasUnsavedChanges: () => { },
});

const tabs = [
  { name: "General", href: "general" },
  { name: "Usuarios", href: "users" },
];

export default function GroupRightLayout({ children }: { children: React.ReactNode }) {
  const params = useParams();
  const pathname = usePathname();
  const groupId = params?.groupId as string;

  const [group, setGroups] = useState<GroupFull | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [hasUnsavedChanges, setHasUnsavedChanges] = useState<boolean>(false);

  async function loadgroup() {
    if (!groupId) return;
    setLoading(true);
    const r = await fetchFullGroup(groupId);
    setGroups(r);
    setLoading(false);
  }

  useEffect(() => {
    loadgroup();
  }, [groupId]);

  const handleNew = () => console.log("Nuevo grupo");
  const handleSave = () => {
    console.log("Guardar grupo");
    setHasUnsavedChanges(false);
  };
  const handleDelete = () => {
    if (group && confirm(`¿Eliminar el grupo "${group.name}"?`)) {
      console.log("Eliminar grupo", group.id);
    }
  };


  // 🧠 Evitamos renderizar mientras no exista roleId
  if (!groupId) {
    return (
      <div className="flex items-center justify-center h-full text-gray-400">
        Cargando grupo...
      </div>
    );
  }

  return (
    <GroupContext.Provider value={{ group: group, setGroup: setGroups, refreshGroup: loadgroup, loading }}>
      <UnsavedContext.Provider value={{ setHasUnsavedChanges }}>
        <div className="flex flex-col h-full">
          {/* Encabezado */}
          <header className="flex items-start justify-between p-4 border-b border-emerald-700">
            <div>
              {loading ? (
                <div className="animate-pulse space-y-2">
                  <div className="h-7 w-56 bg-gray-700 rounded" />
                  <div className="h-5 w-80 bg-gray-800 rounded mt-2" />
                </div>
              ) : group ? (
                <div>
                  <h1 className="text-3xl font-bold text-white">{group.name}</h1>
                  {group.description && (
                    <p className="text-sm text-gray-400">{group.description}</p>
                  )}
                </div>
              ) : (
                <div className="text-gray-400">Rol no encontrado</div>
              )}
            </div>

            <div className="flex items-center gap-2">
              {hasUnsavedChanges && (
                <span className="text-sm text-yellow-400 mr-2">
                  Cambios sin guardar
                </span>
              )}
              <Button onClick={handleNew}>Nuevo</Button>
              <Button onClick={handleSave}>Guardar</Button>
              <Button onClick={handleDelete}>
                Eliminar
              </Button>
            </div>
          </header>

          {/* Tabs */}
          <nav className="flex border-b border-emerald-700">
            {tabs.map((tab) => {
              const isActive = pathname.endsWith(`/${tab.href}`);
              return (
                <Link
                  key={tab.href}
                  href={`/groups/${groupId}/${tab.href}`}
                  className={clsx(
                    "px-4 py-3 inline-block text-sm font-medium transition-colors",
                    isActive
                      ? "border-b-2 border-emerald-500 text-emerald-400"
                      : "border-b-2 border-transparent text-white hover:text-emerald-300"
                  )}
                >
                  {tab.name}
                </Link>
              );
            })}
          </nav>

          {/* Contenido dinámico del tab */}
          <section className="flex-1 overflow-auto p-4">{children}</section>
        </div>
      </UnsavedContext.Provider>
    </GroupContext.Provider>
  );
}

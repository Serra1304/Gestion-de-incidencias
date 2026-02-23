"use client";

import React, { useEffect, useState, createContext } from "react";
import Link from "next/link";
import { useParams, usePathname } from "next/navigation";
import clsx from "clsx";
import { fetchRoleById } from "@/data/role";
import { Role } from "@/modules/role/type/role"
import Button from "@/components/ui/Button";

export const RoleContext = createContext<{
  role: Role | null;
  setRole: (r: Role) => void;
  refreshRole: () => Promise<void>;
  loading: boolean;
}>({
  role: null,
  setRole: () => { },
  refreshRole: async () => { },
  loading: true,
});

export const UnsavedContext = createContext<{ setHasUnsavedChanges: (v: boolean) => void }>({
  setHasUnsavedChanges: () => { },
});

const tabs = [
  { name: "General", href: "general" },
  { name: "Usuarios", href: "users" },
  { name: "Permisos", href: "permissions" },
];

export default function RoleRightLayout({ children }: { children: React.ReactNode }) {
  const params = useParams();
  const pathname = usePathname();
  const roleId = params?.roleId as string;

  const [role, setRole] = useState<Role | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [hasUnsavedChanges, setHasUnsavedChanges] = useState<boolean>(false);

  async function loadRole() {
    if (!roleId) return;
    setLoading(true);
    const r = await fetchRoleById(roleId);
    setRole(r);
    setLoading(false);
  }

  useEffect(() => {
    loadRole();
  }, [roleId]);

  const handleNew = () => console.log("Nuevo rol");
  const handleSave = () => {
    console.log("Guardar rol");
    setHasUnsavedChanges(false);
  };
  const handleDelete = () => {
    if (role && confirm(`¿Eliminar el rol "${role.name}"?`)) {
      console.log("Eliminar rol", role.id);
    }
  };


  // 🧠 Evitamos renderizar mientras no exista roleId
  if (!roleId) {
    return (
      <div className="flex items-center justify-center h-full text-gray-400">
        Cargando rol...
      </div>
    );
  }

  return (
    <RoleContext.Provider value={{ role, setRole, refreshRole: loadRole, loading }}>
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
              ) : role ? (
                <div>
                  <h1 className="text-3xl font-bold text-white">{role.name}</h1>
                  {role.description && (
                    <p className="text-sm text-gray-400">{role.description}</p>
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
              <Button variant="danger" onClick={handleDelete}>
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
                  href={`/roles/${roleId}/${tab.href}`}
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
    </RoleContext.Provider>
  );
}

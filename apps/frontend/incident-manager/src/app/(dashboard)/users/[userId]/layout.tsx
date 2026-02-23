"use client";

import React, { useEffect, useState, createContext } from "react";
import Link from "next/link";
import { useParams, usePathname } from "next/navigation";
import clsx from "clsx";
import { createUser, fetchUserById, updateUser } from "@/data/user.api";
import { UserFull } from "@/modules/user/type/userFull"
import Button from "@/components/ui/Button";
import { VARIANTS } from "@/components/ui/theme/variants";
import { createEmptyUser } from "@/modules/user/services/UserFactory";

export const UserContext = createContext<{
  user: UserFull | null;
  updateUser: (patch: Partial<UserFull>) => void;
  setPassword?: (pwd: string | null) => void;
  refreshUser: () => Promise<void>;
  loading: boolean;
}>({
  user: null,
  updateUser: () => { },
  refreshUser: async () => { },
  loading: true,
});

export const UnsavedContext = createContext<{ setHasUnsavedChanges: (v: boolean) => void }>({
  setHasUnsavedChanges: () => { },
});

const tabs = [
  { name: "General", href: "general" },
  { name: "Roles", href: "roles" },
  { name: "Grupos", href: "groups" },
];

export default function UserRightLayout({ children }: { children: React.ReactNode }) {
  const params = useParams();
  const pathname = usePathname();
  const userId = params?.userId as string;

  const [user, setUser] = useState<UserFull | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [hasUnsavedChanges, setHasUnsavedChanges] = useState<boolean>(false);
  const [isNewUser, setIsNewUser] = useState<boolean>(false);
  const [password, setPassword] = useState<string | null>(null);

  async function loadUser() {
    if (!userId) return;
    setLoading(true);
    const r = await fetchUserById(userId);
    setUser({ ...r });
    setLoading(false);
  }


  useEffect(() => {
    loadUser();
  }, [userId]);

  const handleNew = async() => {
    setUser(await createEmptyUser());
    setHasUnsavedChanges(true);
    setIsNewUser(true);
  };

  /** Guardar usuario */
  const handleSave = async () => {
    if (!user) return;

    try {
      const payload: any = {
        name: user.name,
        lastName: user.lastName,
        secondLastName: user.secondLastName,
        address: user.address,
        addressNumber: user.addressNumber,
        city: user.city,
        province: user.province,
        postalCode: user.postalCode,
        phone: user.phone,
        phoneBusiness: user.phoneBusiness,
        phoneExtension: user.phoneExtension,
        email: user.email,
        active: user.active,
        groups: user.groups.map(g => g.id),
      };

      if (password) {
        payload.password = password;
        console.log("Se actualizará la contraseña: ", password);
      }

      if (isNewUser || !user.id) {
        await createUser(payload);
        setIsNewUser(false);
      } else {
        await updateUser(userId, payload);
      }

      await loadUser();
      setHasUnsavedChanges(false);

    } catch (e) {
      console.error("Error al guardar usuario", e);
    }
  };



  const handleDelete = () => {
    if (user && confirm(`¿Eliminar el usuario "${user.name}"?`)) {
      console.log("Eliminar usuario", user.id);
    }
  };

  const handleUserChange = (patch: Partial<UserFull>) => {
    setUser(prev => {
      if (!prev) return prev;
      return { ...prev, ...patch };
    });
    setHasUnsavedChanges(true);
  };

  return (
    <UserContext.Provider value={{ user, updateUser: handleUserChange, setPassword, refreshUser: loadUser, loading }}>
      <UnsavedContext.Provider value={{ setHasUnsavedChanges }}>
        {!userId ? (
          <div className="flex items-center justify-center h-full text-gray-400">
            Cargando usuario...
          </div>
        ) : (
          <div className="flex flex-col h-full">
            {/* Encabezado */}
            <header className="flex items-start justify-between p-4 border-b border-emerald-700">
              <div>
                {loading ? (
                  <div className="animate-pulse space-y-2">
                    <div className="h-7 w-56 bg-gray-700 rounded" />
                    <div className="h-5 w-80 bg-gray-800 rounded mt-2" />
                  </div>
                ) : user ? (
                  <div>
                    <h1 className="text-3xl font-bold text-white">{user.name}</h1>
                    {user.lastName && (
                      <p className="text-sm text-gray-400">{user.lastName} {user.secondLastName}</p>
                    )}
                  </div>
                ) : (
                  <div className="text-gray-400">Usuario no encontrado</div>
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
                    href={`/users/${userId}/${tab.href}`}
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
            <section className={`flex-1 overflow-auto p-4 scrollbar scrollbar-track-white/00 ${VARIANTS.standard.scroll2}`}>{children}</section>
          </div>
        )}
      </UnsavedContext.Provider>
    </UserContext.Provider>
  );
}


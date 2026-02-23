"use client";

import Sidebar from "@/components/ui/Sidebar";
import { fetchUsers } from "@/data/user.api";
import { fetchGroups } from "@/data/workGroup.api";
import { UserInfo } from "@/modules/user/type/userInfo";
import { Group } from "@/modules/workGroup/type/group";
import { GroupListItem } from "@/modules/workGroup/type/groupListItem";
import { useRouter, usePathname } from "next/dist/client/components/navigation";
import { createContext, useEffect, useState } from "react";

export const ReferenceDataContext = createContext<{
  groups: Group[];
  /*roles: RoleInfo[];*/
  loading: boolean;
}>({
  groups: [],
  /*roles: [],*/
  loading: true,
});

export default function UserLayout({ children, }: Readonly<{ children: React.ReactNode; }>) {
    const [users, setUsers] = useState<UserInfo[]>([]);
    const [selectedUserId, setSelectedUserId] = useState<string | null>(null);

    const [groups, setGroups] = useState<GroupListItem[]>([]);
    /*const [roles, setRoles] = useState<RoleInfo[]>([]);*/
    const [loadingRefs, setLoadingRefs] = useState(true);

    const router = useRouter();
    const pathname = usePathname();

    useEffect(() => {
        async function load() {
            setLoadingRefs(true);

            const [userData, groupsData, /*rolesData*/] = await Promise.all([
                fetchUsers(),
                fetchGroups(),
                /*fetchRoles()*/
            ]);
            
            setUsers(userData);
            setGroups(groupsData);
            /*setRoles(rolesData);*/

            const alreadySelected = /^\/users\/[^/]+/.test(pathname);

            if (userData.length > 0 && !alreadySelected) {
                const firstId = userData[0].id;
                setSelectedUserId(firstId);
                router.replace(`/users/${firstId}/general`);
            }

            setLoadingRefs(false);
        }

        load();
    }, [pathname]);

    const handleSelectUser = (userId: string) => {
        setSelectedUserId(userId);
        router.push(`/users/${userId}/general`);
    };

    return (
        <ReferenceDataContext.Provider value={{ groups, /*roles,*/ loading: loadingRefs }}>
        <div className="flex h-screen px-20 py-10">
            {/* Sidebar con usuarios */}
            <Sidebar
                title="Usuarios"
                items={users.map(u => ({
                    id: u.id,
                    label: u.name + " " + u.lastName + " " + u.secondLastName,
                }))}
                selectedId={selectedUserId}
                onSelect={handleSelectUser}
            />

            <div className="flex-1 overflow-auto">
                {children}
            </div>
        </div>
        </ReferenceDataContext.Provider>
    );
}

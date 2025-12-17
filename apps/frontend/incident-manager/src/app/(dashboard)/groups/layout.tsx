"use client";

import { useEffect, useState } from "react";
import { useRouter, usePathname } from "next/navigation";
import Sidebar from "@/components/ui/Sidebar";
import { fetchGroups } from "@/data/workGroup.api";
import { GroupListItem } from "@/modules/workGroup/type/groupListItem";

export default function GroupLayout({ children }: { children: React.ReactNode }) {
    const [groups, setGroups] = useState<GroupListItem[]>([]);
    const [selectedGroupId, setSelectedGroupId] = useState<string | null>(null);

    const router = useRouter();
    const pathname = usePathname();

    useEffect(() => {
        async function loadGroups() {
            const data = await fetchGroups();
            setGroups(data);

            const alreadySelected = /^\/groups\/[^/]+/.test(pathname);

            if (data.length > 0 && !alreadySelected) {
                const firstId = data[0].id;
                setSelectedGroupId(firstId);
                router.replace(`/groups/${firstId}/general`);
            }
        }

        loadGroups();
    }, [pathname]);

    const handleSelectGroup = (groupId: string) => {
        setSelectedGroupId(groupId);
        router.push(`/groups/${groupId}/general`);
    };

    return (
        <div className="flex h-screen px-20 py-10">
            {/* Sidebar con grupos */}
            <Sidebar
                title="Grupos"
                items={groups.map(g => ({
                    id: g.id,
                    label: g.name,
                    description: g.description
                }))}
                selectedId={selectedGroupId}
                onSelect={handleSelectGroup}
            />

            {/* Contenido a la derecha */}
            <main className="flex-1 overflow-auto">
                {children}
            </main>
        </div>
    );
}

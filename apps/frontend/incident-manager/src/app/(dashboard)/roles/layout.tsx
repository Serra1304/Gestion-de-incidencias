"use client";

import { useEffect, useState } from "react";
import { useRouter, usePathname } from "next/navigation";
import Sidebar from "@/components/ui/Sidebar";
import { fetchRoles } from "@/data/role";
import { Role } from "@/modules/role/type/role"
import BarMenu from "@/components/layout/BarMenu";

export default function RolesLayout({ children }: { children: React.ReactNode }) {
	const [roles, setRoles] = useState<Role[]>([]);
	const [selectedRoleId, setSelectedRoleId] = useState<string | null>(null);
	const router = useRouter();
	const pathname = usePathname();

	useEffect(() => {
		async function loadRoles() {
			const data = await fetchRoles();
			setRoles(data);

			// Detectar si ya estás en /roles/[id], para no sobreescribir la navegación
			const isRolePath = /^\/roles\/[^/]+/.test(pathname);

			if (data.length > 0 && !isRolePath) {
				const firstRoleId = data[0].id;
				setSelectedRoleId(firstRoleId);
				// 🔧 usamos directamente el valor, no el estado (que aún no está actualizado)
				router.replace(`/roles/${firstRoleId}/general`);
			}
		}

		loadRoles();
	}, [pathname, router]);

	const handleSelectRole = (roleId: string) => {
		setSelectedRoleId(roleId);
		router.push(`/roles/${roleId}/general`);
	};

	return (
		<div className="flex flex-col h-screen overflow-hidden">
			<BarMenu />
			<div className="flex h-screen px-20 py-10">
				<Sidebar
					title="Roles"
					items={roles.map((r) => ({ id: r.id, label: r.name, description: r.description}))}
					selectedId={selectedRoleId}
					onSelect={handleSelectRole}
				/>

				{/* Contenido derecho */}
				<main className="flex-1 overflow-auto">
					{children}
				</main>
			</div>
		</div>
	);
}

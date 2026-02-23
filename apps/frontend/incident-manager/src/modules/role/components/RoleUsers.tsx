import { useState } from "react";
import SelectableList from "@/components/ui/SelectTableList";
import IconButton from "@/components/ui/IconButton";
import { ChevronDoubleLeftIcon, ChevronDoubleRightIcon } from "@heroicons/react/24/outline";

export default function RoleUserManager() {
	const [roleUsers, setRoleUsers] = useState(["1", "2"]); // IDs en el rol
	const [selected, setSelected] = useState<string[]>([]);
	
	const allUsers = [
		{ id: "1", label: "Juan Pérez" },
		{ id: "2", label: "María López" },
		{ id: "3", label: "Carlos Gómez" },
		{ id: "4", label: "Ana Ruiz" },
	];

	const usersInRole = allUsers.filter((u) => roleUsers.includes(u.id));
	const usersAvailable = allUsers.filter((u) => !roleUsers.includes(u.id));

	const toggleUser = (id: string) => {
		setSelected((prev) =>
			prev.includes(id)
				? prev.filter((i) => i !== id)
				: [...(false ? prev : []), id] // Si quisieras selección simple, podrías limpiar aquí
		);
	};

	const addToRole = () => {
		setRoleUsers((prev) => prev.filter((id) => !selected.includes(id)));
		setSelected([]);
	};

	const removeFromRole = () => {
		setRoleUsers((prev) => [...prev, ...selected]);
		setSelected([]);
	};

	return (
		<div className="flex h-full gap-4 ">
			<div className="w-1/2">
				<SelectableList
					title="Usuarios en el rol"
					items={usersAvailable}
					selected={selected}
					onToggle={toggleUser}
					className="h-full"
				/>
			</div>
			<div className="flex flex-col gap-2 justify-center">
				<IconButton icon={<ChevronDoubleLeftIcon onClick={addToRole} />} />
				<IconButton icon={<ChevronDoubleRightIcon onClick={removeFromRole} />} />
			</div>
			<div className="w-1/2">
				<SelectableList
					title="Usuarios disponibles"
					items={usersInRole}
					selected={selected}
					onToggle={toggleUser}
					className= "h-full"
				/>
			</div>
		</div>
	);
}

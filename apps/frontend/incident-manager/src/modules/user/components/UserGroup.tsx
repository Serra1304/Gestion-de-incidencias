import { useState } from "react";
import SelectableList from "@/components/ui/SelectTableList";
import IconButton from "@/components/ui/IconButton";
import { ChevronDoubleLeftIcon, ChevronDoubleRightIcon } from "@heroicons/react/24/outline";

export default function UserGroup() {
	const [groups, setGroups] = useState(["1", "2"]);
	const [selected, setSelected] = useState<string[]>([]);
	
	const allGroups = [
		{ id: "1", label: "Mantenimiento" },
		{ id: "2", label: "Informatica" },
		{ id: "3", label: "Administracion" },
		{ id: "4", label: "Calidad" },
	];

	const groupTheUser = allGroups.filter((u) => groups.includes(u.id));
	const usersAvailable = allGroups.filter((u) => !groups.includes(u.id));

	const toggleUser = (id: string) => {
		setSelected((prev) =>
			prev.includes(id)
				? prev.filter((i) => i !== id)
				: [...(false ? prev : []), id] // Si quisieras selección simple, podrías limpiar aquí
		);
	};

	const addToRole = () => {
		setGroups((prev) => prev.filter((id) => !selected.includes(id)));
		setSelected([]);
	};

	const removeFromRole = () => {
		setGroups((prev) => [...prev, ...selected]);
		setSelected([]);
	};

	return (
		<div className="flex h-full gap-4 ">
			<div className="w-1/2">
				<SelectableList
					title="Grupos del usuario"
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
					title="Grupos disponibles"
					items={groupTheUser}
					selected={selected}
					onToggle={toggleUser}
					className= "h-full"
				/>
			</div>
		</div>
	);
}

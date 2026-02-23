import { ArrowUturnLeftIcon, HomeIcon, TrashIcon, PencilIcon, UserCircleIcon } from "@heroicons/react/24/outline";
import IconButton from "../ui/IconButton";
import { UserInfo } from "@/modules/user/type/userInfo";

type BarMenuProps = {
    user: UserInfo | null;
};

export default function BarMenu({ user }: BarMenuProps) {

    const fullName = user
        ? `${user.name} ${user.lastName} ${user.secondLastName}`
        : "Invitado";

    return (
        <div className="flex justify-between w-full p-1 gap-1">
            <div className="flex gap-1">
                <IconButton icon={<HomeIcon />} />
                <IconButton icon={<ArrowUturnLeftIcon />} />
            </div>
            <div>
                <IconButton icon={<UserCircleIcon className="text-emerald-700"/>} label={fullName}/>
            </div>
        </div>
    );
}
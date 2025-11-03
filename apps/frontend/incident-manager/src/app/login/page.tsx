import LoginForm from "@/modules/auth/components/LoginForm";
import { UserCircleIcon } from "@heroicons/react/24/outline";
import { THEME } from "@/components/ui/theme/theme";

export default function LoginPage() {
	return (
		<div className="flex flex-col h-screen items-center justify-center">
			<div className={`py-10 rounded-2xl ${THEME.standard.bg.base} shadow-xl shadow-current w-sm`}>

				{/* Icono superior */}
				<div className="flex justify-center mb-5">
					<UserCircleIcon className={`size-50 stroke-1 ${THEME.standard.text.field}`} />
				</div>

				{/* Formulario */}
				<LoginForm />
			</div>
		</div>
	);
}

import { THEME } from "@/components/ui/theme/theme";

export default function ViewIncidentPage() {
    return (
        <div>
            <header className={`flex items-center justify-between border-b ${THEME.standard.border.base} p-4`}>
                <h1 className={`text-2xl font-bold ${THEME.standard.text.base}`}>Vista de incidencia</h1>
            </header>
        </div>
    )
}
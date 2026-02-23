import { cookies } from "next/headers";
import BarMenu from "@/components/layout/BarMenu";

export default async function IncidentsLayout({children,}: Readonly<{children: React.ReactNode;}>) {
    const cookieStore = await cookies();
    const cookie = cookieStore.get("user_info");

    const user = cookie ? JSON.parse(cookie.value) : null;

    return (
        <div className="flex flex-col h-screen overflow-hidden">
            <BarMenu user={user} />
            <div className="flex-1">
                {children}
            </div>
        </div>
    );
}

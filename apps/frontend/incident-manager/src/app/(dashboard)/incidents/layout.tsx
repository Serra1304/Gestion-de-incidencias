import BarMenu from "@/components/layout/BarMenu";

export default function IncidentsLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
    return (
        <div>
          <div className="px-20 py-10">
            {children}
          </div>
        </div>
    );
}
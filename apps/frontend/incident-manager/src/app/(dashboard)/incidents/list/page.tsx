"use client";

import { useState, useEffect } from "react";
import { fetchIncidents } from "@/data/incident";
import IncidentTable from "@/modules/incident/components/IncidentsTable";
import Button from "@/components/ui/Button";
import { THEME } from "@/components/ui/theme/theme";
import type { Incident } from "@/modules/incident/type/incident";
import { useRouter } from "next/navigation";

export default function IncidentsPage() {
  const [incidents, setIncidents] = useState<Incident[]>([]);
  const [filtered, setFiltered] = useState<Incident[]>([]);
  const [query, setQuery] = useState("");
  const route = useRouter();

  useEffect(() => {
    (async () => {
      const data = await fetchIncidents();
      setIncidents(data);
      setFiltered(data);
    })();
  }, []);

  const handleSearch = (value: string) => {
    setQuery(value);
    if (!value) return setFiltered(incidents);

    const lower = value.toLowerCase();
    setFiltered(
      incidents.filter(
        (i) =>
          i.title.toLowerCase().includes(lower) ||
          i.description.toLowerCase().includes(lower)
      )
    );
  };

  return (
    <div className="space-y-6">
      <header className={`flex items-center justify-between border-b ${THEME.standard.border.base} p-4`}>
        <h1 className={`text-2xl font-bold ${THEME.standard.text.base}`}>Incidencias</h1>
        <div className="flex gap-3">
          <Button>Ver</Button>
          <Button>Editar</Button>
          <Button>Cerrar incidencia</Button>
          <Button onClick={() => route.push("/incidents/new")}>Abrir incidencia</Button>
        </div>
      </header>

      <input
        value={query}
        onChange={(e) => handleSearch(e.target.value)}
        placeholder="Buscar incidencia..."
        className={`w-full rounded-lg border ${THEME.standard.border.base} ${THEME.standard.bg.base} ${THEME.standard.text.base} ${THEME.standard.focus.base} px-3 py-2`}
      />

      <IncidentTable incidents={filtered} />

      {filtered.length === 0 && (
        <div className="text-gray-400 text-center py-10">
          No se encontraron incidencias.
        </div>
      )}
    </div>
  );
}

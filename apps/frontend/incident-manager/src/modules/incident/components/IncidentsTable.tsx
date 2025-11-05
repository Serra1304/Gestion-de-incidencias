"use client";

import StatusBadge from "./StatusBadge";
import { INCIDENT_PRIORITY_OPTIONS } from "@/modules/incident/type/incidentPriority";
import type { Incident } from "@/modules/incident/type/incident";
import { THEME } from "@/components/ui/theme/theme";

export default function IncidentTable({ incidents }: { incidents: Incident[] }) {
  return (
    <div className={`overflow-x-auto ${THEME.standard.bg.base} rounded-xl shadow-md`}>
      <table className={`w-full text-sm text-left ${THEME.standard.text.base}`}>
        <thead className={`${THEME.standard.bg.button} ${THEME.standard.text.base} uppercase text-s`}>
          <tr>
            <th className="px-4 py-3">Id</th>
            <th className="px-4 py-3">Título</th>
            <th className="px-4 py-3">Prioridad</th>
            <th className="px-4 py-3">Estado</th>
            <th className="px-4 py-3">Asignado a</th>
            <th className="px-4 py-3">Fecha</th>
          </tr>
        </thead>
        <tbody>
          {incidents.map((i) => {
            const priority = INCIDENT_PRIORITY_OPTIONS.find((p) => p.value === i.priority);
            return (
              <tr key={i.id} className={`border-t ${THEME.standard.border.base} ${THEME.standard.hover.base}`}>
                <td className="px-4 py-3">{i.id}</td>
                <td className="px-4 py-3 font-medium">{i.title}</td>
                <td className="px-4 py-3">{priority?.label || i.priority}</td>
                <td className="px-4 py-3">
                  <StatusBadge status={i.status} />
                </td>
                <td className="px-4 py-3">{i.assignedTo || "—"}</td>
                <td className="px-4 py-3">
                  {i.reportedAt ? new Date(i.reportedAt).toLocaleDateString() : "—"}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

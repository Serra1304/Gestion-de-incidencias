"use client";

import clsx from "clsx";
import { INCIDENT_STATUS_OPTIONS } from "@/modules/incident/type/incidentStatus";
import type { Incident } from "@/modules/incident/type/incident";

export default function StatusBadge({ status }: { status: Incident["status"] }) {
  const option = INCIDENT_STATUS_OPTIONS.find((s) => s.value === status);

  return (
    <span
      className={clsx(
        "px-2 py-1 text-xs font-semibold rounded-lg border",
        "bg-gray-600 text-gray-200 border-gray-500"
      )}
    >
      {option?.label || status}
    </span>
  );
}
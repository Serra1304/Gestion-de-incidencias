"use client";

type Item = {
  id: string;
  label: string;
  description?: string;
};

type SidebarProps = {
  title: string;
  items: Item[];
  selectedId: string | null;
  onSelect: (id: string) => void;
};

export default function Sidebar({ title, items, selectedId, onSelect }: SidebarProps) {
  return (
    <div className="w-1/4 flex flex-col border-r border-emerald-700 bg-emerald-950">
      {/* Header */}
      <div className="p-4 border-b border-emerald-700">
        <h2 className="text-xl font-bold text-white">{title}</h2>
      </div>

      {/* List */}
      <div className="flex-1 overflow-auto">
        {items.map((item) => (
          <button
            key={item.id}
            onClick={() => onSelect(item.id)}
            className={`w-full text-left px-4 py-2 transition-colors ${
              selectedId === item.id
                ? "bg-emerald-700 text-white font-semibold"
                : "text-gray-300 hover:bg-emerald-800 hover:text-white"
            }`}
          >
            <div className="text-base">{item.label}</div>
            {item.description && (
              <div className="text-xs text-gray-400 truncate">{item.description}</div>
            )}
          </button>
        ))}
      </div>
    </div>
  );
}

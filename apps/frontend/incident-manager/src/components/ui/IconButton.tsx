"use client";

type IconButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
  icon: React.ReactNode;
  label?: string;
};

export default function IconButton({
  icon,
  label,
  className = "",
  ...props
}: IconButtonProps) {

  return (
    <button
        className={`bg-black/30 text-white border border-emerald-700 hover:bg-emerald-900 rounded-lg p-1 items-center
            ${label ? "inline-flex gap-2 px-2" :""} ${className}`}
        {...props}
    >
      <span className="size-7 flex items-center justify-center">{icon}</span>
      {label && <span>{label}</span>}
    </button>
  );
}
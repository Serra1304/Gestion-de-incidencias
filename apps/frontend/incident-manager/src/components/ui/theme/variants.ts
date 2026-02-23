export type Variant = "standard" | "light" | "danger";

export const VARIANTS = {
    standard: {
        border: "border-standard-base",
        base: "bg-standard-base",
        bg: "bg-standard-bg",
        hover: "hover:bg-standard-hover",
        focus: "focus:ring-standard-focus",
        scroll: "scrollbar-thumb-standard-scroll",
        scroll2: "scrollbar-thumb-standard-base",
        text: "text-standard-text",
        textDisable: "text-standard-text-disable",
        textFocus: "text-standard-text-focus",
    },

    light: {
        border: "border-light",
        base: "bg-light",
        bg: "",
        hover: "hover:bg-light-hover",
        focus: "",
        scroll: "scrollbar-thumb-standard-base",
        scroll2: "",
        text: "text-light-text",
        textDisable: "",
        textFocus: "",
    },

    danger: {
        border: "border-danger",
        base: "bg-danger",
        bg: "",
        hover: "hover:bg-danger-hover",
        focus: "",
        scroll: "scrollbar-thumb-standard-base",
        scroll2: "",
        text: "text-danger-text",
        textDisable: "",
        textFocus: "",
    },
};
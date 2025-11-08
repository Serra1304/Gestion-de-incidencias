export type Theme = "standard" | "light" | "danger";

export const THEME = {
    standard: {
        border: {
            base: "border-standard-border"
        },
        bg: {
            base: "bg-standard-base",
            field: "bg-standard-field",
            button: "bg-standard-border",
            calendar: "bg-standard-border"
        },
        hover: {
            base: "hover:bg-standard-hover hover:text-standard-text",
        },
        focus: {
            base: "focus:outline-none focus:ring-2 focus:ring-standard-focus",
        },
        scroll: {
            base: "scrollbar-thumb-standard-scroll",
        },
        text: {
            base: "text-standard-text",
            field: "text-standard-field",
            focus: "text-standard-focus",
            disble: "text-standard-disable",
        },
    },

    danger: {
        border: {
            base: "border border-danger-border",
        },
        bg: {
            base: "bg-danger-base",
            field: "",
            button: "",
            calendar: ""
        },
        hover: {
            base: "",
        },
        focus: {
            base: "",
        },
        scroll: {
            base: "",
        },
        text: {
            base: "text-danger-text",
            field: "",
            focus: "",
            disble: "",
        },
    },

    light: {
        border: {
            base: "",
        },
        bg: {
            base: "",
            field: "",
            button: "",
            calendar: ""
        },
        hover: {
            base: "",
        },
        focus: {
            base: "",
        },
        scroll: {
            base: "",
        },
        text: {
            base: "",
            field: "",
            focus: "",
            disble: "",
        },
    }
};
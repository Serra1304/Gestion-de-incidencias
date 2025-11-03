export type Theme = "standard" | "light" | "danger";

export const THEME = {
    standard: {
        border: {
            base: "border border-standard-border"
        },
        bg: {
            base: "bg-standard-base",
            field: "bg-standard-field",
            button: "bg-standard-border",
        },
        hover: {
            base: "hover:bg-standard-hover",
        },
        focus: {

        },
        scroll: {

        },
        text: {
            base: "text-standard-text",
            field: "text-standard-field",

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

        },
        hover: {
            base: "",
        },
        focus: {

        },
        scroll: {

        },
        text: {
            base: "text-danger-text",
            field: "",

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

        },
        hover: {
            base: "",
        },
        focus: {

        },
        scroll: {

        },
        text: {
            base: "",
            field: "",

        },
    }
};
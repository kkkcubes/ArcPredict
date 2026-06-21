import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./hooks/**/*.{js,ts,jsx,tsx}"
  ],

  theme: {
    extend: {
      colors: {
        background: "#0A0A0A",
        foreground: "#FAFAFA",

        primary: "#2563EB",
        success: "#16A34A",
        danger: "#DC2626",

        card: "#111827",
        border: "#1F2937"
      }
    }
  },

  plugins: []
};

export default config;
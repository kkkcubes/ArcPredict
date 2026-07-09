"use client";

import {
  ButtonHTMLAttributes,
  ReactNode,
} from "react";

type ButtonVariant =
  | "primary"
  | "secondary"
  | "success"
  | "danger";

interface ButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  children: ReactNode;
  variant?: ButtonVariant;
  leftIcon?: ReactNode;
  rightIcon?: ReactNode;
  fullWidth?: boolean;
}

const variants: Record<ButtonVariant, string> = {
  primary:
    "bg-[#6D4AFF] hover:bg-[#5B39F2] text-white",

  secondary:
    "bg-white border border-gray-300 hover:bg-gray-50 text-gray-800",

  success:
    "bg-green-600 hover:bg-green-700 text-white",

  danger:
    "bg-red-600 hover:bg-red-700 text-white",
};

export default function Button({
  children,
  variant = "primary",
  leftIcon,
  rightIcon,
  fullWidth = false,
  className = "",
  ...props
}: ButtonProps) {
  return (
    <button
      {...props}
      className={`
        inline-flex
        items-center
        justify-center
        gap-2
        px-5
        py-3
        rounded-2xl
        font-semibold
        transition-all
        duration-300
        hover:scale-[1.02]
        disabled:opacity-50
        disabled:cursor-not-allowed
        ${fullWidth ? "w-full" : ""}
        ${variants[variant]}
        ${className}
      `}
    >
      {leftIcon}

      <span>{children}</span>

      {rightIcon}
    </button>
  );
}
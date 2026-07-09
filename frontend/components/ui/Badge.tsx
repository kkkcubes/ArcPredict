"use client";

import { ReactNode } from "react";

type BadgeVariant =
  | "primary"
  | "success"
  | "danger"
  | "warning"
  | "neutral";

interface BadgeProps {
  children: ReactNode;
  variant?: BadgeVariant;
  className?: string;
}

const variants: Record<BadgeVariant, string> = {
  primary:
    "bg-violet-100 text-violet-700",

  success:
    "bg-green-100 text-green-700",

  danger:
    "bg-red-100 text-red-700",

  warning:
    "bg-amber-100 text-amber-700",

  neutral:
    "bg-gray-100 text-gray-700",
};

export default function Badge({
  children,
  variant = "neutral",
  className = "",
}: BadgeProps) {
  return (
    <span
      className={`
        inline-flex
        items-center
        justify-center
        rounded-full
        px-3
        py-1
        text-xs
        font-semibold
        ${variants[variant]}
        ${className}
      `}
    >
      {children}
    </span>
  );
}
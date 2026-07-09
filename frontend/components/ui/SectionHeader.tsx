"use client";

import { ReactNode } from "react";

interface SectionHeaderProps {
  eyebrow?: string;
  title: string;
  subtitle?: string;
  action?: ReactNode;
}

export default function SectionHeader({
  eyebrow,
  title,
  subtitle,
  action,
}: SectionHeaderProps) {
  return (
    <div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between mb-8">

      <div>

        {eyebrow && (
          <p className="text-sm font-medium text-gray-500">
            {eyebrow}
          </p>
        )}

        <h2 className="mt-1 text-3xl font-bold text-gray-900">
          {title}
        </h2>

        {subtitle && (
          <p className="mt-2 text-gray-500 max-w-2xl">
            {subtitle}
          </p>
        )}

      </div>

      {action && (
        <div className="flex items-center gap-3">
          {action}
        </div>
      )}

    </div>
  );
}
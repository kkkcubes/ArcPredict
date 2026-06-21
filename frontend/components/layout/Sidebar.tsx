"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

const menu = [
  {
    label: "Dashboard",
    href: "/",
  },
  {
    label: "Create Market",
    href: "/create",
  },
  {
    label: "Portfolio",
    href: "/portfolio",
  },
  {
    label: "Analytics",
    href: "/analytics",
  },
  {
    label: "AI Assistant",
    href: "/ai",
  },
  {
    label: "Verification",
    href: "/verification",
  },
];

export default function Sidebar() {
  const pathname =
    usePathname();

  return (
    <aside
      className="
        w-64
        min-h-screen
        border-r
        border-gray-800
        p-4
      "
    >
      <h2
        className="
          text-xl
          font-bold
          mb-6
        "
      >
        ArcPredict
      </h2>

      <div
        className="
          flex
          flex-col
          gap-2
        "
      >
        {menu.map((item) => (
          <Link
            key={item.href}
            href={item.href}
            className={`
              px-4
              py-3
              rounded-xl
              ${
                pathname ===
                item.href
                  ? "bg-blue-600 text-white"
                  : "text-gray-400 hover:bg-gray-900"
              }
            `}
          >
            {item.label}
          </Link>
        ))}
      </div>
    </aside>
  );
}
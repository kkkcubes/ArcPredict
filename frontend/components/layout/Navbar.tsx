"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

const links = [
  {
    label: "Dashboard",
    href: "/",
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
    label: "AI",
    href: "/ai",
  },
  {
    label: "Verification",
    href: "/verification",
  },
  {
    label: "Arc",
    href: "/arc",
  },
];

export default function Navbar() {

  const pathname =
    usePathname();

  return (

    <nav
      className="
        sticky
        top-0
        z-50
        border-b
        border-gray-800
        bg-black/90
        backdrop-blur
      "
    >

      <div
        className="
          container
          flex
          items-center
          justify-between
          py-4
        "
      >

        <Link
          href="/"
          className="
            text-2xl
            font-bold
            bg-gradient-to-r
            from-blue-500
            to-cyan-400
            bg-clip-text
            text-transparent
          "
        >
          ArcPredict
        </Link>

        <div
          className="
            hidden
            md:flex
            items-center
            gap-6
          "
        >

          {links.map(
            (link) => (

              <Link
                key={link.href}
                href={link.href}
                className={
                  pathname ===
                  link.href
                    ? "text-blue-500 font-semibold"
                    : "text-gray-400 hover:text-white transition"
                }
              >
                {link.label}
              </Link>

            )
          )}

        </div>

      </div>

    </nav>

  );
}
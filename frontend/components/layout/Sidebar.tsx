"use client";

import Link from "next/link";
import {
  usePathname,
  useRouter,
} from "next/navigation";
import { useActiveSection } from "@/hooks/useActiveSection";
import clsx from "clsx";

import {
  X,
  LayoutDashboard,
  TrendingUp,
  Bot,
  PieChart,
  Wallet,
  Trophy,
  ShieldCheck,
  Landmark,
  PlusCircle,
  FolderOpen,
  Activity,
  BookOpen,
  LifeBuoy,
} from "lucide-react";

interface SidebarProps {
  isOpen: boolean;
  onClose: () => void;
}

const workspace = [
  {
    icon: LayoutDashboard,
    label: "Dashboard",
    href: "/",
  },
  {
    icon: TrendingUp,
    label: "Markets",
    href: "/#markets",
  },
  {
    icon: Bot,
    label: "AI Insights",
    href: "/#ai",
  },
  {
    icon: Wallet,
    label: "Portfolio",
    href: "/#portfolio",
  },
  {
    icon: PieChart,
    label: "Analytics",
    href: "/#analytics",
  },
  {
    icon: Trophy,
    label: "Leaderboard",
    href: "/#leaderboard",
  },
  {
    icon: ShieldCheck,
    label: "Verification",
    href: "/#verification",
  },
  {
    icon: Landmark,
    label: "Arc Finance",
    href: "/#contracts",
  },
];

const resources = [
  {
    icon: PlusCircle,
    label: "Create Market",
    href: "#create-market",
  },
  {
    icon: FolderOpen,
    label: "My Markets",
    href: "#my-markets",
  },
  {
    icon: Activity,
    label: "Activity Feed",
    href: "#activity",
  },
  {
    icon: BookOpen,
    label: "Documentation",
    href: "#documentation",
  },
  {
    icon: LifeBuoy,
    label: "Support",
    href: "#support",
  },
];

export default function Sidebar({
  isOpen,
  onClose,
}: SidebarProps) {
  const pathname = usePathname();
  const router = useRouter();

const {
  activeSection,
} = useActiveSection();

  return (
    <>
      {/* Mobile Overlay */}

      {isOpen && (
        <div
          onClick={onClose}
          className="
            fixed
            inset-0
            z-40
            bg-black/40
            lg:hidden
          "
        />
      )}

      <aside
        className={clsx(
          `
            fixed
            top-0
            left-0
            z-50
            h-screen
            w-[260px]
            bg-white
            border-r
            border-gray-200
            flex
            flex-col
            transition-transform
            duration-300
          `,
          isOpen
            ? "translate-x-0"
            : "-translate-x-full",
          "lg:translate-x-0"
        )}
      >
        {/* Header */}

        <div className="h-20 border-b border-gray-200 px-6 flex items-center justify-between">

          <h1 className="text-3xl font-black">
            Arc
            <span className="text-[#6D4AFF]">
              Predict
            </span>
          </h1>

          <button
            onClick={onClose}
            className="lg:hidden"
          >
            <X size={24} />
          </button>

        </div>

        {/* Navigation */}

        <div className="flex-1 overflow-y-auto px-4 py-6">

          <p className="mb-4 text-xs uppercase tracking-wider text-gray-400">
            Workspace
          </p>

          <div className="space-y-2">

            {workspace.map((item) => {
              const Icon = item.icon;

              const active =
  pathname === "/"
    ? item.href === "/"
      ? activeSection === "top"
      : activeSection === item.href.replace("/#", "")
    : pathname === item.href;

              return (
                <Link
  key={item.label}
  href={item.href}
  onClick={(e) => {

    onClose();

    if (item.href.startsWith("/#")) {

      e.preventDefault();

      if (pathname !== "/") {

        router.push(item.href);

        return;

      }

      const element = document.getElementById(
        item.href.replace("/#", "")
      );

      if (element) {

        element.scrollIntoView({
          behavior: "smooth",
          block: "start",
        });

      }

    }

  }}
  className={clsx(
                    `
                      flex
                      items-center
                      gap-3
                      rounded-xl
                      px-4
                      py-3
                      transition-all
                    `,
                    active
                      ? "bg-[#6D4AFF] text-white shadow-md"
                      : "text-gray-600 hover:bg-gray-100"
                  )}
                >
                  <Icon size={18} />

                  <span className="font-medium">
                    {item.label}
                  </span>
                </Link>
              );
            })}

          </div>

          {/* Resources */}

          <div className="mt-10">

            <p className="mb-4 text-xs uppercase tracking-wider text-gray-400">
              Resources
            </p>

            <div className="space-y-2">

              {resources.map((item) => {
                const Icon = item.icon;

                return (
                  <Link
                    key={item.label}
                    href={item.href}
                    onClick={onClose}
                    className="
                      flex
                      items-center
                      gap-3
                      rounded-xl
                      px-4
                      py-3
                      text-gray-600
                      transition-all
                      hover:bg-gray-100
                    "
                  >
                    <Icon size={18} />

                    <span>
                      {item.label}
                    </span>
                  </Link>
                );
              })}

            </div>

          </div>

        </div>

      </aside>
    </>
  );
}
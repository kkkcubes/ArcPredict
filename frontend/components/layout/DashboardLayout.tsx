"use client";

import { ReactNode, useState } from "react";

import Sidebar from "./Sidebar";
import TopNavbar from "./TopNavbar";

interface DashboardLayoutProps {
  children: ReactNode;
}

export default function DashboardLayout({
  children,
}: DashboardLayoutProps) {

  const [sidebarOpen, setSidebarOpen] =
    useState(false);

  return (

    <div
      className="
        min-h-screen
        bg-[#F7F8FC]
        overflow-x-hidden
      "
    >

      <Sidebar
        isOpen={sidebarOpen}
        onClose={() => setSidebarOpen(false)}
      />

      <div
        className="
          min-h-screen
          lg:ml-[260px]
          transition-all
          duration-300
        "
      >

        <TopNavbar
          onMenuClick={() =>
            setSidebarOpen(true)
          }
        />

        <main
          className="
            w-full
            max-w-[1800px]
            mx-auto
            px-4
            py-4
            sm:px-5
            sm:py-5
            md:px-6
            md:py-6
            lg:px-8
            lg:py-8
          "
        >

          {children}

        </main>

      </div>

    </div>

  );

}
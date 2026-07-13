"use client";

import Link from "next/link";

import BackButton
  from "@/components/ui/BackButton";

const supportOptions = [

  {
    title: "Discord Community",
    description:
      "Join the ArcPredict community and ask questions.",
    href: "#",
  },

  {
    title: "GitHub Issues",
    description:
      "Report bugs and track development progress.",
    href: "#",
  },

  {
    title: "Email Support",
    description:
      "Contact the ArcPredict team directly.",
    href: "#",
  },

  {
    title: "Frequently Asked Questions",
    description:
      "Find answers to common questions.",
    href: "#",
  },

  {
    title: "Report a Bug",
    description:
      "Submit a bug report or feature request.",
    href: "#",
  },

];

export default function SupportPage() {

  return (

    <main className="container py-10">

      <BackButton />

      <div className="mb-8">

        <h1 className="text-4xl font-bold">
          Support
        </h1>

        <p className="mt-2 text-gray-500">
          Get help, report issues, and connect with the ArcPredict community.
        </p>

      </div>

      <div
        className="
          grid
          grid-cols-1
          md:grid-cols-2
          xl:grid-cols-3
          gap-6
        "
      >

        {supportOptions.map((item) => (

          <Link
            key={item.title}
            href={item.href}
            className="
              dashboard-card
              p-6
              transition-all
              duration-300
              hover:-translate-y-1
              hover:shadow-xl
            "
          >

            <h2 className="text-2xl font-bold">
              {item.title}
            </h2>

            <p className="mt-3 text-gray-500">
              {item.description}
            </p>

          </Link>

        ))}

      </div>

    </main>

  );

}
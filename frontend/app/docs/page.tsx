"use client";

import Link from "next/link";

import BackButton
  from "@/components/ui/BackButton";

const docs = [

  {
    title: "Smart Contracts",
    description:
      "Learn about the deployed ArcPredict smart contracts.",
    href: "#",
  },

  {
    title: "API Reference",
    description:
      "Explore backend REST API endpoints.",
    href: "#",
  },

  {
    title: "User Guide",
    description:
      "Learn how to create markets, trade, and claim rewards.",
    href: "#",
  },

  {
    title: "Developer Guide",
    description:
      "Architecture, backend services, and frontend structure.",
    href: "#",
  },

  {
    title: "GitHub Repository",
    description:
      "Source code and project documentation.",
    href: "#",
  },

];

export default function DocsPage() {

  return (

    <main className="container py-10">

      <BackButton />

      <div className="mb-8">

        <h1 className="text-4xl font-bold">
          Documentation
        </h1>

        <p className="mt-2 text-gray-500">
          Everything you need to build, use, and understand ArcPredict.
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

        {docs.map((doc) => (

          <Link
            key={doc.title}
            href={doc.href}
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
              {doc.title}
            </h2>

            <p className="mt-3 text-gray-500">
              {doc.description}
            </p>

          </Link>

        ))}

      </div>

    </main>

  );

}
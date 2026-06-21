import Link from "next/link";

export default function ArcPage() {
  return (
    <main className="container">

      <h1 className="text-4xl font-bold mb-6">
        Arc Ecosystem
      </h1>

      <div className="grid grid-cols-3 gap-4">

        <Link
          href="/arc/unified-balance"
          className="card p-6"
        >
          Unified Balance
        </Link>

        <Link
          href="/arc/bridge"
          className="card p-6"
        >
          Arc Bridge
        </Link>

        <Link
          href="/arc/deposit"
          className="card p-6"
        >
          Arc Deposits
        </Link>

      </div>

    </main>
  );
}
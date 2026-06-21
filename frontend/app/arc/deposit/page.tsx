export default function DepositPage() {
  return (
    <main className="container">

      <h1 className="text-4xl font-bold mb-6">
        Arc Deposits
      </h1>

      <div className="card p-6">

        <input
          placeholder="Deposit Amount"
          className="
            w-full
            p-3
            border
            border-gray-800
            rounded-xl
            bg-black
          "
        />

      </div>

    </main>
  );
}
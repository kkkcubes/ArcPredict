export default function Loading() {
  return (
    <main className="container flex items-center justify-center min-h-screen">

      <div className="text-center">

        <div className="w-16 h-16 border-4 border-blue-500 border-t-transparent rounded-full animate-spin mx-auto" />

        <h2 className="mt-6 text-2xl font-semibold">
          Loading ArcPredict
        </h2>

        <p className="mt-2 text-gray-400">
          Connecting to Arc Testnet...
        </p>

      </div>

    </main>
  );
}
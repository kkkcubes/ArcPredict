"use client";

interface ErrorProps {
  error: Error;
  reset: () => void;
}

export default function Error({
  error,
  reset,
}: ErrorProps) {

  return (
    <main className="container flex items-center justify-center min-h-screen">

      <div className="card p-8 max-w-xl w-full text-center">

        <h1 className="text-3xl font-bold text-red-500">
          Something went wrong
        </h1>

        <p className="mt-4 text-gray-400 break-words">
          {error.message}
        </p>

        <button
          onClick={() => reset()}
          className="
            mt-6
            px-6
            py-3
            rounded-xl
            bg-blue-600
            hover:bg-blue-700
          "
        >
          Try Again
        </button>

      </div>

    </main>
  );
}
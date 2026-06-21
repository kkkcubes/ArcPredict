"use client";

interface Props {
  count?: number;
}

export default function MarketParticipants({
  count = 0,
}: Props) {

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-4
        "
      >
        Participants
      </h2>

      <div
        className="
          text-5xl
          font-bold
        "
      >
        {count}
      </div>

      <p className="text-gray-400">
        Active Traders
      </p>

    </div>
  );
}
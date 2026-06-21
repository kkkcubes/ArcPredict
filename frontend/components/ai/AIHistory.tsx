"use client";

interface Props {
  history: string[];
}

export default function AIHistory({
  history,
}: Props) {

  return (
    <div className="card p-6">

      <h2
        className="
          text-xl
          font-bold
          mb-4
        "
      >
        AI History
      </h2>

      <div className="space-y-3">

        {history.map(
          (
            item,
            index
          ) => (

            <div
              key={index}
              className="
                border-b
                border-gray-800
                pb-2
              "
            >
              {item}
            </div>
          )
        )}

      </div>

    </div>
  );
}
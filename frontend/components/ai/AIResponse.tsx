interface Props {
  response: string;
}

export default function AIResponse({
  response,
}: Props) {

  if (!response) {
    return null;
  }

  return (
    <div
      className="
        mt-6
        p-4
        rounded-xl
        bg-gray-900
      "
    >
      <h3
        className="
          font-semibold
          mb-2
        "
      >
        Response
      </h3>

      <p>
        {response}
      </p>

    </div>
  );
}
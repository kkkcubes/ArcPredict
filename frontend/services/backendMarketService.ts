const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export async function getMarkets() {

  const response = await fetch(
    `${API_URL}/api/markets`,
    {
      cache: "no-store",
    }
  );

  if (!response.ok) {

    throw new Error(
      "Failed to fetch markets"
    );

  }

  return response.json();

}
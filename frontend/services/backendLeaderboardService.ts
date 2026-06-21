const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export async function getLeaderboard() {

  const response =
    await fetch(
      `${API_URL}/api/leaderboard`,
      {
        cache: "no-store",
      }
    );

  if (!response.ok) {

    throw new Error(
      "Failed to fetch leaderboard"
    );

  }

  return response.json();
}
const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export async function getDashboardStats() {

  const response = await fetch(
    `${API_URL}/api/analytics`,
    {
      cache: "no-store",
    }
  );

  if (!response.ok) {

    throw new Error(
      "Failed to fetch analytics"
    );

  }

  const analytics =
    await response.json();

  return {

    activeMarkets:
      analytics.totalMarkets ?? 0,

    totalVolume:
      analytics.totalVolume ?? 0,

    participants:
      analytics.totalTraders ?? 0,

    openInterest:
      analytics.openInterest ?? 0,

  };

}
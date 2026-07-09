const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export async function getDashboardStats() {

  const response = await fetch(
    `${API_URL}/api/dashboard`,
    {
      cache: "no-store",
    }
  );

  if (!response.ok) {

    throw new Error(
      "Failed to fetch dashboard stats"
    );

  }

  const dashboard =
    await response.json();

  return {

    latestBlock:
      dashboard.latestBlock ?? 0,

    totalMarkets:
      dashboard.totalMarkets ?? 0,

    totalTrades:
      dashboard.totalTrades ?? 0,

    totalVolume:
      dashboard.totalVolume ?? 0,

    activeMarkets:
      dashboard.activeMarkets ?? 0,

    resolvedMarkets:
      dashboard.resolvedMarkets ?? 0,

  };

}
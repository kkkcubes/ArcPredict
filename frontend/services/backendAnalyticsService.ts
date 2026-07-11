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

  totalProtocolFees:
    dashboard.totalProtocolFees ?? 0,

  vaultBalance:
    dashboard.vaultBalance ?? 0,

  totalLiquidity:
    dashboard.totalLiquidity ?? 0,

  totalLockedLiquidity:
    dashboard.totalLockedLiquidity ?? 0,

  totalReleasedLiquidity:
    dashboard.totalReleasedLiquidity ?? 0,

  availableLiquidity:
    dashboard.availableLiquidity ?? 0,

  treasuryUtilization:
    dashboard.treasuryUtilization ?? 0,

  treasuryHealth:
    dashboard.treasuryHealth ?? "Unknown",

  activeMarkets:
    dashboard.activeMarkets ?? 0,

  resolvedMarkets:
    dashboard.resolvedMarkets ?? 0,

};

}
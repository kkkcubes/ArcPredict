const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export const analyticsService = {

  async getAnalytics() {

    const response =
      await fetch(
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

    return response.json();
  },
};
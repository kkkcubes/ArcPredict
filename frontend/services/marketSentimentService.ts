const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export const marketSentimentService = {

  async getMarketSentiment() {

    const response =
      await fetch(

        `${API_URL}/api/analytics/sentiment`,

        {
          cache: "no-store",
        }

      );

    if (!response.ok) {

      throw new Error(
        "Failed to load market sentiment"
      );

    }

    return response.json();

  },

};
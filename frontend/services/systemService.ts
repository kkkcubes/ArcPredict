const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export const systemService = {

  async getMetrics() {

    const response =
      await fetch(

        `${API_URL}/api/system/metrics`,

        {
          cache: "no-store",
        }

      );

    if (!response.ok) {

      throw new Error(
        "Failed to load system metrics"
      );

    }

    return response.json();

  },

};
const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export const activityService = {

  async getActivityFeed() {

    const response =
      await fetch(

        `${API_URL}/api/events`,

        {
          cache: "no-store",
        }

      );

    if (!response.ok) {

      throw new Error(
        "Failed to load activity feed"
      );

    }

    return response.json();

  },

};
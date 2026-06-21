import axios from "axios";

export async function getLeaderboard() {
  const response =
    await axios.get(
      "http://localhost:8080/api/portfolio/leaderboard"
    );

  return response.data;
}
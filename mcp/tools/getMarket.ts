import axios from "axios";

export async function getMarket(
  marketId: number
) {
  const response =
    await axios.get(
      `http://localhost:8080/api/markets/${marketId}`
    );

  return response.data;
}
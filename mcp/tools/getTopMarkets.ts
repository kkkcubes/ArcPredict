import axios from "axios";

export async function getTopMarkets() {
  const response =
    await axios.get(
      "http://localhost:8080/api/markets"
    );

  return response.data
    .sort(
      (a: any, b: any) =>
        b.totalVolume - a.totalVolume
    )
    .slice(0, 10);
}
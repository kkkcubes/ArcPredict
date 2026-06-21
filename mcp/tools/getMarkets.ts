import axios from "axios";

export async function getMarkets() {
  const response =
    await axios.get(
      "http://localhost:8080/api/markets"
    );

  return response.data;
}
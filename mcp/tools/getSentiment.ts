import axios from "axios";

export async function getSentiment() {
  const response =
    await axios.get(
      "http://localhost:8080/api/analytics"
    );

  return {
    bullish:
      response.data.bullishPercentage,
    bearish:
      response.data.bearishPercentage
  };
}
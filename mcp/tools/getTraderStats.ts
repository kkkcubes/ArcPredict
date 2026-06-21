import axios from "axios";

export async function getTraderStats() {
  const response =
    await axios.get(
      "http://localhost:8080/api/analytics"
    );

  return {
    traders:
      response.data.totalTraders
  };
}
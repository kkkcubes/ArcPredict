import axios from "axios";

export async function getVolume() {
  const response =
    await axios.get(
      "http://localhost:8080/api/analytics"
    );

  return {
    totalVolume:
      response.data.totalVolume
  };
}
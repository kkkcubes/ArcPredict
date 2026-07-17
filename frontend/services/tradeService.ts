const API_URL =
  process.env.NEXT_PUBLIC_API_URL ??
  "http://localhost:8080";

export async function getTrades() {

  const response =
    await fetch(
      `${API_URL}/api/trades`,
      {
        cache: "no-store",
      }
    );

  if (!response.ok) {

    throw new Error(
      "Failed to load trades"
    );

  }

  return response.json();

}

export async function getTradesByWallet(

  wallet: string,

  page = 0,

  size = 20

) {

  const response =
    await fetch(

      `${API_URL}/api/portfolio/transactions/${wallet}?page=${page}&size=${size}`,

      {
        cache: "no-store",
      }

    );

  if (!response.ok) {

    throw new Error(
      "Failed to load wallet trades"
    );

  }

  return response.json();

}
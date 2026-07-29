import axios from "axios";


const BACKEND_URL =
  process.env.BACKEND_URL ||
  "http://arcpredict-backend:8080";


export async function getMarkets() {

  const response =
    await axios.get(
      `${BACKEND_URL}/api/markets`
    );


  return response.data;

}
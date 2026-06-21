import axios from "axios";

export async function getArcStats() {
  return {
    network: "Arc Testnet",
    chainId: 5042002,
    rpc: "https://rpc.testnet.arc.network"
  };
}
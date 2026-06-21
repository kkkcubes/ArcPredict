import {
  createPublicClient,
  webSocket
} from "viem";

import { arcChain }
  from "./arcChain";

export const wsClient =
  createPublicClient({

    chain:
      arcChain,

    transport:
      webSocket(
        "wss://rpc.testnet.arc.network"
      ),

  });
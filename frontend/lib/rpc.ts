import {
  createPublicClient,
  http
} from "viem";

import { arcChain }
  from "./arcChain";

export const publicClient =
  createPublicClient({

    chain:
      arcChain,

    transport:
      http(
        "https://rpc.testnet.arc.network"
      ),

  });
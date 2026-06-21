import {
  createConfig,
  http
} from "wagmi";

import {
  injected,
  walletConnect
} from "wagmi/connectors";

import { arcChain }
  from "./arcChain";

export const wagmiConfig =
  createConfig({

    chains: [
      arcChain,
    ],

    connectors: [

      injected(),

      walletConnect({
        projectId:
          process.env
            .NEXT_PUBLIC_WALLETCONNECT_PROJECT_ID ||
          "arcpredict"
      }),

    ],

    transports: {

      [arcChain.id]:
        http(
          "https://rpc.testnet.arc.network"
        ),

    },

    ssr: true,
  });
import { HardhatUserConfig } from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";
import "@typechain/hardhat";
import * as dotenv from "dotenv";

dotenv.config();

const PRIVATE_KEY = process.env.PRIVATE_KEY || "";
const ARC_RPC_URL =
  process.env.ARC_RPC_URL ||
  "https://rpc.testnet.arc.network";

const config: HardhatUserConfig = {
  solidity: {
    version: "0.8.24",
    settings: {
      optimizer: {
        enabled: true,
        runs: 200
      }
    }
  },

  networks: {
    hardhat: {},

    arcTestnet: {
      url: ARC_RPC_URL,
      chainId: 5042002,
      accounts: PRIVATE_KEY ? [PRIVATE_KEY] : []
    }
  },

  gasReporter: {
    enabled: true,
    currency: "USD"
  },

  mocha: {
    timeout: 120000
  }
};

export default config;
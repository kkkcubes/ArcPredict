import { readContract }
  from "@wagmi/core";

import { wagmiConfig }
  from "@/lib/wagmi";

import { CONTRACTS }
  from "@/lib/contracts";

import ArcUSDCVaultArtifact
  from "@/abi/ArcUSDCVault.json";

export const arcService = {

  async getVaultBalance() {

    return await readContract(
      wagmiConfig,
      {
        address:
          CONTRACTS.arcVault as `0x${string}`,

        abi:
          ArcUSDCVaultArtifact.abi as any,

        functionName:
          "vaultBalance",
      }
    );
  },

  async getAvailableLiquidity() {

    return await readContract(
      wagmiConfig,
      {
        address:
          CONTRACTS.arcVault as `0x${string}`,

        abi:
          ArcUSDCVaultArtifact.abi as any,

        functionName:
          "availableLiquidity",
      }
    );
  },
};
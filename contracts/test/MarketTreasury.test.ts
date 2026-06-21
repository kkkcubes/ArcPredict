import { expect } from "chai";
import { ethers } from "hardhat";

describe("MarketTreasury", function () {

  let treasury: any;

  beforeEach(async () => {

    const Vault =
      await ethers.getContractFactory(
        "ArcUSDCVault"
      );

    const vault =
      await Vault.deploy();

    await vault.waitForDeployment();

    const Treasury =
      await ethers.getContractFactory(
        "MarketTreasury"
      );

    treasury =
      await Treasury.deploy(
        await vault.getAddress()
      );
  });

  it("should deploy",
    async () => {

      expect(
        treasury.target
      ).to.not.equal(
        ethers.ZeroAddress
      );
  });

});
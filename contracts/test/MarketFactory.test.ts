import { expect } from "chai";
import { ethers } from "hardhat";

describe("MarketFactory", function () {

  let factory: any;

  beforeEach(async () => {

    const Factory =
      await ethers.getContractFactory(
        "MarketFactory"
      );

    factory =
      await Factory.deploy();
  });

  it("initial market count is zero",
    async () => {

      expect(
        await factory.marketCount()
      ).to.equal(0);
  });

});
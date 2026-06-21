#!/bin/bash

cd contracts

npx hardhat run \
scripts/deploy.ts \
--network arcTestnet
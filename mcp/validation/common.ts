import { z } from "zod";

export const walletAddressSchema = z
  .string()
  .trim()
  .min(1, "Wallet address is required");

export const marketIdSchema = z
  .number()
  .int()
  .positive("Market ID must be a positive integer");

export const optionalLimitSchema = z
  .number()
  .int()
  .positive()
  .max(100)
  .optional();
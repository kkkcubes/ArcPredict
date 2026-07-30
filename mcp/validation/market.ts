import { z } from "zod";
import { marketIdSchema } from "./common.js";

export const marketDetailsSchema = z.object({
    marketId: marketIdSchema
});

export type MarketDetailsInput =
    z.infer<typeof marketDetailsSchema>;
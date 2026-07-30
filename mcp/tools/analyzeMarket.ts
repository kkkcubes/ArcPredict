import { getMarketSummary } from "./market.js";
import { getMarketSentiment } from "./sentiment.js";
import { getAnalyticsSummary } from "./analytics.js";
import { getLeaderboard } from "./leaderboard.js";
import { getRecentEvents } from "./events.js";

export async function analyzeMarket() {

    const marketSummary =
        await getMarketSummary();

    const sentiment =
        await getMarketSentiment();

    const analytics =
        await getAnalyticsSummary();

    const leaderboard =
        await getLeaderboard();

    const recentEvents =
        await getRecentEvents();

    return {

        marketSummary,

        sentiment,

        analytics,

        leaderboard,

        recentEvents

    };

}
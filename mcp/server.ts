import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StreamableHTTPServerTransport } from "@modelcontextprotocol/sdk/server/streamableHttp.js";


import {
    ListToolsRequestSchema,
    CallToolRequestSchema
} from "@modelcontextprotocol/sdk/types.js";

import http from "http";


import {
    getMarketSummary
} from "./tools/market.js";


import {
    getAnalyticsSummary
} from "./tools/analytics.js";


import {
    getPortfolioSummary
} from "./tools/portfolio.js";


import {
    getMarketSentiment
} from "./tools/sentiment.js";

import {
    getLeaderboard
}
from "./tools/leaderboard.js";

import {
    getRecentEvents
}
from "./tools/events.js";

import {
    getMarketDetails
}
from "./tools/marketDetails.js";

import {
    analyzeMarket
}
from "./tools/analyzeMarket.js";

import { marketDetailsSchema } from "./validation/market.js";

import { formatMcpError } from "./utils/error.js";

import { formatMcpSuccess } from "./utils/success.js";

import { logger } from "./utils/logger.js";

import { config } from "./config.js";

import { registerGracefulShutdown } from "./utils/shutdown.js";

import { isBackendHealthy } from "./services/healthService.js";

import {
    ROUTES,
    SERVICE_NAME,
    HEADERS
} from "./constants.js";

import { sendJson } from "./utils/http.js";



function createServer() {


    const server =
        new Server(
            {
                name:"arcpredict-mcp",
                version:"1.0.0"
            },
            {
                capabilities:{
                    tools:{}
                }
            }
        );





    server.setRequestHandler(
        ListToolsRequestSchema,
        async ()=>{


            return {

                tools:[


                    {
    name:
        "get_market_summary",

    description:
"Retrieve all active prediction markets on ArcPredict, including market ID, question, category, liquidity, trading volume, participant count, market status, and blockchain metadata. Use this tool whenever a user asks about active markets, open markets, available prediction markets, market listings, trading opportunities, or requests an overview of all markets.",

    inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },



                    {
                        name:
                            "get_market_analytics",

                        description:
    "Retrieve aggregated analytics for the ArcPredict protocol, including total trading volume, active markets, participant statistics, market performance, protocol metrics, and overall ecosystem insights. Use this tool whenever a user asks about analytics, statistics, metrics, protocol performance, platform activity, trading volume, or overall market health.",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },



                    {
                        name:
                            "get_portfolio_summary",

                        description:
    "Retrieve a wallet's portfolio on ArcPredict, including active prediction positions, YES and NO holdings, invested amount, trading history, portfolio summary, and participation across markets. Requires a wallet address. Use this tool whenever a user asks about their portfolio, wallet holdings, positions, investments, trades, or overall account activity.",

                        inputSchema:{
                            type:"object",

                            properties:{

                                wallet:{
                                    type:"string",

                                    description:
                                        "Wallet address"
                                }

                            },

                            required:[
                                "wallet"
                            ]
                        }
                    },



                                        {
                        name:
                            "get_market_sentiment",

                        description:
    "Retrieve real-time market sentiment across ArcPredict prediction markets, including bullish and bearish trends, YES versus NO positioning, sentiment percentages, trading bias, and overall market outlook. Use this tool whenever a user asks about market sentiment, bullish or bearish conditions, YES/NO ratios, trader confidence, or the current prediction market outlook.",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },


                    {
                        name:
                            "get_leaderboard",

                        description:
    "Retrieve the ArcPredict trading leaderboard, ranking the most active traders by trading volume, transaction count, market participation, and overall activity. Use this tool whenever a user asks about top traders, leaderboard rankings, most active wallets, highest trading volume, or leading participants on the platform.",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },

                    {
    name:
        "get_recent_events",

    description:
    "Retrieve the most recent blockchain activity on ArcPredict, including market creation, share purchases, settlements, trading events, protocol actions, and event timelines. Use this tool whenever a user asks about recent events, latest activity, recent trades, blockchain history, protocol actions, or what has happened recently on the platform.",

    inputSchema:{
        type:"object",
        properties:{}
    }
},

{
    name:
        "get_market_details",

    description:
    "Retrieve detailed information for a specific ArcPredict prediction market using its market ID, including the market question, category, YES and NO liquidity pools, participant count, trading volume, resolution status, creator, end time, and blockchain metadata. Use this tool whenever a user asks about a specific market, requests market details, wants to inspect a market by ID, or needs information about an individual prediction market.",

    inputSchema:{
        type:"object",

        properties:{

            marketId:{
                type:"number",

                description:
                    "Market ID"
            }

        },

        required:[
            "marketId"
        ]
    }
},

{
    name: "analyze_market",

    description:
    "Generate a comprehensive AI-driven analysis of ArcPredict prediction markets by combining market details, sentiment, trading activity, liquidity, analytics, leaderboard insights, and recent blockchain events. Use this tool whenever a user asks for market analysis, trading recommendations, market outlook, risk assessment, investment insights, or an overall evaluation of a prediction market.",

    inputSchema: {
        type: "object",
        properties: {},
        required: []
    }
},


                ]

            };

        }

    );

    







    server.setRequestHandler(
    CallToolRequestSchema,
    async(request)=>{

        try {



            if(
                request.params.name ===
                "get_market_summary"
            ){


                const result =
                    await getMarketSummary();



                return formatMcpSuccess(result);

            }



            if(
                request.params.name ===
                "get_market_analytics"
            ){


                const result =
                    await getAnalyticsSummary();



                return formatMcpSuccess(result);

            }


            if(
                request.params.name ===
                "get_portfolio_summary"
            ){


                const wallet =
                    String(
                        request.params.arguments?.wallet
                    );



                const result =
                    await getPortfolioSummary(
                        wallet
                    );



                return formatMcpSuccess(result);

            }


if (
    request.params.name ===
    "get_market_sentiment"
) {

    logger.info(
        "Market sentiment tool invoked"
    );

    const result =
        await getMarketSentiment();

    return formatMcpSuccess(result);

}

            

                        if(
                request.params.name ===
                "get_leaderboard"
            ){


                const result =
                    await getLeaderboard();



                return formatMcpSuccess(result);

            }



            if(
    request.params.name ===
    "get_recent_events"
)
{


    const result =
        await getRecentEvents();



    return formatMcpSuccess(result);

}

if(
    request.params.name ===
    "get_market_details"
)
{


    const validationResult =
    marketDetailsSchema.safeParse({
        marketId: Number(
            request.params.arguments?.marketId
        )
    });

if (!validationResult.success) {

    return formatMcpError(
        new Error(
            JSON.stringify({
                error: "Invalid input",
                details: validationResult.error.flatten()
            })
        )
    );

}

const result =
    await getMarketDetails(
        validationResult.data.marketId
    );



    return formatMcpSuccess(result);

}

if (
    request.params.name ===
    "analyze_market"
) {

    const result =
        await analyzeMarket();

    return formatMcpSuccess(result);

}

                        throw new Error(
                "Unknown tool"
            );


        } catch (error) {

    logger.error(
    {
        error
    },
    "TOOL EXECUTION ERROR"
);

    return formatMcpError(error);

}


        }
    );



    return server;

}





const server = createServer();


export const httpServer =
    http.createServer(
        async (req, res) => {

            res.setHeader(
    HEADERS.X_SERVICE,
    SERVICE_NAME
);

res.setHeader(
    "X-Content-Type-Options",
    "nosniff"
);

res.setHeader(
    "Cache-Control",
    "no-store"
);


            if (req.url === ROUTES.HEALTH) {


                sendJson(res, 200, {
    status: "ok",
    service: SERVICE_NAME
});

return;

            }

            if (req.url === ROUTES.READY) {

    const healthy =
        await isBackendHealthy();

    if (!healthy) {

    sendJson(res, 503, {
        status: "unavailable",
        service: SERVICE_NAME
    });

    return;
}

    res.writeHead(
        200,
        {
            "Content-Type": "application/json"
        }
    );

    res.end(
        JSON.stringify({
            status: "ready",
            service: SERVICE_NAME
        })
    );

    return;
}




           if (req.url === ROUTES.MCP) {

    if (req.method !== "POST") {
        res.writeHead(405, {
            "Content-Type": "application/json"
        });

        res.end(
            JSON.stringify({
                error: "Method Not Allowed"
            })
        );

        return;
    }

    logger.info(
        {
            method: req.method,
            url: req.url
        },
        "Incoming MCP Request"
    );

    const server = createServer();

    try {

        const transport =
            new StreamableHTTPServerTransport({
                sessionIdGenerator: undefined
            });

        await server.connect(transport);

        await transport.handleRequest(req, res);

if (res.headersSent) {
    return;
}

        return;

    } catch (error) {

        logger.error(
            { error },
            "MCP REQUEST ERROR"
        );

        if (!res.headersSent) {

            res.writeHead(500);

            res.end(
                JSON.stringify({
                    error: "MCP request failed"
                })
            );
        }

        return;
    }
}

if (!res.headersSent) {
    res.writeHead(404, {
        "Content-Type": "application/json"
    });

    res.end(
        JSON.stringify({
            error: "Not Found"
        })
    );
}

}
);

    httpServer.requestTimeout = config.requestTimeout;



if (import.meta.url === `file://${process.argv[1]}`) {

    httpServer.listen(
        config.port,
        () => {

            logger.info(
    {
        port: config.port,
        requestTimeout: config.requestTimeout
    },
    "ArcPredict MCP Server started"
);

        }
    );

    registerGracefulShutdown(httpServer);

}

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
    "Returns a wallet portfolio including holdings, positions and trading summary. Requires a wallet address. Use this tool when users ask about a specific wallet.",

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
    "Returns bullish and bearish sentiment for active prediction markets based on current trading activity. Use this tool when users ask which markets are bullish or bearish.",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },


                    {
                        name:
                            "get_leaderboard",

                        description:
    "Returns the top traders ranked by trading activity and volume. Use this tool when users ask about the most active traders.",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },

                    {
    name:
        "get_recent_events",

    description:
    "Returns the latest blockchain events including market creation, trades and settlements. Use this tool when users ask about recent protocol activity.",

    inputSchema:{
        type:"object",
        properties:{}
    }
},

{
    name:
        "get_market_details",

    description:
    "Returns detailed information for a specific prediction market identified by market ID, including pools, participants, status and metadata.",

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
        "Provides a complete market intelligence report by combining market summary, sentiment, analytics, leaderboard, and recent events into a single response.",

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

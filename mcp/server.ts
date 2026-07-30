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
        "Returns all active ArcPredict prediction markets including market ID, question, category, liquidity, trading volume, participants and status. Use this tool when the user asks about available markets or requests a market overview.",

    inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },



                    {
                        name:
                            "get_market_analytics",

                        description:
    "Returns protocol analytics including total volume, market statistics, participation metrics and aggregated insights. Use this tool when users ask about protocol performance or analytics.",

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



                return {

                    content:[

                        {

                            type:"text",

                            text:
                                JSON.stringify(
                                    result,
                                    null,
                                    2
                                )

                        }

                    ]

                };

            }







            if(
                request.params.name ===
                "get_market_analytics"
            ){


                const result =
                    await getAnalyticsSummary();



                return {

                    content:[

                        {

                            type:"text",

                            text:
                                JSON.stringify(
                                    result,
                                    null,
                                    2
                                )

                        }

                    ]

                };

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



                return {

                    content:[

                        {

                            type:"text",

                            text:
                                JSON.stringify(
                                    result,
                                    null,
                                    2
                                )

                        }

                    ]

                };

            }







if (
    request.params.name ===
    "get_market_sentiment"
) {

    console.log("Market sentiment tool invoked");

    const result =
        await getMarketSentiment();

    return {

        content: [

            {

                type: "text",

                text:
                    JSON.stringify(
                        result,
                        null,
                        2
                    )

            }

        ]

    };

}

            

                        if(
                request.params.name ===
                "get_leaderboard"
            ){


                const result =
                    await getLeaderboard();



                return {

                    content:[

                        {

                            type:"text",

                            text:
                                JSON.stringify(
                                    result,
                                    null,
                                    2
                                )

                        }

                    ]

                };

            }

            if(
    request.params.name ===
    "get_recent_events"
)
{


    const result =
        await getRecentEvents();



    return {

        content:[

            {

                type:"text",

                text:
                    JSON.stringify(
                        result,
                        null,
                        2
                    )

            }

        ]

    };

}

if(
    request.params.name ===
    "get_market_details"
)
{


    const marketId =
        Number(
            request.params.arguments?.marketId
        );



    const result =
        await getMarketDetails(
            marketId
        );



    return {

        content:[

            {

                type:"text",

                text:
                    JSON.stringify(
                        result,
                        null,
                        2
                    )

            }
            

        ]

    };


}

if (
    request.params.name ===
    "get_market_analytics"
) {

    console.log("Analytics tool invoked");

    const result =
        await getAnalyticsSummary();

    return {

        content: [

            {

                type: "text",

                text:
                    JSON.stringify(
                        result,
                        null,
                        2
                    )

            }

        ]

    };

}








                        throw new Error(
                "Unknown tool"
            );


        } catch (error) {

    console.error(
        "TOOL EXECUTION ERROR:",
        error
    );

    return {

        content: [

            {

                type: "text",

                text:
                    error instanceof Error
                        ? error.message
                        : "Unknown MCP error"

            }

        ],

        isError: true

    };

}


        }
    );



    return server;

}





const server = createServer();


const httpServer =
    http.createServer(
        async (req, res) => {


            if (
                req.url === "/health"
            ) {


                res.writeHead(
                    200,
                    {
                        "Content-Type":
                            "application/json"
                    }
                );


                res.end(
                    JSON.stringify(
                        {
                            status: "ok",
                            service: "ArcPredict MCP"
                        }
                    )
                );


                return;

            }




            if (
                req.url === "/mcp"
            ) {


                console.log(
    "Incoming MCP Request:",
    req.method,
    req.url
);

const server =
    createServer();

                try {



                    const transport =
                        new StreamableHTTPServerTransport(
                            {
                                sessionIdGenerator:
                                    undefined
                            }
                        );



                    await server.connect(
                        transport
                    );



                    await transport.handleRequest(
                        req,
                        res
                    );


                } catch(error) {


                    console.error(
                        "MCP REQUEST ERROR:",
                        error
                    );


                    if (!res.headersSent) {

                        res.writeHead(
                            500
                        );

                        res.end(
                            JSON.stringify(
                                {
                                    error:
                                        "MCP request failed"
                                }
                            )
                        );

                    }

                }


                return;

            }




            res.writeHead(
                404
            );

            res.end();


        }
    );



httpServer.listen(
    3001,
    () => {

        console.error(
            "ArcPredict MCP Server running on port 3001"
        );

    }
);



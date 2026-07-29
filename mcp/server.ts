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
                            "Returns current ArcPredict prediction markets",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },



                    {
                        name:
                            "get_market_analytics",

                        description:
                            "Returns ArcPredict protocol analytics",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },



                    {
                        name:
                            "get_portfolio_summary",

                        description:
                            "Returns ArcPredict portfolio summary for a wallet",

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
                            "Returns current ArcPredict market sentiment",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },


                    {
                        name:
                            "get_leaderboard",

                        description:
                            "Returns top ArcPredict traders leaderboard",

                        inputSchema:{
                            type:"object",
                            properties:{}
                        }
                    },

                    {
    name:
        "get_recent_events",

    description:
        "Returns latest ArcPredict market activity events",

    inputSchema:{
        type:"object",
        properties:{}
    }
},

{
    name:
        "get_market_details",

    description:
        "Returns details of a specific ArcPredict prediction market",

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
}


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









            if(
                request.params.name ===
                "get_market_sentiment"
            ){


                const result =
                    await getMarketSentiment();



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







                        throw new Error(
                "Unknown tool"
            );


        } catch(error) {

            console.error(
                "TOOL EXECUTION ERROR:",
                error
            );

            throw error;

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


                try {


                    const server =
                        createServer();



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



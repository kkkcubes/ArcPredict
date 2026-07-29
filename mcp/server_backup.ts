import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StreamableHTTPServerTransport } from "@modelcontextprotocol/sdk/server/streamableHttp.js";

import {
    ListToolsRequestSchema,
    CallToolRequestSchema
} from "@modelcontextprotocol/sdk/types.js";

import http from "http";

import { getMarkets } from "./tools/getMarkets.js";


const server =
    new Server(
        {
            name: "arcpredict-mcp",
            version: "1.0.0"
        },
        {
            capabilities: {
                tools: {}
            }
        }
    );



server.setRequestHandler(
    ListToolsRequestSchema,
    async () => {

        return {
            tools: [
                {
                    name: "get_market_summary",
                    description:
                        "Returns current ArcPredict prediction markets",
                    inputSchema: {
                        type: "object",
                        properties: {}
                    }
                }
            ]
        };

    }
);



server.setRequestHandler(
    CallToolRequestSchema,
    async (request) => {


        if (
            request.params.name ===
            "get_market_summary"
        ) {


            const markets =
                await getMarkets();



            return {
                content: [
                    {
                        type: "text",
                        text: JSON.stringify(
                            markets,
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

    }
);



async function main() {


    const transport =
        new StreamableHTTPServerTransport({
            sessionIdGenerator: undefined
        });



    await server.connect(
        transport
    );



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
                                service:
                                    "ArcPredict MCP"
                            }
                        )
                    );


                    return;

                }



                if (
                    req.url === "/mcp"
                ) {


                    await transport.handleRequest(
                        req,
                        res
                    );


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

}


main()
.catch(
    error => {

        console.error(
            "MCP startup failed:",
            error
        );

        process.exit(1);

    }
);
import { Server } from "@modelcontextprotocol/sdk/server/index.js";

const server = new Server(
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

console.log("ArcPredict MCP Server Started");
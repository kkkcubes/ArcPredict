export function formatMcpError(error: unknown) {

    return {

        content: [

            {

                type: "text",

                text: JSON.stringify({

                    success: false,

                    error:
                        error instanceof Error
                            ? error.message
                            : "Unknown MCP error"

                })

            }

        ]

    };

}
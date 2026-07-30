export function formatMcpSuccess(data: unknown) {

    return {

        content: [

            {

                type: "text",

                text: JSON.stringify(
                    data,
                    null,
                    2
                )

            }

        ]

    };

}
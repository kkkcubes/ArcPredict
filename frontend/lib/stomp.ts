import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export const stompClient =
  new Client({

    webSocketFactory: () =>
      new SockJS(
  process.env
    .NEXT_PUBLIC_WS_URL!
),

    reconnectDelay: 5000,

    debug: () => {},
  });
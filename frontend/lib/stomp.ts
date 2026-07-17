import SockJS from "sockjs-client";
import {
  Client,
  StompSubscription,
  IMessage,
} from "@stomp/stompjs";

export const stompClient = new Client({

  webSocketFactory: () =>
    new SockJS(
      process.env
        .NEXT_PUBLIC_WS_URL!
    ),

  reconnectDelay: 5000,

  debug: (message) => {
  console.log("[STOMP]", message);
},

});

let connected = false;

const pendingSubscriptions:
{
  destination: string;
  callback: (message: IMessage) => void;
}[] = [];

stompClient.onConnect = () => {

  connected = true;

  pendingSubscriptions.forEach(

    ({
      destination,
      callback,
    }) => {

      stompClient.subscribe(
        destination,
        callback
      );

    }

  );

};

export function connectStomp() {

  if (!stompClient.active) {

    stompClient.activate();

  }

}

export function disconnectStomp() {

  if (stompClient.active) {

    stompClient.deactivate();

  }

}

export function isStompConnected() {

  return connected;

}

export function subscribe(

  destination: string,

  callback: (
    message: IMessage
  ) => void

): (() => void) | undefined {

  connectStomp();

  if (connected) {

    const subscription:
      StompSubscription =

      stompClient.subscribe(

        destination,

        callback

      );

    return () => {

      subscription.unsubscribe();

    };

  }

  pendingSubscriptions.push({

  destination,

  callback,

});

return () => {

  const index =
    pendingSubscriptions.findIndex(

      (subscription) =>

        subscription.destination === destination &&

        subscription.callback === callback

    );

  if (index !== -1) {

    pendingSubscriptions.splice(index, 1);

  }

};

}
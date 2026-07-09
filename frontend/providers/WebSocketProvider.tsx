"use client";

import {
  createContext,
  useContext,
  useEffect,
} from "react";

import {
  stompClient,
} from "@/lib/stomp";

import {
  useTransactionConfirmation,
} from "@/hooks/useTransactionConfirmation";

const WebSocketContext =
  createContext(null);

export function WebSocketProvider({
  children,
}: {
  children: React.ReactNode;
}) {

  useEffect(() => {

    if (
      !stompClient.active
    ) {

      stompClient.activate();

    }

  }, []);

  useTransactionConfirmation();

  return (

    <WebSocketContext.Provider
      value={null}
    >

      {children}

    </WebSocketContext.Provider>

  );
}

export function useWebSocket() {

  return useContext(
    WebSocketContext
  );
}
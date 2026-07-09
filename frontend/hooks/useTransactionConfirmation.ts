"use client";

import { useEffect } from "react";
import { toast } from "react-hot-toast";

import { stompClient } from "@/lib/stomp";

import {
  getTransactionToast,
  removeTransactionToast,
} from "@/lib/transactionToastStore";

interface TransactionConfirmedMessage {
  type: string;
  txHash: string;
  marketId: number;
}

export function useTransactionConfirmation() {

  useEffect(() => {

    let subscription: any;

    const subscribe = () => {

      subscription =
        stompClient.subscribe(

          "/topic/transaction-confirmed",

          (message) => {

            const payload: TransactionConfirmedMessage =
              JSON.parse(
                message.body
              );

            console.log(
              "Transaction confirmed:",
              payload
            );

            const toastId =
              getTransactionToast(
                payload.txHash
              );

            if (toastId) {

              toast.success(
                "Transaction confirmed",
                {
                  id: toastId,
                }
              );

              removeTransactionToast(
                payload.txHash
              );

            }

          }

        );

    };

    if (
      stompClient.connected
    ) {

      subscribe();

    } else {

      stompClient.onConnect =
        subscribe;

    }

    return () => {

      subscription?.unsubscribe();

    };

  }, []);

}
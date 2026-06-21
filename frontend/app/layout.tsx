import "./globals.css";

import type { Metadata }
  from "next";

import Providers
  from "@/components/Providers";

import {
  Toaster,
} from "react-hot-toast";

import {
  WebSocketProvider,
} from "@/providers/WebSocketProvider";

export const metadata: Metadata = {

  title: "ArcPredict",

  description:
    "AI Powered Real-Time Prediction Markets on Arc",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {

  return (
    <html lang="en">

      <body>

        <Providers>

          <WebSocketProvider>

            <Toaster
              position="top-right"
              toastOptions={{
                duration: 4000,
              }}
            />

            {children}

          </WebSocketProvider>

        </Providers>

      </body>

    </html>
  );
}
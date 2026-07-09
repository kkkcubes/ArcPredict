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

import {
  PortfolioProvider,
} from "@/providers/PortfolioProvider";

import {
  AnalyticsProvider,
} from "@/providers/AnalyticsProvider";

import {
  MarketsProvider,
} from "@/providers/MarketsProvider";

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

          <PortfolioProvider>

          <AnalyticsProvider>

           <MarketsProvider> 

            <Toaster
              position="top-right"
              toastOptions={{
                duration: 4000,
              }}
            />

            {children}

          </MarketsProvider>  

          </AnalyticsProvider>

          </PortfolioProvider>

          </WebSocketProvider>

        </Providers>

      </body>

    </html>
  );
}
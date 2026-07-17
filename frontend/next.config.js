const path = require("path");

/** @type {import('next').NextConfig} */
const nextConfig = {

  reactStrictMode: true,

  output: "standalone",

  outputFileTracingRoot: path.join(
    __dirname
  ),

  experimental: {
    optimizePackageImports: [
      "lucide-react",
    ],
  },

  webpack: (config) => {

    config.resolve.fallback = {

      ...config.resolve.fallback,

      "@react-native-async-storage/async-storage":
        false,

      "pino-pretty":
        false,

    };

    return config;

  },

};

module.exports = nextConfig;
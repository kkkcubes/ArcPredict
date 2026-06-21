export default function VerificationPage() {
  return (
    <main className="container">

      <h1 className="text-4xl font-bold mb-6">
        Verification
      </h1>

      <section className="card p-6">

        <div className="space-y-4">

          <p>
            Network:
            Arc Testnet
          </p>

          <p>
            Chain ID:
            5042002
          </p>

          <p>
            PredictionMarket:
            {process.env.NEXT_PUBLIC_PREDICTION_MARKET}
          </p>

          <p>
            Factory:
            {process.env.NEXT_PUBLIC_MARKET_FACTORY}
          </p>

          <p>
            Treasury:
            {process.env.NEXT_PUBLIC_MARKET_TREASURY}
          </p>

          <p>
            RewardDistributor:
            {process.env.NEXT_PUBLIC_REWARD_DISTRIBUTOR}
          </p>

          <p>
            Vault:
            {process.env.NEXT_PUBLIC_USDC_VAULT}
          </p>

        </div>

      </section>

    </main>
  );
}
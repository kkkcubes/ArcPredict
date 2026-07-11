export function formatNumber(
  value: number | null | undefined
) {

  return new Intl.NumberFormat(
    "en-US"
  ).format(
    Number(value ?? 0)
  );

}

export function formatCurrency(
  value: number | null | undefined,
  suffix = "USDC"
) {

  return `${new Intl.NumberFormat(
    "en-US",
    {
      minimumFractionDigits: 0,
      maximumFractionDigits: 2,
    }
  ).format(
    Number(value ?? 0)
  )} ${suffix}`;

}

export function formatPercentage(
  value: number | null | undefined
) {

  return `${Number(
    value ?? 0
  ).toFixed(2)}%`;

}
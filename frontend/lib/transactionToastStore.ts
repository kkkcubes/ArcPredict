const transactionToastStore = new Map<string, string>();

export function registerTransactionToast(
  txHash: string,
  toastId: string
) {
  transactionToastStore.set(txHash, toastId);
}

export function getTransactionToast(
  txHash: string
) {
  return transactionToastStore.get(txHash);
}

export function removeTransactionToast(
  txHash: string
) {
  transactionToastStore.delete(txHash);
}
package io.arcpredict.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import org.web3j.protocol.core.methods.response.EthBlock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BlockScannerServiceTest {

    @Mock
    private ReceiptScannerService receiptScannerService;

    @InjectMocks
    private BlockScannerService blockScannerService;

    @Test
    void shouldNotThrowWhenTransactionListIsEmpty() {

        EthBlock.Block block =
            mock(EthBlock.Block.class);

        when(
            block.getNumber()
        ).thenReturn(
            java.math.BigInteger.ONE
        );

        when(
            block.getTransactions()
        ).thenReturn(
            java.util.Collections.emptyList()
        );

        blockScannerService.scanBlock(
            block
        );

    }

    @Test
void shouldIgnoreTransactionWhenToAddressIsNull() {

    EthBlock.Block block =
        mock(EthBlock.Block.class);

    @SuppressWarnings("unchecked")
    EthBlock.TransactionResult<org.web3j.protocol.core.methods.response.Transaction> txResult =
        mock(EthBlock.TransactionResult.class);

    org.web3j.protocol.core.methods.response.Transaction tx =
        mock(org.web3j.protocol.core.methods.response.Transaction.class);

    when(
        block.getNumber()
    ).thenReturn(
        java.math.BigInteger.ONE
    );

    when(
        block.getTransactions()
    ).thenReturn(
        java.util.List.of(txResult)
    );

    when(
        txResult.get()
    ).thenReturn(
        tx
    );

    when(
        tx.getTo()
    ).thenReturn(
        null
    );

    blockScannerService.scanBlock(
        block
    );

    org.mockito.Mockito.verifyNoInteractions(
        receiptScannerService
    );

}

@Test
void shouldScanReceiptForPredictionMarketTransaction() {

    ReflectionTestUtils.setField(
        blockScannerService,
        "predictionMarketAddress",
        "0xABC"
    );

    EthBlock.Block block =
        mock(EthBlock.Block.class);

    @SuppressWarnings("unchecked")
    EthBlock.TransactionResult<org.web3j.protocol.core.methods.response.Transaction> txResult =
        mock(EthBlock.TransactionResult.class);

    org.web3j.protocol.core.methods.response.Transaction tx =
        mock(org.web3j.protocol.core.methods.response.Transaction.class);

    when(
        block.getNumber()
    ).thenReturn(
        java.math.BigInteger.ONE
    );

    when(
        block.getTransactions()
    ).thenReturn(
        java.util.List.of(txResult)
    );

    when(
        txResult.get()
    ).thenReturn(
        tx
    );

    when(
        tx.getHash()
    ).thenReturn(
        "0x123"
    );

    when(
        tx.getTo()
    ).thenReturn(
        "0xABC"
    );

    blockScannerService.scanBlock(
        block
    );

    verify(
        receiptScannerService
    ).scanReceipt(
        "0x123"
    );

}

@Test
void shouldIgnoreTransactionForDifferentAddress() {

    ReflectionTestUtils.setField(
        blockScannerService,
        "predictionMarketAddress",
        "0xABC"
    );

    EthBlock.Block block =
        mock(EthBlock.Block.class);

    @SuppressWarnings("unchecked")
EthBlock.TransactionResult<org.web3j.protocol.core.methods.response.Transaction> txResult =
    mock(EthBlock.TransactionResult.class);

org.web3j.protocol.core.methods.response.Transaction tx =
    mock(org.web3j.protocol.core.methods.response.Transaction.class);

    when(
        block.getNumber()
    ).thenReturn(
        java.math.BigInteger.ONE
    );

    when(
        block.getTransactions()
    ).thenReturn(
        java.util.List.of(txResult)
    );

    when(
        txResult.get()
    ).thenReturn(
        tx
    );

    when(
        tx.getHash()
    ).thenReturn(
        "0x123"
    );

    when(
        tx.getTo()
    ).thenReturn(
        "0xDEF"
    );

    blockScannerService.scanBlock(
        block
    );

    verifyNoInteractions(
        receiptScannerService
    );

}

@Test
void shouldContinueWhenTransactionProcessingThrowsException() {

    EthBlock.Block block =
        mock(EthBlock.Block.class);

    @SuppressWarnings("unchecked")
    EthBlock.TransactionResult<org.web3j.protocol.core.methods.response.Transaction> txResult =
        mock(EthBlock.TransactionResult.class);

    when(
        block.getNumber()
    ).thenReturn(
        java.math.BigInteger.ONE
    );

    when(
        block.getTransactions()
    ).thenReturn(
        java.util.List.of(txResult)
    );

    when(
        txResult.get()
    ).thenThrow(
        new RuntimeException("boom")
    );

    blockScannerService.scanBlock(
        block
    );

    verifyNoInteractions(
        receiptScannerService
    );

}

@Test
void shouldHandleExceptionWhenGettingTransactions() {

    EthBlock.Block block =
        mock(EthBlock.Block.class);

    when(
        block.getNumber()
    ).thenReturn(
        java.math.BigInteger.ONE
    );

    when(
        block.getTransactions()
    ).thenThrow(
        new RuntimeException("boom")
    );

    blockScannerService.scanBlock(
        block
    );

    verifyNoInteractions(
        receiptScannerService
    );

}

}
package io.arcpredict.service;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;


import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;



class BlockPollingServiceTest {


    private final BlockchainService
        blockchainService =
            mock(
                BlockchainService.class
            );


    private final LogScannerService
        logScannerService =
            mock(
                LogScannerService.class
            );


    private final ReceiptScannerService
        receiptScannerService =
            mock(
                ReceiptScannerService.class
            );


    private final BlockCheckpointService
        blockCheckpointService =
            mock(
                BlockCheckpointService.class
            );


    private final BlockPollingService
        blockPollingService =
            new BlockPollingService(
                blockchainService,
                logScannerService,
                receiptScannerService,
                blockCheckpointService
            );



    @BeforeEach
    void setUp() throws Exception {


        Field field =
            BlockPollingService.class
                .getDeclaredField(
                    "predictionMarketAddress"
                );


        field.setAccessible(
            true
        );


        field.set(
            blockPollingService,
            "0x1234567890123456789012345678901234567890"
        );

    }




    @Test
void shouldInitializeCheckpointWhenNoPreviousBlock()
throws Exception
{


        when(
            blockchainService.getLatestBlock()
        )
        .thenReturn(
            100L
        );


        when(
            blockCheckpointService
                .getLastProcessedBlock()
        )
        .thenReturn(
            null
        );


        blockPollingService.poll();



        verify(
            blockCheckpointService
        )
        .updateLastProcessedBlock(
            BigInteger.valueOf(
                100L
            )
        );


        verify(
            logScannerService,
            never()
        )
        .getLogs(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any()
        );

    }





    @Test
void shouldUpdateCheckpointWhenNoLogsAreFound()
throws Exception 
{


        when(
            blockchainService.getLatestBlock()
        )
        .thenReturn(
            105L
        );


        when(
            blockCheckpointService
                .getLastProcessedBlock()
        )
        .thenReturn(
            BigInteger.valueOf(
                100L
            )
        );


        EthLog ethLog =
            mock(
                EthLog.class
            );


        when(
            ethLog.getLogs()
        )
        .thenReturn(
            Collections.emptyList()
        );


        when(
            logScannerService.getLogs(
                BigInteger.valueOf(101L),
                BigInteger.valueOf(105L),
                "0x1234567890123456789012345678901234567890"
            )
        )
        .thenReturn(
            ethLog
        );



        blockPollingService.poll();



        verify(
            blockCheckpointService
        )
        .updateLastProcessedBlock(
            BigInteger.valueOf(
                105L
            )
        );


        verify(
            receiptScannerService,
            never()
        )
        .scanReceipt(
            org.mockito.ArgumentMatchers.anyString()
        );

    }






    @Test
void shouldScanReceiptWhenLogsAreFound()
throws Exception
{

        when(
            blockchainService.getLatestBlock()
        )
        .thenReturn(
            105L
        );


        when(
            blockCheckpointService
                .getLastProcessedBlock()
        )
        .thenReturn(
            BigInteger.valueOf(
                100L
            )
        );


        EthLog ethLog =
            mock(
                EthLog.class
            );


        Log log =
            mock(
                Log.class
            );


        @SuppressWarnings("unchecked")
        EthLog.LogResult<Log> logResult =
            mock(
                EthLog.LogResult.class
            );


        when(
            logResult.get()
        )
        .thenReturn(
            log
        );


        when(
            ethLog.getLogs()
        )
        .thenReturn(
            List.of(
                logResult
            )
        );


        when(
            log.getTransactionHash()
        )
        .thenReturn(
            "0xabc123"
        );


        when(
            logScannerService.getLogs(
                BigInteger.valueOf(101L),
                BigInteger.valueOf(105L),
                "0x1234567890123456789012345678901234567890"
            )
        )
        .thenReturn(
            ethLog
        );



        blockPollingService.poll();



        verify(
            receiptScannerService
        )
        .scanReceipt(
            "0xabc123"
        );


        verify(
            blockCheckpointService
        )
        .updateLastProcessedBlock(
            BigInteger.valueOf(
                105L
            )
        );

    }




@Test
void shouldHandleExceptionDuringPolling()
throws Exception
{


        when(
            blockchainService.getLatestBlock()
        )
        .thenThrow(
            new RuntimeException(
                "RPC failed"
            )
        );



        blockPollingService.poll();



        verify(
            logScannerService,
            never()
        )
        .getLogs(
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any(),
            org.mockito.ArgumentMatchers.any()
        );


        verify(
            receiptScannerService,
            never()
        )
        .scanReceipt(
            org.mockito.ArgumentMatchers.anyString()
        );

    }






    @Test
void shouldLimitEndBlockToLatestBlock()
throws Exception
{ 
        when(
            blockchainService.getLatestBlock()
        )
        .thenReturn(
            120L
        );


        when(
            blockCheckpointService
                .getLastProcessedBlock()
        )
        .thenReturn(
            BigInteger.valueOf(
                100L
            )
        );


        EthLog ethLog =
            mock(
                EthLog.class
            );


        when(
            ethLog.getLogs()
        )
        .thenReturn(
            Collections.emptyList()
        );



        when(
            logScannerService.getLogs(
                BigInteger.valueOf(101L),
                BigInteger.valueOf(120L),
                "0x1234567890123456789012345678901234567890"
            )
        )
        .thenReturn(
            ethLog
        );



        blockPollingService.poll();



        verify(
            blockCheckpointService
        )
        .updateLastProcessedBlock(
            BigInteger.valueOf(
                120L
            )
        );

    }

    @Test
void shouldKeepEndBlockWhenItIsBelowLatest()
throws Exception {

    when(
        blockchainService.getLatestBlock()
    )
    .thenReturn(
        300L
    );


    when(
        blockCheckpointService
            .getLastProcessedBlock()
    )
    .thenReturn(
        BigInteger.valueOf(
            100L
        )
    );


    EthLog ethLog =
        mock(
            EthLog.class
        );


    when(
        ethLog.getLogs()
    )
    .thenReturn(
        Collections.emptyList()
    );


    when(
        logScannerService.getLogs(
            BigInteger.valueOf(101L),
            BigInteger.valueOf(200L),
            "0x1234567890123456789012345678901234567890"
        )
    )
    .thenReturn(
        ethLog
    );


    blockPollingService.poll();


    verify(
        blockCheckpointService
    )
    .updateLastProcessedBlock(
        BigInteger.valueOf(
            200L
        )
    );

}

}
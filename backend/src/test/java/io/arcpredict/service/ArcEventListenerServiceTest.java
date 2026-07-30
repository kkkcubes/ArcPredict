package io.arcpredict.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;

import io.reactivex.Flowable;


class ArcEventListenerServiceTest {


    private final Web3j web3j =
        mock(Web3j.class);


    private final BlockScannerService
        blockScannerService =
            mock(
                BlockScannerService.class
            );


    private final ArcEventListenerService
        arcEventListenerService =
            new ArcEventListenerService(
                web3j,
                blockScannerService
            );


    @BeforeEach
    void setUp() throws Exception {

        Field field =
            ArcEventListenerService.class
                .getDeclaredField(
                    "predictionMarketAddress"
                );


        field.setAccessible(
            true
        );


        field.set(
            arcEventListenerService,
            "0x1234567890123456789012345678901234567890"
        );

    }



    @Test
    void shouldStartWithoutErrors()
    {

        when(
            web3j.blockFlowable(
                true
            )
        )
        .thenReturn(
            Flowable.empty()
        );


        arcEventListenerService.start();

    }




    @Test
    void shouldScanBlockWhenNewBlockArrives()
    {

        EthBlock.Block block =
            new EthBlock.Block();


        block.setNumber(
            "0x1"
        );


        block.setTransactions(
            Collections.emptyList()
        );


        EthBlock ethBlock =
            new EthBlock();


        ethBlock.setResult(
            block
        );


        when(
            web3j.blockFlowable(
                true
            )
        )
        .thenReturn(
            Flowable.just(
                ethBlock
            )
        );


        arcEventListenerService.start();



        verify(
            blockScannerService
        )
        .scanBlock(
            block
        );

    }





    @Test
    void shouldHandleBlockFlowableError()
    {

        when(
            web3j.blockFlowable(
                true
            )
        )
        .thenReturn(
            Flowable.error(
                new RuntimeException(
                    "Blockchain connection failed"
                )
            )
        );


        arcEventListenerService.start();

    }

}
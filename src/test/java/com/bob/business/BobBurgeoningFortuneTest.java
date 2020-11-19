package com.bob.business;

import com.bob.business.service.impl.CryptoCoinsRateCalculation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.fail;



@ExtendWith(MockitoExtension.class)

public class BobBurgeoningFortuneTest {
    @Spy
    CryptoCoinsRateCalculation rateCalculation;


    @Mock
    HttpURLConnection connection;

    @BeforeAll
    static void setUp() {

    }

    @BeforeEach
    void init() {
    }



    @Test
    void testCommandLineArguments() {

    }

    @Test
    void succeedingTest() {
    }

    @Test
    @Disabled
    void failingTest() {
        fail("a failing test");
    }

    @Test
    @Disabled("for demonstration purposes")
    void skippedTest() {
        // not executed
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }

}

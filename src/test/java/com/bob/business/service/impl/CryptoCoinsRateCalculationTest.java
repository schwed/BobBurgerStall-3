package com.bob.business.service.impl;

import com.bob.business.BobBurgeoningFortune;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;
import java.net.HttpURLConnection;

public class CryptoCoinsRateCalculationTest {

    @Spy
    CryptoCoinsRateCalculation rateCalculation;

    @Mock
    HttpURLConnection connection;

    @Test
    void status() throws IOException {
        int expected = 200;

    }
}

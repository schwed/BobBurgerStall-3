package com.bob.business.service.impl;

import com.bob.business.service.HttpUrlStreamHandler;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CryptoCoinsRateCalculationTest {

    private static HttpUrlStreamHandler httpUrlStreamHandler;
    @BeforeAll
    static void setupURLStreamHandlerFactory() {
        // allow for mocking url connections
        URLStreamHandlerFactory urlStreamHandlerFactory = mock(URLStreamHandlerFactory.class);
        URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);
        httpUrlStreamHandler = new HttpUrlStreamHandler();
        given(urlStreamHandlerFactory.createURLStreamHandler("https")).willReturn(httpUrlStreamHandler);
    }

    @Spy
    CryptoCoinsRateCalculation rateCalculation = new CryptoCoinsRateCalculation();

    @Mock
    HttpURLConnection connection;

    @Test
    void status() throws IOException {
        int expected = 200;
        doReturn(connection).when(rateCalculation).createConnection(any());
        doReturn(expected).when(connection).getResponseCode();
        URL url = new URL("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR");
        int status = rateCalculation.createConnection(url).getResponseCode();

        Assertions.assertEquals(expected, status);
    }

    @BeforeEach
    void reset(){
        httpUrlStreamHandler.resetConnections();
    }

    @Disabled
    @Test
    void shouldReadJsonData() throws Exception {
        String href = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR";
        URLConnection urlConnection = mock(URLConnection.class);
        httpUrlStreamHandler.addConnection(new URL(href), urlConnection);
        IOException ioException = new MalformedURLException(href);

        given(urlConnection.getInputStream()).willThrow(ioException);

        try {
            double rate = rateCalculation.fetchCryptoCoinRate(connection.getInputStream(), "EUR");
            assumeTrue(rate  > 0);
            fail("Was expecting a RuntimeException with IOException");

        } catch (RuntimeException e) {
            assertThat(e.getCause(), equalTo(ioException));
        }
    }


    @Test
    void getCryptoCoinExchangeRate() {
        //BTC=10
        //ETH=5
        //XRP=2000
        String coin = "BTC";
        String currency = "EUR";

    }
}

package com.bob.business.service.impl;

import com.bob.business.service.CryptoFortuneCalculator;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class CryptoCoinsRateCalculation  implements CryptoFortuneCalculator {

    private String buildUrl(String cryptoName, String currency) {
        // https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR

        StringBuilder urlString = new StringBuilder("https://min-api.cryptocompare.com/data/price?fsym=")
                .append(cryptoName)
                .append("&tsyms=")
                .append(currency);

        return urlString.toString();
    }

    public double getCryptoCoinExchangeRate(String cryptoName, String currency) {

        double rate = 0;
        String urlString = buildUrl(cryptoName, currency);
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // read response
            int status = connection.getResponseCode();
            if(status != 200) {
                throw new RuntimeException("HttpResponseCode: " + status);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ( (inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JSONObject obj = new JSONObject(content.toString());
            rate = obj.getDouble(currency);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            if (connection != null) connection.disconnect();
        }

        return rate;
    }
}

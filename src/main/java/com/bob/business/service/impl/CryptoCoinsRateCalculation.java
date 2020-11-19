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

        double rate;
        String urlString = buildUrl(cryptoName, currency);

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = createConnection(url);
            // read response
            int status = connection.getResponseCode();
            if(status != 200) {
                throw new RuntimeException("HttpResponseCode: " + status);
            }

            rate = fetchCryptoCoinRate(connection.getInputStream(), currency);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return rate;
    }

    HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        return urlConnection;
    }

    double fetchCryptoCoinRate(InputStream inputStream, String currency) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JSONObject obj = new JSONObject(content.toString());
        return obj.getDouble(currency);
    }


}

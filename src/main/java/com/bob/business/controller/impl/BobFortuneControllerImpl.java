package com.bob.business.controller.impl;

import com.bob.business.controller.BobFortuneController;
import com.bob.business.service.CryptoFortuneCalculator;
import com.bob.business.service.impl.CryptoCoinsRateCalculation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BobFortuneControllerImpl implements BobFortuneController {

    CryptoFortuneCalculator calculator = new CryptoCoinsRateCalculation();

    @Override
    public List<String> readFile(Path filePath) throws IOException {
        List<String> cryptoCoinsList;

        try (Stream<String> lines = Files.lines(filePath)) {
            cryptoCoinsList = lines.collect(Collectors.toList());
        }

        return cryptoCoinsList;
    }


    @Override
    public double getCryptoCoinValue(String fileLine, String currency) {

        // split file line such as BTC=10
        String[] lineSplit = fileLine.split("=");
        double rate = calculator.getCryptoCoinExchangeRate(lineSplit[0], currency);
        return Double.parseDouble(lineSplit[1]) * rate;
    }
}

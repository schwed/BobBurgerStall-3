package com.bob.business.controller.impl;

import com.bob.business.controller.BobFortuneController;
import com.bob.business.service.CryptoFortuneCalculator;
import com.bob.business.service.impl.CryptoCoinsRateCalculation;

public class BobFortuneControllerImpl implements BobFortuneController {

    CryptoFortuneCalculator calculator = new CryptoCoinsRateCalculation();

    @Override
    public double getCryptoCoinValue(String cryptoCoin, String amount, String currency) {
        double rate = calculator.getCryptoCoinExchangeRate(cryptoCoin, currency);
        return Double.parseDouble(amount) * rate;
    }
}

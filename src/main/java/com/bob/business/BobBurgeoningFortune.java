package com.bob.business;

import com.bob.business.controller.BobFortuneController;
import com.bob.business.controller.impl.BobFortuneControllerImpl;
import com.bob.business.service.CryptoFortuneCalculator;
import com.bob.business.service.impl.CryptoCoinsRateCalculation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class BobBurgeoningFortune {

    private final static Logger logger = Logger.getLogger(BobBurgeoningFortune.class.getName());
    private final static Currency currency = Currency.getInstance(Locale.GERMANY);
    private final static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            logger.info("TO RUN TOOL PLEASE PROVIDE FILE");
            System.exit(-1);
        }

        Path filePath = Paths.get(args[0]);

        if (Files.notExists(filePath, LinkOption.NOFOLLOW_LINKS)) {
            throw new RuntimeException("INVALID FILE LOCATION");
        }


        if (Files.isRegularFile(filePath, LinkOption.NOFOLLOW_LINKS)
                && !Files.isReadable(filePath)) {
            throw new RuntimeException("INPUT FILE DOES NOT HAVE READ PERMISSION");
        }

        BobFortuneController controller = new BobFortuneControllerImpl();
        // read file bob prepared with all his investments
        List<String> cryptoCoinsList = controller.readFile(filePath);

        double totalCoinsValue = 0;

        System.out.println("Bob investment by Crypto Coins:");

        for (String fileLine : cryptoCoinsList) {

            // display each coin in EUR
            double cryptoCoinValue =  controller.getCryptoCoinValue(fileLine, "EUR");

            System.out.println(fileLine + " in " + currency.getDisplayName() + ": " + numberFormat.format(cryptoCoinValue));

            // calculate total
            totalCoinsValue += cryptoCoinValue;
        }

        // display total
        System.out.print("Bobs crypto currency fortune total in " + currency.getDisplayName() + ": " + numberFormat.format(totalCoinsValue));
    }

}

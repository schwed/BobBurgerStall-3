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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BobBurgeoningFortune {

    private final static Logger logger = Logger.getLogger(BobBurgeoningFortune.class.getName());
    static Currency currency = Currency.getInstance(Locale.GERMANY);
    static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);

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

        // read file bob prepared with all his investments ino crypto coins
        List<String> cryptoCoinsList;

        try (Stream<String> stream = Files.lines(filePath)) {
            cryptoCoinsList = stream.collect(Collectors.toList());
        }

        BobFortuneController controller = new BobFortuneControllerImpl();
        double totalCoinsValue = 0;
        System.out.println("Bob investment by Crypto Coins:");
        for (String line : cryptoCoinsList) {
            String[] lineSplit = line.split("=");
            // display each coin in EUR
            double cryptoCoinValue =  controller.getCryptoCoinValue(lineSplit[0], lineSplit[1], "EUR");

            System.out.println(line + " in " + currency.getDisplayName() + ": " + numberFormat.format(cryptoCoinValue));
            totalCoinsValue += cryptoCoinValue;
        }

        // display total
        System.out.print("Bobs crypto currency fortune total in " + currency.getDisplayName() + ": " + numberFormat.format(totalCoinsValue));
        System.out.println("\n");
    }
}

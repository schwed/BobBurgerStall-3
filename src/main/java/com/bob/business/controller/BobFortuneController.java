package com.bob.business.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface BobFortuneController {

    List<String> readFile(Path filePatch) throws IOException;
    double getCryptoCoinValue(String fileLine, String currency);

}

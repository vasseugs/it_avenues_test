package com.itavenues.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Consts {

  public static final String API_V1 = "/api_v1";

  public static final Path AVATARS_CATALOG_PATH = Paths.get(new File("").getAbsolutePath() + "/../avatars");
}

package com.lookingglass.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils
{
    private Utils ()
    {

    }

    public static byte[] loadImgToByteArray(String path) throws IOException {
        File fi = new File(path);
        return Files.readAllBytes(fi.toPath());
    }

}

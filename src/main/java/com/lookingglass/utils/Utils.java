package com.lookingglass.utils;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils
{

    private static org.apache.log4j.Logger log = Logger.getLogger(Utils.class);

    private Utils ()
    {

    }

    public static void logTrace(String _text)
    {
        log.trace("Trace message!");
        log.trace(_text);
    }

    public static void logDebug(String _text)
    {
        log.debug("debug message!");
        log.debug(_text);
    }

    public static void logInfo(String _text)
    {
        log.info("info message!");
        log.info(_text);
    }

    public static void logWarn(String _text)
    {
        log.warn("warn message!");
        log.warn(_text);
    }

    public static void logError(String _text)
    {
        log.error("error message!");
        log.error(_text);
    }
    public static void logFatal(String _text)
    {
        log.fatal("Trace message!");
        log.fatal(_text);
    }


    public static byte[] loadImgToByteArray(String path) throws IOException {
        File fi = new File(path);
        return Files.readAllBytes(fi.toPath());
    }

    public static String getPasswordHash(String _pw)
    {
        return BCrypt.hashpw(_pw, BCrypt.gensalt(12));
    }

    public static Boolean validatePassword(String _pw, String _hash)
    {
        return BCrypt.checkpw(_pw,_hash);
    }



}

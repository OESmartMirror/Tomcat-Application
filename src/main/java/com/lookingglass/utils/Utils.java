package com.lookingglass.utils;

import com.google.gson.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lookingglass.model.UsersEntity;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils
{

    //private static org.apache.log4j.Logger log = Logger.getLogger(Utils.class);

    private static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .serializeNulls()
            .create();;

    private static String webAppRoot = System.getProperty("catalina.base");
    private static String s = File.separator;
    private static String usersDir = webAppRoot + s + "users" + s;
    private static String tempDir = Paths.get(System.getProperty("java.io.tmpdir") )+ s + "users" + s;

    private Utils ()
    {

    }

    /*public static void logTrace(String _text)
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
*/

    public static byte[] loadImgToByteArray(String path) throws IOException
    {
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

    public static String getListAsJson(List<UsersEntity> passedList)
    {
        String returnString = null;
        List<JsonObject> tempJsonObjects = new ArrayList<JsonObject>();
        passedList.stream().forEach
         (
            li ->
            {
                JsonElement tempElement = gson.fromJson(li.userAsJson(),JsonElement.class);
                JsonObject tempObject = tempElement.getAsJsonObject();
                tempJsonObjects.add(tempObject);
            }
        );
        returnString = gson.toJson(tempJsonObjects);
        return returnString;
    }

    /*public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception
    {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 400, 400);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }*/

    public static BufferedImage generateQRCodeImage(String barcodeText)
    {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try
        {
            bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 400, 400);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.err.println(e);
        }

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage getUserQRCodeRegisternImg(UsersEntity user) throws Exception
    {
        String temp = "Register:".concat(user.getLabel());
        return generateQRCodeImage(temp);
    }

    public static BufferedImage getUserQRCodeLoginImg(UsersEntity user) throws Exception
    {
        String temp = "User:".concat(user.getLabel());
        return generateQRCodeImage(temp);
    }

    public static void getUserQRCodeRegister(UsersEntity user)
{
    String temp = "Register:".concat(user.getLabel());
    try {
        BufferedImage tempImage = generateQRCodeImage(temp);
        File outputFile = new File(usersDir + user.getLabel().concat(File.separator).concat("register.png"));
        ImageIO.write(tempImage, "png", outputFile);
    }
    catch (IOException e)
    {
        e.printStackTrace();
        System.out.println(e.getMessage());
        System.err.println(e);
    }
}

    public static void getUserQRCodeLogin(UsersEntity user){
        String temp = "User:".concat(user.getLabel());
        try {
            BufferedImage tempImage = generateQRCodeImage(temp);
            File outputFile = new File(usersDir + user.getLabel().concat(File.separator).concat("login.png"));
            ImageIO.write(tempImage, "png", outputFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.err.println(e);
        }
    }

    public static File getUserQRCodeRegisterAsFile(UsersEntity user)
    {
        String temp = "Register:".concat(user.getLabel());
        //createFolder(user);
        createFolderTemp(user);
        File outputFile = null;
        try {
            BufferedImage tempImage = generateQRCodeImage(temp);
            //outputFile = new File(usersDir + user.getLabel().concat(File.separator).concat("register.png"));
            outputFile = new File(tempDir + user.getLabel().concat(File.separator).concat("register.png"));
            ImageIO.write(tempImage, "png", outputFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.err.println(e);
        }
        return outputFile;
    }

    public static File getUserQRCodeLoginAsFile(UsersEntity user)
    {
        String temp = "User:".concat(user.getLabel());
        //createFolder(user);
        createFolderTemp(user);
        File outputFile = null;
        try {
            BufferedImage tempImage = generateQRCodeImage(temp);
            //outputFile = new File(usersDir + user.getLabel().concat(File.separator).concat("login.png"));
            outputFile = new File(tempDir + user.getLabel().concat(File.separator).concat("login.png"));
            ImageIO.write(tempImage, "png", outputFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.err.println(e);
        }
        return outputFile;
    }




    public static void createQRIMG(UsersEntity user)
    {
        getUserQRCodeRegister(user);
        getUserQRCodeLogin(user);
    }

    public static void createFolder(UsersEntity user)
    {
        File path = new File(usersDir.concat(user.getLabel()));
        try
        {
            Files.createDirectories(path.toPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());System.err.println(e);
        }
    }

    public static void createFolderTemp(UsersEntity user)
    {
        File path = new File(tempDir.concat(user.getLabel()));
        try
        {
            Files.createDirectories(path.toPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());System.err.println(e);
        }
    }

}

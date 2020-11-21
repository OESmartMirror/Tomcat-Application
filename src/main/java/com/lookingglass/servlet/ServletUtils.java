package com.lookingglass.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class ServletUtils
{

    /*public static String getParameter(String paramName, HttpServletRequest request)
    {
        String temp = null;
        try
        {
            temp = request.getParameter(paramName);
            if(null == temp)
            {
                throw new NullPointerException(new StringBuilder().append("Missing parameter: ").append(paramName).toString());
            }
        }
        catch (NullPointerException ex)
        {
            //TODO create response so user sees what missing
        }
        return temp;
    }*/

    public static String getParameterRequired(String paramName, HttpServletRequest request) throws NullPointerException
    {
        String temp = null;
        temp = request.getParameter(paramName);
        if(null == temp)
        {
            throw new NullPointerException(new StringBuilder().append("Missing parameter: ").append(paramName).toString());
        }
        return temp;
    }

    public static String getParameter(String paramName, HttpServletRequest request)
    {
        String temp = null;
        if(null != request.getParameter(paramName))
        {
            temp = request.getParameter(paramName);
        }
        return temp;
    }

    public static String getFormField(HttpServletRequest request, String paramName)
    {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        String tempParam = null;
        List items = null;
        try
        {
            items = upload.parseRequest(request);
        }
        catch (FileUploadException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Iterator iterator = items.iterator();

        while (iterator.hasNext())
        {
            FileItem item = (FileItem) iterator.next();
            if (item.isFormField())
            {
                String name = item.getFieldName();
                String value = item.getString();
                if(name.equals(paramName))
                {
                    tempParam = value;
                    break;
                }
            }
        }
        return tempParam;
    }
}

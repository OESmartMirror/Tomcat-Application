package com.lookingglass.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.lookingglass.model.PicturesEntity;
import com.lookingglass.model.UsersEntity;
import com.lookingglass.model.UsersParametersEntity;
import com.lookingglass.utils.HibernateUtil;
import com.lookingglass.utils.LookUp;
import com.lookingglass.utils.Utils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;
import org.hibernate.Transaction;

@WebServlet
        (
                name = "File upload servlet",
                description = "handles file upload via HTTP POST and GET method",
                urlPatterns = "/uploadFile"
        )
public class UploadServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    private static final String s =  File.separator;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request))
        {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }

        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);


        String queryLabel = ServletUtils.getParameterRequired("label", request);
        //String queryLabel = ServletUtils.getFormField(request,"label");
        Session hibSession = HibernateUtil.getSession();
        UsersEntity tempUser = LookUp.User(queryLabel,hibSession);
        // constructs the directory path to store upload file
        String uploadPath = getServletContext().getRealPath("") + s + UPLOAD_DIRECTORY + s + tempUser.getLabel();

        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
        {
            uploadDir.mkdir();
        }
        Transaction tx = null;
        try {
            // parses the request's content to extract file data
            HashMap<String,String> formFieldProperties = new HashMap<String,String>();

            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();

            // iterates over form's fields
            String fileName = null;
            String filePath = null;
            while (iter.hasNext())
            {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField())
                {
                    fileName = new File(item.getName()).getName();
                    filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    // saves the file on disk
                    item.write(storeFile);
                }
                else if(item.isFormField())
                {
                    String name = item.getFieldName();
                    String value = item.getString();
                    formFieldProperties.put(name,value);
                }
            }

            try
            {
                tx = hibSession.beginTransaction();
                try
                {
                    tempUser.addPictures(uploadPath);
                }
                catch (Exception e3)
                {
                    e3.printStackTrace();
                    System.out.println(e3.getMessage());
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
                System.out.println(e2.getMessage());
            }
            finally
            {
                if(null != hibSession)
                {
                    hibSession.saveOrUpdate(tempUser);
                    for(PicturesEntity pe : tempUser.getPicturesById())
                    {
                        hibSession.saveOrUpdate(pe);
                    }
                }
            }
            request.setAttribute("message", "Upload has been done successfully!");
        }
        catch (Exception ex)
        {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
        }
        finally
        {
            if(null != tx) tx.commit();
            if(null != hibSession) hibSession.close();
            hibSession.close();
            getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }


    @Override
    protected  void  doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }
}

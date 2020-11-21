package com.lookingglass.servlet;

import com.lookingglass.model.UsersEntity;
import com.lookingglass.utils.HibernateUtil;
import com.lookingglass.utils.LookUp;
import com.lookingglass.utils.Utils;
import org.hibernate.Session;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet
        (
                name = "QR code servlet",
                description = "Nomen est omen",
                urlPatterns = "/qrcode"
        )
public class QRCodeServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String queryLabel = ServletUtils.getParameterRequired("label", request);
        String QRtype = ServletUtils.getParameterRequired("qrcode", request);
        Session hibSession = HibernateUtil.getSession();
        UsersEntity temp = LookUp.User(queryLabel,hibSession);
        OutputStream os = null;
        //ServletContext sc = getServletContext();
        File QRCode = null;
        if(QRtype.equals("registration"))
        {
            QRCode = Utils.getUserQRCodeRegisterAsFile(temp);
        }
        else if(QRtype.equals("login"))
        {
            QRCode = Utils.getUserQRCodeLoginAsFile(temp);
        }

        try (InputStream is = new FileInputStream(QRCode) )
        {
            os = response.getOutputStream();
            if (is == null)
            {
                response.setContentType("text/plain");
                os.write("Failed to send image".getBytes());
            }
            else
            {
                byte[] buffer = new byte[1024];
                int bytesRead;
                response.setContentType("image/png");
                while ((bytesRead = is.read(buffer)) != -1)
                {
                    os.write(buffer, 0, bytesRead);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally
        {
            hibSession.close();
            os.flush();
            os.close();
        }
    }

    @Override
    protected  void  doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        doPost(request,response);
    }
}

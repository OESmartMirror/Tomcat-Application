package com.lookingglass.servlet;

import com.lookingglass.model.UsersEntity;
import com.lookingglass.utils.HibernateUtil;
import com.lookingglass.utils.Utils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet
        (
                name = "Registration servlet",
                description = "Nomen est omen",
                urlPatterns = "/registration"
        )
public class RegistrationServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {

        Session session = null;
        Transaction tx = null;
        UsersEntity tempUser = null;
        try
        {
            HibernateUtil.getInstance();
            session = HibernateUtil.getSession();
            try
            {
                    tx = session.beginTransaction();
                    try
                    {
                        tempUser = new UsersEntity(getParameter("email",request));
                        tempUser.addParameter("firstName",getParameter("firstName",request));
                        tempUser.addParameter("lastName",getParameter("lastName",request));
                        tempUser.addParameter("password", Utils.getPasswordHash(getParameter("password",request)));
                    }
                    catch (Exception e3)
                    {
                        e3.printStackTrace();
                    }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
            finally
            {
                if(null != session) session.save(tempUser);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        catch (Error err)
        {
            err.printStackTrace();
            System.out.println(err.getMessage());
        }
        finally
        {
            System.out.println(session.isConnected());
            if(null != tx) tx.commit();
            if(null != session) session.close();
        }





    }

    @Override
    protected  void  doGet(HttpServletRequest request, HttpServletResponse response)
    {
        doPost(request,response);
    }

    private String getParameter(String paramName,HttpServletRequest request )
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
    }
}

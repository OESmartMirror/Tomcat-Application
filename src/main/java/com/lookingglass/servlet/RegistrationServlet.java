package com.lookingglass.servlet;

import com.lookingglass.model.UsersEntity;
import com.lookingglass.model.UsersParametersEntity;
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
                        tempUser = new UsersEntity(ServletUtils.getParameterRequired("email",request));
                        tempUser.addParameter("firstName", ServletUtils.getParameterRequired("firstName",request));
                        tempUser.addParameter("lastName", ServletUtils.getParameterRequired("lastName",request));
                        tempUser.addParameter("password", Utils.getPasswordHash(ServletUtils.getParameterRequired("password",request)));
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
                if(null != session)
                {
                    session.saveOrUpdate(tempUser);
                    for(UsersParametersEntity usp : tempUser.getUsersParametersById())
                    {
                        session.saveOrUpdate(usp);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
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

}

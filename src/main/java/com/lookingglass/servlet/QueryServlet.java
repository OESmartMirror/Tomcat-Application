package com.lookingglass.servlet;

import com.lookingglass.model.UsersEntity;
import com.lookingglass.utils.HibernateUtil;
import com.lookingglass.utils.LookUp;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet
        (
                name = "Query servlet",
                description = "Nomen est omen",
                urlPatterns = "/query"
        )
public class QueryServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        String queryLabel = getParameter("label", request);
        PrintWriter pwout = null;
        Session hibSession = HibernateUtil.getSession();
        try
        {
            UsersEntity temp = LookUp.User(queryLabel,hibSession);
            String tempJson = temp.userAsJson();
            response.setContentType("application/json;charset=UTF-8");
            pwout = response.getWriter();
            pwout.println(tempJson);
            System.out.println(tempJson);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally
        {
            if(null != hibSession) hibSession.close();
            pwout.flush();
            pwout.close();
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

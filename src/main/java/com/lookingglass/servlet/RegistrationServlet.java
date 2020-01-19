package com.lookingglass.servlet;

import com.lookingglass.model.UsersEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {

        UsersEntity tempUser = new UsersEntity(getParameter("email",request));
        tempUser.AddParameter("firstName",getParameter("firstname",request));
        tempUser.AddParameter("lastName",getParameter("lastname",request));

    }

    private String getParameter(String paramName,HttpServletRequest request )
    {
        String temp = null;
        try
        {
            temp = request.getParameter(paramName);
            if(null == temp) throw new NullPointerException(new StringBuilder().append("Missing parameter: ").append(paramName).toString());
        }
        catch (NullPointerException ex)
        {
            //TODO create response so user sees what missing
        }
        return temp;
    }
}

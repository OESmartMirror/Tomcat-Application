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
        String queryLabel = ServletUtils.getParameterRequired("label", request);
        String param = ServletUtils.getParameter("param", request);
        /*String justPictures = getParameter("justPictures", request);
        String justUserParameters = getParameter("justUserParameters", request);
        String justPrograms = getParameter("justPrograms", request);*/
        //user by label
        //pictures by user labal
        //user parameter by id
        //program parameter by id
        //új tábla a módosítások kezeléséhez, ha ebben vaj label, akkor történt módosítás a tükör queryzik, aztán kiveszi belőle
        //ha van változás, akkor amióta került bevezetésre új tensor azt felrakja a db-be
        //új tensorokat egy jsonbe elküldeni és az berakni a db-be
        //új servlet a tensorok fogadására cheksummal

        //Kellene egy olyan ami az összes label-t visszaadja ezt a lookupba kellene megcsinálni ha van label paraméter és üres string akkor minde user jsonjét adja vissza!!!!

        PrintWriter pwout = null;
        Session hibSession = HibernateUtil.getSession();
        //Valami kifinomultabb megoldás kell majd ide, talán valami olyasmi ami a contextel együtt felépül
        LookUp.initAll();
        try
        {
            UsersEntity temp = null;
            pwout = response.getWriter();
            String tempJson = null;
            if(null != param && "justUserParameters".equals(param))
            {
                temp = LookUp.User(queryLabel,hibSession);
                tempJson = temp.usersParametersAsJson();
            }
            else if(null != param && "justPrograms".equals(param))
            {
                temp = LookUp.User(queryLabel,hibSession);
                tempJson = temp.usersProgramsAsJson();
            }
            else if(null != param && "justPictures".equals(param))
            {
                temp = LookUp.User(queryLabel,hibSession);
                tempJson = temp.usersPicturseAsJson();
            }
            else if(!"".equals(queryLabel))
            {
                temp = LookUp.User(queryLabel,hibSession);
                tempJson = temp.userAsJson();
            }
            else
            {
                tempJson = LookUp.AllUserAsJson();
            }

            response.setContentType("application/json;charset=UTF-8");
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


}

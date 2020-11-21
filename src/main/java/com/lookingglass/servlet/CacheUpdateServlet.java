package com.lookingglass.servlet;

import com.lookingglass.model.UpdateQueueEntity;
import com.lookingglass.model.UsersEntity;
import com.lookingglass.utils.HibernateUtil;
import com.lookingglass.utils.LookUp;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet
        (
                name = "Cache update que servlet",
                description = "Adds and deletes Users from the cache update queue",
                urlPatterns = "/cache"
        )
public class CacheUpdateServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //ide kellene egy lookup, hogy ha már benne van a sorban, akkor nem rakja mégegyszer bele
        String label = ServletUtils.getParameterRequired("label", request);
        String action = ServletUtils.getParameterRequired("action", request);

        UpdateQueueEntity tempQItem = null;
        UsersEntity tempUser = null;
        Session hibSession = null;
        Transaction tx = null;
        try
        {
            HibernateUtil.getInstance();
            hibSession = HibernateUtil.getSession();
            tempUser = LookUp.User(label,hibSession);
            Integer tempUserId = tempUser.getId();

            //itt még megkell gondolni azt, hogyan legyen megoldva, hogy az indításánál mindent betöltsön!!!
            if(null != tempUser)// ezen el kellene gondolkodni, mert ugye ez mindig bekéri a dolgokat, így a cache mindig friss
            {
                tx = hibSession.beginTransaction();
                //tempQItem = LookUp.getUpdateQueueEntity(tempUserId,hibSession);
                tempQItem = tempUser.getUsersUpdateQueueById().isEmpty() ? null : (UpdateQueueEntity) tempUser.getUsersUpdateQueueById().toArray()[0];
                if(null != tempQItem)
                {
                    if("put".equals(action))
                    {
                        tempQItem.setTimeStamp(new Timestamp(System.currentTimeMillis()));
                        hibSession.saveOrUpdate(tempQItem);
                    }
                    else if("remove".equals(action))
                    {
                        hibSession.delete(tempQItem);
                    }
                }
                else
                {
                    if("put".equals(action))
                    {
                        /*tempQItem = new UpdateQueueEntity();
                        tempQItem.setUsersByUserId(tempUser);
                        tempQItem.setTimeStamp(new Timestamp(System.currentTimeMillis()));
                        hibSession.saveOrUpdate(tempQItem);*/
                        tempUser.addUpdateQueueItem();
                        for(UpdateQueueEntity uqe : tempUser.getUsersUpdateQueueById())
                        {
                            hibSession.saveOrUpdate(uqe);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            tx.rollback();
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally
        {
            if(null != tx) tx.commit();
            if(null != hibSession) hibSession.close();
        }
    }

    @Override
    protected  void  doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        doPost(request,response);
    }
}

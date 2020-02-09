package com.lookingglass.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil
{
    public static SessionFactory factory;
    public static Configuration cfg;
    private static HibernateUtil me;
//to disallow creating objects by other classes.

    private HibernateUtil()
    {

    }
//maling the Hibernate SessionFactory object as singleton

    public static synchronized SessionFactory getSessionFactory()
    {

        if (factory == null)
        {
            try
            {
                cfg = new Configuration();
                //cfg.configure("src/main/java/resources/hibernate.cfg.xml");
                cfg.configure();
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
                factory = cfg.buildSessionFactory(serviceRegistry);
            }
            catch (Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }

        }
        return factory;
    }

    //@org.jetbrains.annotations.NotNull
    public static Session getSession() throws HibernateException
    {
        factory = getSessionFactory();
        Session session = null;
        try
        {
            session = factory.openSession();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (!session.isConnected())
        {
            reconnect();
        }
        return session;
    }


    public static void reconnect() throws HibernateException
    {
        factory = cfg.buildSessionFactory();
    }

    public static synchronized HibernateUtil getInstance() throws HibernateException
    {
        if (me == null)
        {
            me = new HibernateUtil();
        }
        return me;
    }
}

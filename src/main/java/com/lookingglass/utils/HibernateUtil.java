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
        factory = getSessionFactory();
    }
//maling the Hibernate SessionFactory object as singleton

    public static synchronized SessionFactory getSessionFactory()
    {

        if (factory == null)
        {
            try
            {
                cfg = new Configuration().configure("/com/lookingglass/model/hibernate.cfg.xml");
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
                factory = cfg.buildSessionFactory(serviceRegistry);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return factory;
    }

    //@org.jetbrains.annotations.NotNull
    public static Session getSession() throws HibernateException
    {
        Session session = factory.openSession();
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

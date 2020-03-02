package com.lookingglass.utils;

import com.lookingglass.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;
import java.util.Set;

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
                /*cfg = new Configuration();
                cfg.addAnnotatedClass(UsersEntity.class);
                cfg.addAnnotatedClass(UsersParametersEntity.class);
                cfg.addAnnotatedClass(PicturesEntity.class);
                cfg.addAnnotatedClass(ProgramsEntity.class);
                cfg.addAnnotatedClass(ProgramParametersEntity.class);
                //cfg.configure("src/main/java/resources/hibernate.cfg.xml");
                cfg.configure();
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
                Metadata metadata = new MetadataSources( serviceRegistry ).getMetadataBuilder().build();
                //factory = cfg.buildSessionFactory(serviceRegistry);
                factory = metadata.getSessionFactoryBuilder().build();*/
                factory = new Configuration().configure().buildSessionFactory();
                Map map = factory.getAllCollectionMetadata();
                Set<Map.Entry<String,CollectionMetadata>> set = map.entrySet();
                for (Map.Entry e : set) {
                    System.out.println(e.getKey());
                    System.out.println(e.getValue());
                }
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

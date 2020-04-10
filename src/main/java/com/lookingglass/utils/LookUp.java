package com.lookingglass.utils;

import com.lookingglass.model.*;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class LookUp
{
    private static ConcurrentHashMap <String, UsersEntity> usersByLabelCache = new ConcurrentHashMap<>();
    private static ConcurrentHashMap <Integer, UsersEntity> usersByIdCache = new ConcurrentHashMap<>();
    private static ConcurrentHashMap <Integer, UsersParametersEntity> usersParameterByIdCache = new ConcurrentHashMap<>();
    private static ConcurrentHashMap <Integer, ProgramsEntity> programsByIdCache = new ConcurrentHashMap<>();
    private static ConcurrentHashMap <Integer, ProgramParametersEntity> programParameterssByIdCache = new ConcurrentHashMap<>();
    private static ConcurrentHashMap <Integer, PicturesEntity> picturesByIdCache = new ConcurrentHashMap<>();

    public static UsersEntity User(String label)
    {
        UsersEntity temp = usersByLabelCache.getOrDefault(label,null);
        if(null == temp)
        {
            Session hibSession = HibernateUtil.getSession();
            try
            {
                CriteriaBuilder criteriaBuilder = hibSession.getCriteriaBuilder();
                CriteriaQuery<UsersEntity> criteriaQuery = criteriaBuilder.createQuery(UsersEntity.class);
                Root<UsersEntity> root = criteriaQuery.from(UsersEntity.class);
                criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("label"),label));
                Query<UsersEntity> query = hibSession.createQuery(criteriaQuery);
                List<UsersEntity> results = query.getResultList();
                temp = results.get(0);
                Integer tempId = temp.getId();
                usersByIdCache.put(tempId,temp);
                usersByLabelCache.put(label,temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            finally
            {
                hibSession.close();
            }
        }
        return temp;
    }

    public static UsersEntity User(String label, Session hibSession)
    {
        UsersEntity temp = usersByLabelCache.getOrDefault(label,null);
        if(null == temp)
        {
            try
            {
                CriteriaBuilder criteriaBuilder = hibSession.getCriteriaBuilder();
                CriteriaQuery<UsersEntity> criteriaQuery = criteriaBuilder.createQuery(UsersEntity.class);
                Root<UsersEntity> root = criteriaQuery.from(UsersEntity.class);
                criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("label"),label));
                Query<UsersEntity> query = hibSession.createQuery(criteriaQuery);
                List<UsersEntity> results = query.getResultList();
                temp = results.get(0);
                Integer tempId = temp.getId();
                usersByIdCache.put(tempId,temp);
                usersByLabelCache.put(label,temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return temp;
    }

    public static  UsersEntity User(Integer id)
    {
        UsersEntity temp = usersByIdCache.getOrDefault(id,null);
        if(null == temp)
        {
            Session hibSession = HibernateUtil.getSession();
            try
            {
                temp = hibSession.get(UsersEntity.class,id);
                Hibernate.initialize(temp);
                if(null != temp) usersByIdCache.put(id,temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());System.err.println(e);
            }
            finally
            {
                hibSession.close();
            }
        }
        return temp;
    }

    public static  UsersEntity User(Integer id, Session hibSession)
    {
        UsersEntity temp = usersByIdCache.getOrDefault(id,null);
        if(null == temp)
        {
            try
            {
                temp = hibSession.get(UsersEntity.class,id);
                Hibernate.initialize(temp);
                if(null != temp) usersByIdCache.put(id,temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return temp;
    }
}

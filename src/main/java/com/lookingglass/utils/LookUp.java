package com.lookingglass.utils;

import com.google.gson.Gson;
import com.lookingglass.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

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
    private static ConcurrentHashMap <Integer, ProgramParametersEntity> programParametersByIdCache = new ConcurrentHashMap<>();
    private static ConcurrentHashMap <Integer, PicturesEntity> picturesByIdCache = new ConcurrentHashMap<>();

    private static Gson gson = new Gson();

    private static Session hibSession = HibernateUtil.getSession();

    public static void initAll()
    {
        initUsersEntities();
        initUsersParametersEntity();
        initProgramsEntity();
        initProgramParametersEntity();
        initPicturesEntity();
    }

    private static void initUsersEntities()
    {
        List<UsersEntity> results = null;
        CriteriaBuilder criteriaBuilder = hibSession.getCriteriaBuilder();
        CriteriaQuery<UsersEntity> criteriaQuery = criteriaBuilder.createQuery(UsersEntity.class);
        Root<UsersEntity> root = criteriaQuery.from(UsersEntity.class);
        criteriaQuery.select(root);

        Query<UsersEntity> query = hibSession.createQuery(criteriaQuery);
        results = query.getResultList();
        for(UsersEntity user : results)
        {
            Integer tempId = user.getId();
            String label = user.getLabel();
            usersByIdCache.put(tempId,user);
            usersByLabelCache.put(label,user);
        }
    }

    private static void initUsersParametersEntity()
    {
        List<UsersParametersEntity> results = null;
        CriteriaBuilder criteriaBuilder = hibSession.getCriteriaBuilder();
        CriteriaQuery<UsersParametersEntity> criteriaQuery = criteriaBuilder.createQuery(UsersParametersEntity.class);
        Root<UsersParametersEntity> root = criteriaQuery.from(UsersParametersEntity.class);
        criteriaQuery.select(root);

        Query<UsersParametersEntity> query = hibSession.createQuery(criteriaQuery);
        results = query.getResultList();
        for(UsersParametersEntity item : results)
        {
            Integer tempId = item.getId();
            usersParameterByIdCache.put(tempId,item);
        }
    }

    private static void initProgramsEntity()
    {
        List<ProgramsEntity> results = null;
        CriteriaBuilder criteriaBuilder = hibSession.getCriteriaBuilder();
        CriteriaQuery<ProgramsEntity> criteriaQuery = criteriaBuilder.createQuery(ProgramsEntity.class);
        Root<ProgramsEntity> root = criteriaQuery.from(ProgramsEntity.class);
        criteriaQuery.select(root);

        Query<ProgramsEntity> query = hibSession.createQuery(criteriaQuery);
        results = query.getResultList();
        for(ProgramsEntity item : results)
        {
            Integer tempId = item.getId();
            programsByIdCache.put(tempId,item);
        }
    }

    private static void initProgramParametersEntity()
    {
        List<ProgramParametersEntity> results = null;
        CriteriaBuilder criteriaBuilder = hibSession.getCriteriaBuilder();
        CriteriaQuery<ProgramParametersEntity> criteriaQuery = criteriaBuilder.createQuery(ProgramParametersEntity.class);
        Root<ProgramParametersEntity> root = criteriaQuery.from(ProgramParametersEntity.class);
        criteriaQuery.select(root);

        Query<ProgramParametersEntity> query = hibSession.createQuery(criteriaQuery);
        results = query.getResultList();
        for(ProgramParametersEntity item : results)
        {
            Integer tempId = item.getId();
            programParametersByIdCache.put(tempId,item);
        }
    }

    private static void initPicturesEntity()
    {
        List<PicturesEntity> results = null;
        CriteriaBuilder criteriaBuilder = hibSession.getCriteriaBuilder();
        CriteriaQuery<PicturesEntity> criteriaQuery = criteriaBuilder.createQuery(PicturesEntity.class);
        Root<PicturesEntity> root = criteriaQuery.from(PicturesEntity.class);
        criteriaQuery.select(root);

        Query<PicturesEntity> query = hibSession.createQuery(criteriaQuery);
        results = query.getResultList();
        for(PicturesEntity item : results)
        {
            Integer tempId = item.getId();
            picturesByIdCache.put(tempId,item);
        }
    }

    public static String AllUserAsJson()
    {
        List<UsersEntity> results = null;
        String temp = null;
        try
        {
            results = getUsersEntities(hibSession, results);
            //temp = gson.toJson(results);
            temp = Utils.getListAsJson(results);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return temp;
    }

    public static String AllUserAsJson(Session  hibSession)
    {
        List<UsersEntity> results = null;
        try
        {
            results = getUsersEntities(hibSession, results);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return gson.toJson(results);
    }

    @NotNull
    private static List<UsersEntity> getUsersEntities(Session hibSession, List<UsersEntity> results)
    {
        CriteriaBuilder criteriaBuilder = hibSession.getCriteriaBuilder();
        CriteriaQuery<UsersEntity> criteriaQuery = criteriaBuilder.createQuery(UsersEntity.class);
        Root<UsersEntity> root = criteriaQuery.from(UsersEntity.class);
        criteriaQuery.select(root);

        Query<UsersEntity> query = hibSession.createQuery(criteriaQuery);
        results = query.getResultList();
        for(UsersEntity user : results)
        {
            Integer tempId = user.getId();
            String label = user.getLabel();
            usersByIdCache.put(tempId,user);
            usersByLabelCache.put(label,user);
        }
        return results;
    }

    public static UsersEntity User(String label)
    {
        UsersEntity temp = usersByLabelCache.getOrDefault(label,null);
        if(null == temp)
        {
            try
            {
                temp = getUsersEntity(label, temp, hibSession);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return temp;
    }

    @NotNull
    private static UsersEntity getUsersEntity(String label, UsersEntity temp, Session hibSession)
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
        return temp;
    }

    public static UsersEntity User(String label, Session hibSession)
    {
        UsersEntity temp = usersByLabelCache.getOrDefault(label,null);
        if(null == temp)
        {
            try
            {
                temp = getUsersEntity(label, temp, hibSession);
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

    public static UsersParametersEntity UsersParameter(Integer id)
    {
        UsersParametersEntity temp = usersParameterByIdCache.getOrDefault(id,null);
        if(null == temp)
        {
            try
            {
                temp = hibSession.get(UsersParametersEntity.class,id);
                Hibernate.initialize(temp);
                if(null != temp) usersParameterByIdCache.put(id,temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());System.err.println(e);
            }
        }
        return temp;
    }

    public static UsersParametersEntity UsersParameter(Integer id, Session hibSession)
    {
        UsersParametersEntity temp = usersParameterByIdCache.getOrDefault(id,null);
        if(null == temp)
        {
            try
            {
                temp = hibSession.get(UsersParametersEntity.class,id);
                Hibernate.initialize(temp);
                if(null != temp) usersParameterByIdCache.put(id,temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());System.err.println(e);
            }
        }
        return temp;
    }

    public static ProgramParametersEntity ProgramParameters(Integer id)
    {
        ProgramParametersEntity temp = programParametersByIdCache.getOrDefault(id,null);
        if(null == temp)
        {
            try
            {
                temp = hibSession.get(ProgramParametersEntity.class,id);
                Hibernate.initialize(temp);
                if(null != temp) programParametersByIdCache.put(id,temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());System.err.println(e);
            }
        }
        return temp;
    }

    public static ProgramParametersEntity ProgramParameters(Integer id, Session hibSession)
    {
        ProgramParametersEntity temp = programParametersByIdCache.getOrDefault(id,null);
        if(null == temp)
        {
            try
            {
                temp = hibSession.get(ProgramParametersEntity.class,id);
                Hibernate.initialize(temp);
                if(null != temp) programParametersByIdCache.put(id,temp);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());System.err.println(e);
            }
        }
        return temp;
    }
}

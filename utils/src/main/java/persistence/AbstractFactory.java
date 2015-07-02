package persistence;

import utils.ReflectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dgarcia on 08/04/2015.
 */
public abstract class AbstractFactory<C, PK> {

    private static final Logger LOGGER          = LogManager.getLogger( AbstractFactory.class.getName() );

    private Field             primaryKeyField = null;

    private Class<C>          dtoClassType    = null;
    private Field[]           dtoFields       = null;
    private final EntityManagerHelper emh;

    protected AbstractFactory(Class<C> clazz, EntityManagerHelper emh) {
        this.dtoClassType = clazz;
        this.emh = emh;

        this.dtoFields = this.dtoClassType.getDeclaredFields();

        try {
            this.primaryKeyField = ReflectionUtils.getPrimaryKey( clazz );
        } catch ( NoSuchFieldException e ) {
            String msg = new ParameterizedMessage( "The pojo has not a primary key field [{}]", primaryKeyField ).getFormattedMessage();
            LOGGER.fatal( msg, e );
        }

    }

    protected PK create( C pojo ) {

        PK idValue = null;

        try {
            emh.beginTransaction();
            emh.getEntityManager().persist( pojo );
            emh.getEntityManager().flush();
            emh.commit();

        } catch( RuntimeException e ) {

            emh.safeRollback();
            throw e;

        }

        try {

            if ( primaryKeyField != null ) {
                primaryKeyField.setAccessible( true );
                idValue = (PK) primaryKeyField.get( pojo );
            }

        } catch ( SecurityException | IllegalAccessException e ) {
            String msg = new ParameterizedMessage( "The primary key field [{}] is not accesible", primaryKeyField ).getFormattedMessage();
            LOGGER.fatal( msg, e );
        }

        return idValue;
    }

    protected C update(C pojo) {
        try {
            emh.beginTransaction();
            emh.getEntityManager().merge( pojo );
            emh.commit();

        } catch ( RuntimeException e ) {

            emh.safeRollback();
            throw e;

        }

        return pojo;
    }

    protected C get( PK id ) {

        C dto = emh.getEntityManager().find( dtoClassType, id );

        return dto;
    }

    protected List<C> executeQuery( String jpql ) {

        EntityManager em = emh.getEntityManager();

        Query query = em.createNativeQuery(jpql, dtoClassType );
        List<C> results = null;

        try {
            results = query.getResultList();
        } catch ( RuntimeException e ) {

            emh.safeRollback();
            throw e;

        }

        return results;
    }

    protected List<C> find( C pojo, boolean bExact ) {

        EntityManager em = emh.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<C> query = cb.createQuery( dtoClassType );
        Root<C> c = query.from( dtoClassType );

        List<C> result;

        String fieldName;
        Object fieldValue;
        Class fieldType;
        Field field;
        ParameterExpression p;
        Map<ParameterExpression, Object> params = new HashMap<>();
        int f, length = this.dtoFields.length;

        for ( f = 0; f < length; f++ ) {

            field = this.dtoFields[f];

            // en las clases serializables, tenemos que descartar el
            // serialVersionUID (static)
            // además, hay que descartar los atributos protected que inserta el
            // weblogic durante el tratamiento de las entidades
            if ( !Modifier.isStatic( this.dtoFields[f].getModifiers() ) && !Modifier.isProtected( this.dtoFields[f].getModifiers() ) ) {

                field.setAccessible( true );

                fieldName = field.getName();
                fieldType = field.getType();

                try {
                    fieldValue = field.get(pojo);
                } catch( IllegalAccessException e ) {
                    query = null;
                    String msg = new ParameterizedMessage( "The field [{}] is not accesible", field ).getFormattedMessage();
                    LOGGER.fatal( msg, e );
                    break;
                }

                if ( !field.isAnnotationPresent( Transient.class ) && fieldValue != null ) {

                    p = cb.parameter(fieldType);

                    if ( String.class.equals( field.getType() ) && !bExact ) {

                        query = query.where(cb.like(c.get(fieldName), p));
                        fieldValue = "%" + fieldValue + "%";

                    } else {

                        query = query.where(cb.equal(c.get(fieldName), p));

                    }
                    params.put(p, fieldValue);
                }
            }
        }

        if( query != null ) {
            TypedQuery<C> typedQuery = em.createQuery(query);

            for (Map.Entry<ParameterExpression, Object> entry : params.entrySet()) {

                typedQuery.setParameter(entry.getKey(), entry.getValue());

            }

            result = typedQuery.getResultList();

        } else {

            result = Collections.emptyList();
        }

        return result;
    }

    protected EntityManager getEntityManager(){
        return emh.getEntityManager();
    }

    public void beginTransaction(){
        emh.beginTransaction();
    }

    public void commit(){
        emh.commit();
    }

    public void safeRollback(){
        emh.safeRollback();
    }
}

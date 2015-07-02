package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by dgarcia on 09/04/2015.
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static Field getPrimaryKey(Class clazz) throws NoSuchFieldException {

        Field[] fields = clazz.getDeclaredFields();

        for ( Field field : fields ) {

            Annotation[] annotations = field.getDeclaredAnnotations();

            for ( Annotation annotation : annotations ) {

                if ( annotation instanceof javax.persistence.Id || annotation instanceof javax.persistence.EmbeddedId ) {
                    return field;
                }
            }
        }

        throw new NoSuchFieldException( "The class " + clazz.getName() + " has no @Id annotated field." );
    }

}

package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SmartProperties {

    private static Properties properties = null;

    private SmartProperties() {
    }

    private static void init() throws IOException {

        properties = new Properties();
        String propertiesFileName = "smart.properties";

        InputStream inputStream = SmartProperties.class.getClassLoader().getResourceAsStream( propertiesFileName );
        properties.load( inputStream );

    }

    public static void loadFile( String propertiesFileName ) throws IOException {

        if ( properties == null ) {
            init();
        }

        InputStream inputStream = SmartProperties.class.getClassLoader().getResourceAsStream( propertiesFileName );
        properties.load( inputStream );

    }

    public static String get( String property ) {

        return properties.getProperty( property );
    }

}

# Server
*Smart 2.0 Java server*

## Description

Java server implementation

- Profiling

-P pre

-P test (default)

-P validation

-P weblogic

-P weblogic-test

- Deploy

```
    cd server/
    mvn clean package pre-integration-test
```

- Run on jetty
```
    cd server/
    mvn clean package jetty:run -P pre
```

- Test on Weblogic (IGZ)
```
    cd server/
    mvn clean package pre-integration-test -P test,weblogic-test
```

- Deploy on Weblogic for development (IGZ)
```
    cd server/
    mvn clean package pre-integration-test -P pre,weblogic-dev
```

- Deploy on Weblogic (IGZ)
```
    cd server/
    mvn clean package pre-integration-test -P pre,weblogic
```

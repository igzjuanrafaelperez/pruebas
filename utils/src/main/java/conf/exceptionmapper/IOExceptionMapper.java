package conf.exceptionmapper;

import utils.TraceManager;
import rest.BaseWS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Date;

@Provider
public class IOExceptionMapper extends BaseWS implements ExceptionMapper<IOException> {

    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public Response toResponse( IOException e ) {
        LOGGER.error( TraceManager.manage( "" ), e );
        return generateResponse( 500, "IOException " + new Date( System.currentTimeMillis() ) );
    }
}

package conf.exceptionmapper;

import exceptions.GenericSmartException;
import rest.BaseWS;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by ruben on 18/02/15.
 */
// Mapper for our custom exception
@Provider
public class GenericSmartExceptionMapper extends BaseWS implements ExceptionMapper<GenericSmartException> {

    @Override
    public Response toResponse(GenericSmartException exception) {
        return generateResponse(exception);
    }
}

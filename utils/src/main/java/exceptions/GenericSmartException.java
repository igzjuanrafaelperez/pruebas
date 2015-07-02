package exceptions;

import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Created by ruben on 18/02/15.
 */
public class GenericSmartException extends Exception implements Serializable {

    private static final long serialVersionUID = -5278843769302102341L;
    private final Integer errorCode;
    private String description;

    public GenericSmartException(Integer statusCode){
        super(Response.Status.fromStatusCode(statusCode).getReasonPhrase());
        this.errorCode=statusCode;
    }

    public GenericSmartException(Integer statusCode, String description){
        this(statusCode);
        this.description=description;
    }

    public Integer getErrorCode(){
        return this.errorCode;
    }
    public String getDescription(){
        return this.description;
    }

}

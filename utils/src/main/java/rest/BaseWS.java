package rest;

import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.GenericSmartException;

/**
 * Created by ruben on 11/02/15.
 */
public class BaseWS {

    protected static final int    OK                    = Response.Status.OK.getStatusCode();
    protected static final int    INTERNAL_SERVER_ERROR = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    protected static final int    SERVICE_UNAVAILABLE   = Response.Status.SERVICE_UNAVAILABLE.getStatusCode();
    protected static final int    UNAUTHORIZED          = Response.Status.UNAUTHORIZED.getStatusCode();

    // Request context property names
    protected static final String USER_PROPERTY = "user";

    static class ResponsePOJO {
        @JsonProperty
        ResultPOJO result;
        @JsonProperty
        Object data;
        @JsonProperty
        String cursor;

        /**
         * Constructor
         * @param result
         * @param data
         * @param cursor
         */
        public ResponsePOJO( ResultPOJO result, Object data, String cursor) {
            this.data = data;
            this.result = result;
            this.cursor = cursor;
        }

    }

    static class ResultPOJO {
        @JsonProperty
        Integer code;
        @JsonProperty
        String message;
        @JsonProperty
        String description;

        /**
         * Constructor
         * @param statusCode
         * @param message
         */
        public ResultPOJO ( Integer statusCode, String message) {
            this.code = statusCode;
            this.description = Response.Status.fromStatusCode(statusCode).getReasonPhrase();
            this.message = message;
        }
    }

    /**
     * Generate Response
     * @param statusCode
     * @param message
     * @return Response
     */
    public Response generateResponse(int statusCode, String message){

        ResultPOJO result = new ResultPOJO(statusCode, message);
        ResponsePOJO response = new ResponsePOJO(result, null, null);

        return Response.status(statusCode).entity(response).build();

    }

    /**
     * Generate Response
     * @param statusCode
     * @param message
     * @param data
     * @return Response
     */
    public Response generateResponse(int statusCode, String message, Object data){

        ResultPOJO result = new ResultPOJO(statusCode, message);
        ResponsePOJO response = new ResponsePOJO(result, data, null);

        return Response.status(statusCode).entity(response).build();

    }

    /**
     * Generate Response
     * @param statusCode
     * @param message
     * @param data
     * @return Response
     */
    public Response generateResponse(int statusCode, String message, Object data, Map<String, String> headers){

        ResultPOJO result = new ResultPOJO(statusCode, message);
        ResponsePOJO response = new ResponsePOJO(result, data, null);
        Response.ResponseBuilder responseBuilder = Response.status(statusCode).entity(response);

        for(String key : headers.keySet()) {
            responseBuilder.header(key, headers.get(key));
        }

        return responseBuilder.build();

    }

    /**
     * Generate Response
     * @param statusCode
     * @param data
     * @param headers
     * @return
     */
    public Response generateImageResponse(int statusCode, Object data, Map<String, String> headers){

        Response.ResponseBuilder responseBuilder = Response.status(statusCode).entity(data);

        for(String key : headers.keySet()) {
            responseBuilder.header(key, headers.get(key));
        }

        return responseBuilder.build();

    }

    /**
     * Generate Response
     * @param statusCode
     * @param description
     * @param data
     * @param cursor
     * @return Response
     */
    public Response generateResponse(Integer statusCode, String description, Object data, String cursor){

        ResultPOJO result = new ResultPOJO(statusCode, description);
        ResponsePOJO response = new ResponsePOJO(result, data, cursor);

        return Response.status(statusCode).entity(response).build();

    }

    /**
     * Generate Response
     * @param exception
     * @return Response
     */
    public Response generateResponse(GenericSmartException exception){

        ResultPOJO result = new ResultPOJO(exception.getErrorCode(), exception.getDescription());
        ResponsePOJO response = new ResponsePOJO(result, null, null);

        return Response.status(exception.getErrorCode()).entity(response).build();

    }

    /**
     * Store a property in the ContainerRequestContext
     * @param requestContext
     * @param propertyName
     * @param propertyObject
     */
    public void store(ContainerRequestContext requestContext, String propertyName, Object propertyObject) {
        requestContext.setProperty(propertyName, propertyObject);
    }
}

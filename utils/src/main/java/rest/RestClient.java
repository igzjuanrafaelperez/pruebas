package rest;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import com.google.common.base.Strings;
import conf.JaxrsConf;
import utils.SmartProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;


import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.net.ssl.*;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static javax.ws.rs.client.Entity.json;

public class RestClient {

    private String        contextRoot;
    private MediaType           requestMediaType = MediaType.APPLICATION_JSON_TYPE;
    private final Client client;
    private Map<String, Object> payload = new HashMap<>();
    private Map<String, Object> headers;
    private WebTarget webTarget;

    /**
     *
     * @param contextRoot
     */
    public RestClient(final String contextRoot) {
        this( contextRoot, null, null, true );
    }

    /**
     *
     * @param contextRoot
     * @param sessionTokenHeader
     */
    public RestClient(final String contextRoot, final Map<String, Object> sessionTokenHeader) {
        this( contextRoot, sessionTokenHeader, null, true );
    }

    /**
     *
     * @param contextRoot
     * @param useProxy
     */
    public RestClient(final String contextRoot, final boolean useProxy) {
        this( contextRoot, null, null, useProxy );
    }

    /**
     *
     * @param contextRoot
     * @param sessionTokenHeader
     * @param useProxy
     */
    public RestClient(final String contextRoot, final Map<String, Object> sessionTokenHeader, final boolean useProxy) {
        this( contextRoot, sessionTokenHeader, null, useProxy );
    }

    /**
     *
     * @param contextRoot
     * @param sessionTokenHeader
     * @param clientConfig
     */
    public RestClient(final String contextRoot, final Map<String, Object> sessionTokenHeader, ClientConfig clientConfig) { this(contextRoot, sessionTokenHeader, clientConfig, false); }

    /**
     *
     * @param contextRoot
     * @param sessionTokenHeader
     * @param clientConfig
     * @param useProxy
     */
    private RestClient(final String contextRoot, final Map<String, Object> sessionTokenHeader, ClientConfig clientConfig, final boolean useProxy) {

        if ( useProxy && !Strings.isNullOrEmpty( SmartProperties.get( "smart.proxyUrl" ) ) ) {
            // This is intentionally masking the parameter client config, to give priority to true value of useProxy even if clientConfig is not null
            clientConfig = createProxiedClientConfig(clientConfig);
        }

        this.contextRoot = contextRoot;
        this.headers = new HashMap<>();

        if ( clientConfig != null ) {
            client = ClientBuilder.newClient(clientConfig);
        } else {
            client = ClientBuilder.newClient();
        }

        if( sessionTokenHeader != null && !sessionTokenHeader.isEmpty() ) {
            this.headers.putAll(sessionTokenHeader);
        }

        // boolean printEntity = !Strings.isNullOrEmpty( System.getenv( "DEBUG" ) );
        boolean printEntity = true;
        client.register( new LoggingFilter( Logger.getLogger( "RestClient" ), printEntity ) );

        JaxrsConf jaxrsConf = new JaxrsConf();
        client.register( new JacksonJsonProvider( jaxrsConf.jsonObjectMapper() ) );
        client.register( new JacksonXMLProvider( jaxrsConf.xmlObjectMapper() ) );

        client.register( MultiPartFeature.class );

        webTarget = client.target( contextRoot );
    }

    /**
     * PUBLIC CONFIGURATION METHODS
     * */

    public RestClient addParam( String name, Object value ) {

        if ( value != null ) {
            payload.put( name, value );
        }
        return this;
    }

    public RestClient addPathParam( String param ) {
        webTarget = webTarget.path(param + "/");
        return this;
    }

    public RestClient addUriComponent( String uriComponent ) {
        webTarget = webTarget.path(uriComponent);
        return this;
    }

    public RestClient setRequestMediaType( MediaType requestMediaType ) {
        this.requestMediaType = requestMediaType;
        return this;
    }

    /**
     * PUT
     * */

    public Response put() {
        return put(null, null);
    }

    public Response put( Object data ) {
        return put(data, null);
    }

    /**
     *
     * @param data
     * Accepted types:
     *  - Map<String, Object> for json payload
     *  - Entity for entity
     *  - If null passed, params will be got from local attribute 'payload'
     * @See setEntity
     * @param customHeaders
     * Overrides static headers
     * @return Response
     */
    public Response put( Object data, Map<String, Object> customHeaders ) {

        addCustomHeaders(customHeaders);

        Invocation.Builder builder = this.payload.isEmpty() ? createRequestBuilder() : createRequestBuilder(payload);

        Response response = builder.put(setEntity(data));

        newCall();

        return response;
    }

    /**
     * POST
     * */

    public Response post() {
        return post(null, null);
    }

    public Response post( Object data ) {
        return post(data, null);
    }

    /**
     * @param data
     * Accepted types:
     *  - Map<String, Object> for json payload
     *  - Entity for entity
     *  - If null passed, params will be got from local attribute 'payload'
     * @See setEntity
     * @param customHeaders
     * Overrides static headers
     * @return Response
     */
    public Response post( Object data, Map<String, Object> customHeaders ) {

        addCustomHeaders( customHeaders );

        Response response = createRequestBuilder().post(setEntity(data));

        newCall();

        return response;
    }

    /**
     * GET
     * */

    public Response get() {
        return get( payload );
    }

    /**
     *
     * @param queryParams
     * @return Response
     */
    public Response get( Map<String, Object> queryParams ) {

        Response response = createRequestBuilder( queryParams ).get();

        newCall();

        return response;
    }

    /**
     * DELETE
     * */

    public Response delete() {
        return delete( payload );
    }

    public Response delete( Map<String, Object> queryParams ) {
        return createRequestBuilder(queryParams).delete();
    }


    /**
     * HEADERS MANAGE
     * */

    private void addCustomHeaders( Map<String, Object> customHeaders ) {

        if( customHeaders != null && !customHeaders.isEmpty() ) {
            this.headers.putAll( customHeaders );
        }
    }

    private static void addHeaders(Invocation.Builder request, Map<String, Object> headers) {
        headers.forEach(request::header);
    }

    public void addHeader( String key, String value ) {
        this.headers.put(key, value);
    }

    /**
     * ENTITY CREATION FOR PUT AND POST
     *
     * @param data
     * Accepted types:
     *  - Map<String, Object> for json payload
     *  - Entity for entity
     *  - If null passed, params will be got from local attribute 'payload'
     * @return Entity
     */
    private Entity setEntity( Object data ) {

        Entity entity;

        if( data instanceof Map) {
            entity = json(data);
        } else if( data instanceof Entity ) {
            entity = (Entity) data;
        } else {
            entity = encodeParams();
        }
        return entity;
    }

    /**
     * INVOCATION BUILDER MANAGE
     * */

    private Invocation.Builder createRequestBuilder() {

        return createRequestBuilder( null );

    }

    private Invocation.Builder createRequestBuilder( Map<String, Object> queryParams ) {
        if( queryParams != null && !queryParams.isEmpty() ) {
            webTarget = setQueryParams(webTarget, queryParams);
        }

        Invocation.Builder requestBuilder = requestMediaType != null ? webTarget.request(requestMediaType) : webTarget.request();

        addHeaders(requestBuilder, headers);

        return requestBuilder;
    }

    /**
     * PARAMS MANAGE
     * */

    private WebTarget setQueryParams( WebTarget webTarget, Map<String, Object> queryParams ) {

        for (Map.Entry<String, Object> stringObjectEntry : queryParams.entrySet()) {
            webTarget = webTarget.queryParam(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }

        return webTarget;

    }

    private Entity<MultivaluedMap<String, String>> encodeParams() {

        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();

        for (Map.Entry<String, Object> stringObjectEntry : payload.entrySet()) {
            params.add(stringObjectEntry.getKey(), (String) stringObjectEntry.getValue());
        }

        return Entity.entity( params, this.requestMediaType );

    }

    /**
     *
     * */

    private ClientConfig createProxiedClientConfig( ClientConfig clientConfig ) {

        String proxyUrl = SmartProperties.get("smart.proxyUrl");
        String proxyPort = SmartProperties.get( "smart.proxyPort" );
        String proxyUsername = SmartProperties.get( "smart.proxyUsername" );
        String proxyPassword = SmartProperties.get( "smart.proxyPassword" );

        if ( clientConfig == null ) {
            clientConfig = new ClientConfig();
        }

        clientConfig.property( ClientProperties.PROXY_URI, proxyUrl + ":" + proxyPort );
        clientConfig.property( ClientProperties.PROXY_USERNAME, proxyUsername );
        clientConfig.property( ClientProperties.PROXY_PASSWORD, proxyPassword );
        clientConfig.connectorProvider(new ApacheConnectorProvider());

        return clientConfig;
    }

    private void newCall() {

        this.payload = new HashMap<>();
        this.headers = new HashMap<>();
        webTarget = client.target( contextRoot );
    }

    public void close(){
        client.close();
    }

    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
        this.webTarget = this.client.target(contextRoot);
    }

    public String getContextRoot() {
        return this.contextRoot;
    }

}

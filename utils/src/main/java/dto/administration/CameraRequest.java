package dto.administration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by carlos.maycas on 24/06/2015.
 */
public class CameraRequest {

    @JsonProperty(value = "connectionId")
    private String connectionId;

    @JsonProperty(value = "cameraName")
    private String cameraName;

    @JsonProperty(value = "dbInstance")
    private String dbInstance;

    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "cameraCode")
    private String cameraCode;

    public CameraRequest() {
    }

    public CameraRequest(String connectionId, String cameraName, String dbInstance, String country, String cameraCode) {
        this.connectionId = connectionId;
        this.cameraName = cameraName;
        this.dbInstance = dbInstance;
        this.country = country;
        this.cameraCode = cameraCode;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getDbInstance() {
        return dbInstance;
    }

    public void setDbInstance(String dbInstance) {
        this.dbInstance = dbInstance;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCameraCode() {
        return cameraCode;
    }

    public void setCameraCode(String cameraCode) {
        this.cameraCode = cameraCode;
    }
}

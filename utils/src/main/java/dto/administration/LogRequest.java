package dto.administration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by carlos.maycas on 24/06/2015.
 */
public class LogRequest {

    @JsonProperty(value = "connectionId")
    private String connectionId;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "metadata")
    private String metadata;

    @JsonProperty(value = "validFlag")
    private String validFlag;

    @JsonProperty(value = "referenceId")
    private Long referenceId;

    @JsonProperty(value = "user")
    private String user;

    public LogRequest() {
    }

    public LogRequest(String connectionId, String type, String metadata, String validFlag, Long referenceId, String user) {
        this.connectionId = connectionId;
        this.type = type;
        this.metadata = metadata;
        this.validFlag = validFlag;
        this.referenceId = referenceId;
        this.user = user;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

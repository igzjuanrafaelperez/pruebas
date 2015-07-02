package dto.administration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Created by carlos.maycas on 24/06/2015.
 */
public class EventRequest {

    @JsonProperty(value = "connectionId")
    private String connectionId;

    @JsonProperty(value = "user")
    private String user;

    @JsonProperty(value = "date")
    private LocalDateTime date;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "operationReferenceId")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long operationReferenceId;

    public EventRequest() {
    }

    public EventRequest(String connectionId, String user, LocalDateTime date, String type, Long operationReferenceId) {

        this.connectionId = connectionId;
        this.user = user;
        this.date = date;
        this.type = type;
        this.operationReferenceId = operationReferenceId;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOperationReferenceId() {
        return operationReferenceId;
    }

    public void setOperationReferenceId(Long operationReferenceId) {
        this.operationReferenceId = operationReferenceId;
    }
}

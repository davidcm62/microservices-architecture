package ga.davidcm.authservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {

    public enum ConstructorType{
        TOKEN_CONSTRUCTOR,
        MESSAGE_CONSTRUCTOR
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date timestamp;
    private String token;
    private String message;

    public TokenResponse(ConstructorType type, String value) {
        this.token = null;
        this.message = null;
        this.timestamp = Date.from(Instant.now());

        if (type == ConstructorType.TOKEN_CONSTRUCTOR) this.token = value;
        if (type == ConstructorType.MESSAGE_CONSTRUCTOR) this.message = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public TokenResponse setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getToken() {
        return token;
    }

    public TokenResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public TokenResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenResponse that = (TokenResponse) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(token, that.token) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, token, message);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TokenResponse{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", token='").append(token).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

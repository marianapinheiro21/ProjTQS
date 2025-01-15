package api.mappings.generic;

import java.sql.Timestamp;

public class ErrorResponse {
    //TODO: update this with the error Response from your API
    private Timestamp timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    public String getMessage() {
        return this.message;
    }

    public String getPath() {
        return this.path;
    }

    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ErrorResponse)) return false;
        final ErrorResponse other = (ErrorResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        final Object this$error = this.getError();
        final Object other$error = other.getError();
        if (this$error == null ? other$error != null : !this$error.equals(other$error)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$path = this.getPath();
        final Object other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ErrorResponse;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        final Object $error = this.getError();
        result = result * PRIME + ($error == null ? 43 : $error.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $path = this.getPath();
        result = result * PRIME + ($path == null ? 43 : $path.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ErrorResponse(timestamp=" + this.getTimestamp() + ", status=" + this.getStatus() + ", error=" + this.getError() + ", message=" + this.getMessage() + ", path=" + this.getPath() + ")";
    }

    public ErrorResponse() {
    }
}

package api.mappings.generic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGenericRequest {
    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateGenericRequest)) return false;
        final CreateGenericRequest other = (CreateGenericRequest) o;
        if (!other.canEqual((Object) this)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateGenericRequest;
    }

    @Override
    public int hashCode() {
        final int result = 1;
        return result;
    }

    @Override
    public String toString() {
        return "CreateGenericRequest()";
    }

    public CreateGenericRequest() {
    }
    //TODO: replace this with the parameters of you request
}

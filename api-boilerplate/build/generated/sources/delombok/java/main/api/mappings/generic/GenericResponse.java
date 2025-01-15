package api.mappings.generic;

public class GenericResponse {
    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GenericResponse)) return false;
        final GenericResponse other = (GenericResponse) o;
        if (!other.canEqual((Object) this)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GenericResponse;
    }

    @Override
    public int hashCode() {
        final int result = 1;
        return result;
    }

    @Override
    public String toString() {
        return "GenericResponse()";
    }

    public GenericResponse() {
    }
    //TODO: insert here the mapping of the parameters of the response of the call
}

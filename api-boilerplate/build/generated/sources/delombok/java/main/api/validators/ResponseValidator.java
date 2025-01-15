package api.validators;

import api.mappings.generic.Member;
import retrofit2.Response;
import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_BAD_METHOD;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_GONE;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_ACCEPTABLE;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_PRECON_FAILED;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ResponseValidator {
    /**
     * Can use this class to check all the most common errors
     */
    public static void assertMember(Response response, Member memberResponse, Member memberRequest) {
        assertThat("Body is not null", memberResponse, notNullValue());
        assertThat("id should not be null", memberResponse.getId(), notNullValue());
        assertThat("First name is not the expected", memberResponse.getFirstName(), is(memberRequest.getFirstName()));
        assertThat("Last name is not the expected", memberResponse.getLastName(), is(memberRequest.getLastName()));
        assertThat("Address is not the expected", memberResponse.getAddress(), is(memberRequest.getAddress()));
        assertThat("Postal Code is not the expected", memberResponse.getPostalCode(), is(memberRequest.getPostalCode()));
        assertThat("City is not the expected", memberResponse.getCity(), is(memberRequest.getCity()));
        assertThat("Country is not the expected", memberResponse.getCountry(), is(memberRequest.getCountry()));
        assertThat("Phone Number is not the expected", memberResponse.getPhoneNumber(), is(memberRequest.getPhoneNumber()));
        assertThat("Nif is not the expected", memberResponse.getNif(), is(memberRequest.getNif()));
        assertThat("Email is not the expected", memberResponse.getEmail(), is(memberRequest.getEmail()));
        assertThat("Birth Date is not the expected", memberResponse.getBirthDate(), is(memberRequest.getBirthDate()));
        assertThat("Registration Date is not the expected", memberResponse.getRegistrationDate(), is(memberRequest.getRegistrationDate()));
    }

    public static void assertOk(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_OK, response.code()), response.code(), is(HTTP_OK));
    }

    public static void assertCreated(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_CREATED, response.code()), response.code(), is(HTTP_CREATED));
    }

    public static void assertAccepted(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_ACCEPTED, response.code()), response.code(), is(HTTP_ACCEPTED));
    }

    public static void assertNotAcceptable(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_NOT_ACCEPTABLE, response.code()), response.code(), is(HTTP_NOT_ACCEPTABLE));
    }

    public static void assertNoContent(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_NO_CONTENT, response.code()), response.code(), is(HTTP_NO_CONTENT));
    }

    public static void assertBadRequest(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_BAD_REQUEST, response.code()), response.code(), is(HTTP_BAD_REQUEST));
    }

    public static void assertUnauthorized(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_UNAUTHORIZED, response.code()), response.code(), is(HTTP_UNAUTHORIZED));
    }

    public static void assertForbidden(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_FORBIDDEN, response.code()), response.code(), is(HTTP_FORBIDDEN));
    }

    public static void assertNotFound(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_NOT_FOUND, response.code()), response.code(), is(HTTP_NOT_FOUND));
    }

    public static void assertMethodNotAllowed(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_BAD_METHOD, response.code()), response.code(), is(HTTP_BAD_METHOD));
    }

    public static void assertConflict(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_CONFLICT, response.code()), response.code(), is(HTTP_CONFLICT));
    }

    public static void assertGone(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_GONE, response.code()), response.code(), is(HTTP_GONE));
    }

    public static void assertPreconditionFailed(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_PRECON_FAILED, response.code()), response.code(), is(HTTP_PRECON_FAILED));
    }

    public static void assertInternalServerError(Response response) {
        assertThat(String.format("Expected response code to be [%s] but was [%s]", HTTP_INTERNAL_ERROR, response.code()), response.code(), is(HTTP_INTERNAL_ERROR));
    }

    private ResponseValidator() {
    }
}

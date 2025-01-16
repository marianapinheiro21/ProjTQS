package api.validators;

import api.mappings.generic.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseValidator {

    public static void assertErrorResponse(ErrorResponse errorResponse, Integer statusCode, String error, String message, String path) {
        assertThat("Timestamp should not be null", errorResponse.getTimestamp(), notNullValue());
        assertThat("status is not the expected", errorResponse.getStatus(), is(statusCode));
        assertThat("Error is not the expected", errorResponse.getError(), is(error));
        assertThat("Message is not the expected", errorResponse.getMessage(), is(message));
        assertThat("Path is not the expected", errorResponse.getPath(), is(path));

    }
}

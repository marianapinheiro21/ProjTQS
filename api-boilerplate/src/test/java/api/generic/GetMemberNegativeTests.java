package api.generic;

import api.mappings.generic.ErrorResponse;
import api.mappings.generic.Member;
import api.retrofit.generic.Errors;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.Members.*;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetMemberNegativeTests {

    @Test(description = "Failing to get a member")
    public void getMemberNegativeTest() throws IOException {
        Integer id = 99;
        Response<Member> response = getMemberByID(id);
        assertNotFound(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertThat("Timestamp should not be null", errorResponse.getTimestamp(), notNullValue());
        assertThat("status is not the expected", errorResponse.getStatus(), is(404));
        assertThat("Error is not the expected", errorResponse.getError(), is("Not Found"));
        assertThat("Message is not the expected", errorResponse.getMessage(), is("Member not found"));
        assertThat("Path is not the expected", errorResponse.getPath(), is(String.format("/member/%d", id)));

        assertErrorResponse(errorResponse, 404, "Not Found", "Member not found", String.format("/member/%d", id));
    }
}

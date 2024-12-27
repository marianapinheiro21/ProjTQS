package api.generic;

import api.mappings.generic.CreateGenericRequest;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.generic.Generic.createGenericCall;
import static api.retrofit.generic.Generic.getGenericCall;
import static api.validators.ResponseValidator.assertCreated;
import static api.validators.ResponseValidator.assertOk;

public class GenericCallTests {

    @Test(description = "get something from a generic Call")
    public void getSomethingFromGenericCallTest() throws IOException {
        Response<ResponseBody> response = getGenericCall();
        assertOk(response);
    }

    @Test(description = "create something using a call without authentication")
    public void createSomethingTest() throws IOException {
        Response<ResponseBody> response = createGenericCall(new CreateGenericRequest());
        assertCreated(response);
    }
}

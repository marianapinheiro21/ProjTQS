package api.retrofit.generic;

import api.calls.GenericCalls;
import api.mappings.generic.CreateGenericRequest;
import api.retrofit.RetrofitBuilder;
import okhttp3.ResponseBody;
import retrofit2.Response;
import java.io.IOException;
import static lombok.AccessLevel.PRIVATE;

public class Generic {
    private static final GenericCalls genericCalls = new RetrofitBuilder().getRetrofit().create(GenericCalls.class);

    public static Response<ResponseBody> getGenericCall() throws IOException {
        return genericCalls.getGenericCall().execute();
    }

    public static Response<ResponseBody> createGenericCall(CreateGenericRequest createGenericRequest) throws IOException {
        return genericCalls.createGenericCall(createGenericRequest).execute();
    }

    private Generic() {
    }
}

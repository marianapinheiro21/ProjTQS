package api.calls;

import api.mappings.generic.CreateGenericRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GenericCalls {
    String PATH = "path";
    String ACCEPT = "accept";
    String AUTHORIZATION = "Authorization";


    @GET(PATH)
    Call<ResponseBody> getGenericCall();

    @POST(PATH)
    Call<ResponseBody> createGenericCall(@Body CreateGenericRequest createGenericRequest);

}

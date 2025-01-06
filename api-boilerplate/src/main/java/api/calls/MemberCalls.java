package api.calls;

import api.mappings.generic.Member;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MemberCalls {

    String MEMBER="member";
    String MEMBER_ID="member/{id}";

    @GET(MEMBER)
    Call<List<Member>> getAllMember();

    @GET(MEMBER_ID)
    Call<Member> getMemberByID(@Path("id") Integer id);

    @POST(MEMBER)
    Call<ResponseBody> createMember(@Body Member member);

    @DELETE(MEMBER_ID)
    Call<ResponseBody> deleteMember(@Path("id") Integer id);

    @PUT(MEMBER_ID)
    Call<Member> updateMember(@Path("id") Integer id, @Body Member member);
}

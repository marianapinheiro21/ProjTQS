package api.retrofit;

import api.calls.MemberCalls;
import api.mappings.generic.Member;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.util.List;

public class Members {

    public static final MemberCalls memberCalls = new RetrofitBuilder().getRetrofit().create(MemberCalls.class);

    @SneakyThrows
    public static Response<List<Member>> getAllMembers() {
        return memberCalls.getAllMember().execute();
    }

    @SneakyThrows
    public static Response<Member> getMemberByID(Integer MemberId) {
        return memberCalls.getMemberByID(MemberId).execute();
    }
}

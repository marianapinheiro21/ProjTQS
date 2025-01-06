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
    public static Response<Member> getMemberByID(Integer memberId) {
        return memberCalls.getMemberByID(memberId).execute();
    }

    @SneakyThrows
    public static Response<ResponseBody> createMember(Member member) {
        return memberCalls.createMember(member).execute();
    }

    @SneakyThrows
    public static Response<ResponseBody> deleteMember(Integer memberId) {
        return memberCalls.deleteMember(memberId).execute();
    }

    @SneakyThrows
    public static Response<Member> updateMember(Integer memberId, Member member) {
        return memberCalls.updateMember(memberId, member).execute();
    }
}

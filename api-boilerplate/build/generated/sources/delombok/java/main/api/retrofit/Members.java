package api.retrofit;

import api.calls.MemberCalls;
import api.mappings.generic.Member;
import okhttp3.ResponseBody;
import retrofit2.Response;
import java.util.List;

public class Members {
    public static final MemberCalls memberCalls = new RetrofitBuilder().getRetrofit().create(MemberCalls.class);

    public static Response<List<Member>> getAllMembers() {
        try {
            return memberCalls.getAllMember().execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<Member> getMemberByID(Integer memberId) {
        try {
            return memberCalls.getMemberByID(memberId).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<ResponseBody> createMember(Member member) {
        try {
            return memberCalls.createMember(member).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<ResponseBody> deleteMember(Integer memberId) {
        try {
            return memberCalls.deleteMember(memberId).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<Member> updateMember(Integer memberId, Member member) {
        try {
            return memberCalls.updateMember(memberId, member).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }
}

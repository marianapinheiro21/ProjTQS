package api.mappings.Member;

import api.mappings.generic.ErrorResponse;
import api.mappings.generic.Member;
import api.retrofit.generic.Errors;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Members.deleteMember;
import static api.retrofit.Members.updateMember;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertNotFound;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateMemberNegativeTest {

    @SneakyThrows
    @Test(description = "Update Member with no Success")       //Na documentação está explicito que deveria retornar o erro 404, no entanto está a devolver que apagou com sucesso um membro que não existe
    public void deleteMemberSuccess() {
        Member memberUpdate = Member.builder()
                .address("Ilha das Flores")
                .build();

        Integer id = 45;  //Estou a testar com o id 45 porque sei de antemão que há apenas 2 membros
        Response<Member> response = updateMember(id, memberUpdate);
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

package api.mappings.Member;

import api.mappings.generic.Member;
import okhttp3.ResponseBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.retrofit.Members.*;
import static api.validators.ResponseValidator.assertNoContent;
import static api.validators.ResponseValidator.assertNotFound;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteMemberPositiveTest {

    Integer memberId;

    @BeforeMethod
    public void setUpMember() throws IOException {
        Member memberRequest = Member.builder()
                .firstName("Joaquim")
                .lastName("Daniel")
                .address("Rua das Flores")
                .postalCode("3898-098")
                .city("Amarante")
                .country("Portugal")
                .phoneNumber(963852741)
                .nif(123456789)
                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertThat(response.code(), is(201));

        assertThat("Body is not null", response.body(), notNullValue());
        memberId = Integer.parseInt(response.body().string());
    }


    @Test(description = "Delete Member with Success")
    public void deleteMemberSuccess() {
        Response<ResponseBody> response = deleteMember(this.memberId);
        assertNoContent(response);

        Response<Member> responseGet = getMemberByID(memberId);
        assertNotFound(responseGet);
    }
}

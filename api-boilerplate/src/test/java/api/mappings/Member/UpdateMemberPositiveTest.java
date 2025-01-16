package api.mappings.Member;

import api.mappings.generic.Member;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.retrofit.Members.*;
import static api.validators.ResponseValidator.assertMember;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateMemberPositiveTest {

    Integer memberId;

    @SneakyThrows
    @BeforeMethod
    public void setUpMember(){
        Member memberRequest = Member.builder()
                .firstName("Manuel")
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

        assert response.body() != null;
        memberId = Integer.parseInt(response.body().string());
    }

    @AfterMethod
    public void cleanUp() {
        deleteMember(memberId);
    }


    @SneakyThrows
    @Test (description =  "Editar um Member")       //Teste falha
    public void updateMemberPositiveTest1(){
        Member memberUpdate = Member.builder()
                .firstName("Joaquim")
                .lastName("Naniel")
                .build();

        Response<Member> response = updateMember(memberId, memberUpdate);
        Member memberResponse = response.body();
        Member memberRequest = memberUpdate;

        assertOk(response);
        assertMember(response, memberResponse, memberRequest);

    }

    @Test (description =  "Editar um Member")       //Teste falha, É sempre Bad Request
    public void updateMemberPositiveTest2(){
        Member memberUpdate = Member.builder()
                .firstName("Joaquim")
                .lastName("Daniel")
                .address("Ilha das Flores")
                .postalCode("3898-098")
                .city("Amarante")
                .country("Portugal")
                .phoneNumber(963852741)
                .nif(123456789)
                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
                .registrationDate(LocalDate.now())
                .build();

        Response<Member> response = updateMember(memberId, memberUpdate);
        Member memberResponse = response.body();
        Member memberRequest = memberUpdate;

        assertOk(response);
        assertMember(response, memberResponse, memberRequest);
    }

}

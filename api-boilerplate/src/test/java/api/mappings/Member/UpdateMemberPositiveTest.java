package api.mappings.Member;

import api.mappings.generic.Member;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.retrofit.Members.*;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateMemberPositiveTest {

    Integer memberId;
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
        memberId = Integer.parseInt(response.body().toString());
    }

    @AfterMethod
    public void cleanUp() {
        deleteMember(memberId);
    }


    @Test (description =  "Editar um Member")       //Teste falha
    public void updateMemberPositiveTest1(){
        Member memberUpdate = Member.builder()
                .firstName("Joaquim")
                .lastName("Daniel")
                .build();

        Response<Member> response = updateMember(memberId, memberUpdate);
        Member memberResponse = response.body();
        Member memberRequest = memberUpdate;

        assertOk(response);
        assertThat("Body is not null", memberResponse, notNullValue());
        assertThat("id should not be null", memberResponse.getId(), notNullValue());
        assertThat("First name is not the expected", memberResponse.getFirstName(), is(memberRequest.getFirstName()));
        assertThat("Last name is not the expected", memberResponse.getLastName(), is(memberRequest.getLastName()));
        assertThat("Address is not the expected", memberResponse.getAddress(), is(memberRequest.getAddress()));
        assertThat("Postal Code is not the expected", memberResponse.getPostalCode(), is(memberRequest.getPostalCode()));
        assertThat("City is not the expected", memberResponse.getCity(), is(memberRequest.getCity()));
        assertThat("Country is not the expected", memberResponse.getCountry(), is(memberRequest.getCountry()));
        assertThat("Phone Number is not the expected", memberResponse.getPhoneNumber(), is(memberRequest.getPhoneNumber()));
        assertThat("Nif is not the expected", memberResponse.getNif(), is(memberRequest.getNif()));
        assertThat("Email is not the expected", memberResponse.getEmail(), is(memberRequest.getEmail()));
        assertThat("Birth Date is not the expected", memberResponse.getBirthDate(), is(memberRequest.getBirthDate()));
        assertThat("Registration Date is not the expected", memberResponse.getRegistrationDate(), is(memberRequest.getRegistrationDate()));


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
        assertThat("Body is not null", memberResponse, notNullValue());
        assertThat("id should not be null", memberResponse.getId(), notNullValue());
        assertThat("First name is not the expected", memberResponse.getFirstName(), is(memberRequest.getFirstName()));
        assertThat("Last name is not the expected", memberResponse.getLastName(), is(memberRequest.getLastName()));
        assertThat("Address is not the expected", memberResponse.getAddress(), is(memberRequest.getAddress()));
        assertThat("Postal Code is not the expected", memberResponse.getPostalCode(), is(memberRequest.getPostalCode()));
        assertThat("City is not the expected", memberResponse.getCity(), is(memberRequest.getCity()));
        assertThat("Country is not the expected", memberResponse.getCountry(), is(memberRequest.getCountry()));
        assertThat("Phone Number is not the expected", memberResponse.getPhoneNumber(), is(memberRequest.getPhoneNumber()));
        assertThat("Nif is not the expected", memberResponse.getNif(), is(memberRequest.getNif()));
        assertThat("Email is not the expected", memberResponse.getEmail(), is(memberRequest.getEmail()));
        assertThat("Birth Date is not the expected", memberResponse.getBirthDate(), is(memberRequest.getBirthDate()));
        assertThat("Registration Date is not the expected", memberResponse.getRegistrationDate(), is(memberRequest.getRegistrationDate()));


    }

}

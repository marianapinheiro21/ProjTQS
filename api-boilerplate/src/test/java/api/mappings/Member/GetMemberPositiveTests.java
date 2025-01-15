package api.mappings.Member;

import api.mappings.generic.Member;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static api.retrofit.Members.*;
import static api.validators.ResponseValidator.assertMember;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetMemberPositiveTests {

    Integer memberId;
    Member member;
    @BeforeMethod
    public void setUpMember() throws IOException {
        member = Member.builder()
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
        Response<ResponseBody> response = createMember(member);
        assertThat(response.code(), is(201));

        assertThat("Body is not null", response.body(), notNullValue());
        memberId = Integer.parseInt(response.body().string());
    }

    @AfterMethod
    public void cleanUP() {
        deleteMember(memberId);
    }


    @Test(description = "Get all Members with success")
    public void getAllMembersWithSuccessTest() {
        Response<List<Member>> response = getAllMembers();
        assertOk(response);

        assertThat("Body is not null", response.body(), notNullValue());

        List<Member> memberList = response.body();
        assertThat("Lists should have more than one element", memberList, hasSize(greaterThanOrEqualTo(1)));
    }

    @Test(description = "Get Member by ID with success")
    public void getMemberByIdWithSuccessTest() {
        Response<Member> response = getMemberByID(2);
        assertOk(response);

        assertThat("Body is not null", response.body(), notNullValue());

        Member member = response.body();
        assertThat("First Name is not the expected", member.getFirstName(), is("Maria"));
        assertThat("Last Name is not the expected", member.getLastName(), is("Lopes"));
        assertThat("Address is not the expected", member.getAddress(), is("Avenida Principal"));
        assertThat("Postal code is not the expected", member.getPostalCode(), is("4350-334"));
        assertThat("City is not the expected", member.getCity(), is("Porto"));
        assertThat("Country is not the expected", member.getCountry(), is("Portugal"));
        assertThat("Phone Number is not the expected", member.getPhoneNumber(), is(911222333));
        assertThat("NIF is not the expected", member.getNif(), is(222333444));
        assertThat("Email is not the expected", member.getEmail(), is("maria@corp.com"));
        assertThat("Birth Date is not the expected", member.getBirthDate(), is(member.getBirthDate()));
        assertThat("Registration Date is not the expected", member.getRegistrationDate(), is(member.getRegistrationDate()));

    }

    @Test(description = "Get Member by ID with success")
    public void getMemberByIdWithSuccessTest2() {
        Response<Member> response = getMemberByID(memberId);
        assertOk(response);

        assertThat("Body is not null", response.body(), notNullValue());

        Member memberResponse = response.body();

        assertThat("Body is not null", memberResponse, notNullValue());
        //assertThat("id should not be null", memberResponse.getId(), notNullValue());
        assertThat("First name is not the expected", memberResponse.getFirstName(), is(member.getFirstName()));
        assertThat("Last name is not the expected", memberResponse.getLastName(), is(member.getLastName()));
        assertThat("Address is not the expected", memberResponse.getAddress(), is(member.getAddress()));
        assertThat("Postal Code is not the expected", memberResponse.getPostalCode(), is(member.getPostalCode()));
        assertThat("City is not the expected", memberResponse.getCity(), is(member.getCity()));
        assertThat("Country is not the expected", memberResponse.getCountry(), is(member.getCountry()));
        assertThat("Phone Number is not the expected", memberResponse.getPhoneNumber(), is(member.getPhoneNumber()));
        assertThat("Nif is not the expected", memberResponse.getNif(), is(member.getNif()));
        assertThat("Email is not the expected", memberResponse.getEmail(), is(member.getEmail()));
        assertThat("Birth Date is not the expected", memberResponse.getBirthDate(), is(member.getBirthDate()));
        assertThat("Registration Date is not the expected", memberResponse.getRegistrationDate(), is(member.getRegistrationDate()));
    }

}

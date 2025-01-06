package api.mappings.Member;

import api.mappings.generic.Member;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import retrofit2.Response;
import java.util.List;

import static api.retrofit.Members.*;
import static org.testng.Assert.*;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetMemberPositiveTests {

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


}

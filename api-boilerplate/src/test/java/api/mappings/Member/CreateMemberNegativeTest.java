package api.mappings.Member;

import api.mappings.generic.ErrorResponse;
import api.mappings.generic.Member;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.retrofit.Members.createMember;
import static api.retrofit.Members.getMemberByID;
import static api.retrofit.generic.Errors.getErrorsResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertBadRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateMemberNegativeTest {

    Integer memberId;

    @Test(description = "Create new member with birth date in the future")
    public void createMemberNegativeTest1()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
                .postalCode("3898-098")
//                .city("Amarante")
                .phoneNumber(963852741)
                .nif(123456789)
//                .email("john@doe.com")
                .birthDate(LocalDate.now().plusYears(3))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Birth Date", "/member");
    }

    @Test(description = "Create new member with no Phone number")
    public void createMemberNegativeTest2()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
                .postalCode("3898-098")
//                .city("Amarante")
//               .phoneNumber(963852741)
                .nif(123456789)
//                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Phone Number", "/member");
    }

    @Test(description = "Create new member with no Postal Code ")       //Está a devolver erro 500 quando deveria ser 400
    public void createMemberNegativeTest3()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
//                .postalCode("3898-098")
//                .city("Amarante")
                .phoneNumber(963852741)
                .nif(123456789)
//                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
      assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Postal Code", "/member");
    }

    @Test(description = "Create new member with no nif")
    public void createMemberNegativeTest4()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
                .postalCode("3898-098")
//                .city("Amarante")
                .phoneNumber(963852741)
//                .nif(123456789)
//                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid NIF", "/member");
    }

    @Test(description = "Create new member with no birth date")       //Está a devolver erro 500 quando deveria ser 400
    public void createMemberNegativeTest5()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
                .postalCode("3898-098")
//                .city("Amarante")
                .phoneNumber(963852741)
                .nif(123456789)
//                .email("john@doe.com")
//                .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Birth Date", "/member");
    }

    @Test(description = "Create new member with no Postal code nor phone number")      // Deveria queixar-se do codigo postal primeiro
    public void createMemberNegativeTest6()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
//                .postalCode("3898-098")
//                .city("Amarante")
 //               .phoneNumber(963852741)
                .nif(123456789)
//                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Postal Code", "/member");
    }

    @Test(description = "Create new member with no postal code, nif")
    public void createMemberNegativeTest7()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
 //               .postalCode("3898-098")
//                .city("Amarante")
                .phoneNumber(963852741)
//                .nif(123456789)
//                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Postal Code", "/member");
    }

    @Test(description = "Create new member with no Postal Code Birth date")
    public void createMemberNegativeTest8()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
//                .postalCode("3898-098")
//                .city("Amarante")
                .phoneNumber(963852741)
                .nif(123456789)
//                .email("john@doe.com")
 //               .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Postal Code", "/member");
    }

    @Test(description = "Create new member with no phone number nif ")
    public void createMemberNegativeTest9()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
                .postalCode("3898-098")
//                .city("Amarante")
 //               .phoneNumber(963852741)
 //               .nif(123456789)
//                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Phone Number", "/member");
    }

    @Test(description = "Create new member with no  phone number birth date")
    public void createMemberNegativeTest10()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
                .postalCode("3898-098")
//                .city("Amarante")
 //               .phoneNumber(963852741)
                .nif(123456789)
//                .email("john@doe.com")
 //               .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Phone Number", "/member");
    }

    @Test(description = "Create new member with no postal code phone number nif")
    public void createMemberNegativeTest11()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
//                .postalCode("3898-098")
//                .city("Amarante")
  //              .phoneNumber(963852741)
    //            .nif(123456789)
//                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Postal Code", "/member");
    }

    @Test(description = "Create new member with no postal code phone number birth date ")
    public void createMemberNegativeTest12()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
  //              .postalCode("3898-098")
//                .city("Amarante")
    //            .phoneNumber(963852741)
                .nif(123456789)
//                .email("john@doe.com")
      //          .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Postal Code", "/member");
    }

    @Test(description = "Create new member with no phone number nif birth date")
    public void createMemberNegativeTest13()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
                .postalCode("3898-098")
//                .city("Amarante")
 //               .phoneNumber(963852741)
 //               .nif(123456789)
//                .email("john@doe.com")
  //              .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Phone Number", "/member");
    }

    @Test(description = "Create new member with no body")
    public void createMemberNegativeTest14()  throws IOException {
        Member memberRequest = Member.builder()
//                .firstName("Joaquim")
//                .lastName("Daniel")
//                .address("Rua das Flores")
  //              .postalCode("3898-098")
//                .city("Amarante")
    //            .phoneNumber(963852741)
     //           .nif(123456789)
//                .email("john@doe.com")
       //         .birthDate(LocalDate.parse("1999-09-09"))
//                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
       assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Postal Code", "/member");
    }

    // Em retrospectiva há aqui muito teste inutil, sabendo que o teste vai falar quando falta um campo, não faz sentido testar o que acontece quando faltam 2 ou três campos
    // Testar sem Body já é diferente, acho que faz sentido
}

package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.ErrorResponse;
import api.retrofit.generic.Errors;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.assertBadRequest;
import static api.validators.ErrorResponseValidator.assertErrorResponse;

public class CreateBookNegativeTests {

    @DataProvider(name = "invalidBookData")
    public Object[][] invalidBookData() {
        return new Object[][] {
                {"", "George R.R. Martin", "Título não pode estar vazio"},
                {"Game of Thrones", "", "Autor não pode estar vazio"},
                {"Harry Potter", "J.K. Rowling", null, "ISBN é obrigatório"},
                {"SQL!@#$%", "Stephen King", "Título contém caracteres especiais não permitidos"},
                {"O Senhor dos Anéis", "!@#$% Tolkien", "Nome do autor contém caracteres especiais"},
                {"O".repeat(256), "Paulo Coelho", "Título excede o limite de 255 caracteres"},
                {"Dom Casmurro", "M".repeat(256), "Nome do autor excede o limite de 255 caracteres"},
                {"1984", "George Orwell", "0000000000000", "ISBN inválido - não pode ser todos zeros"},
                {"Duna", "Frank Herbert", "abc1234567890", "ISBN deve conter apenas números"}
        };
    }

    @Test(dataProvider = "invalidBookData")
    public void createBookWithInvalidDataTest(String title, String author, String isbn, String expectedError)
            throws IOException {
        Book bookRequest = createDefaultTestBook();
        bookRequest.setTitle(title);
        bookRequest.setAuthor(author);
        bookRequest.setIsbn(isbn);

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",
                expectedError, "/book");
    }

    @Test(description = "Create book with duplicate ISBN")
    public void createBookWithDuplicateIsbnTest() throws IOException {
        Book firstBook = Book.builder()
                .title("O Hobbit")
                .author("J.R.R. Tolkien")
                .isbn("9788533615540")
                .publisher("HarperCollins")
                .build();

        Response<ResponseBody> firstResponse = createBook(firstBook);
        Integer firstBookId = Integer.parseInt(firstResponse.body().string());

        try {
            Book secondBook = Book.builder()
                    .title("O Hobbit - Segunda Edição")
                    .author("J.R.R. Tolkien")
                    .isbn("9788533615540") // Mesmo ISBN
                    .publisher("HarperCollins")
                    .build();

            Response<ResponseBody> response = createBook(secondBook);
            assertConflict(response);

            ErrorResponse error = Errors.getErrorsResponse(response);
            assertErrorResponse(error, 409, "Conflict",
                    "ISBN already exists", "/book");
        } finally {
            deleteBook(firstBookId);
        }
    }

    @Test(description = "Create book with future edition year")
    public void createBookWithFutureYearTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("JavaScript 2025")
                .author("John Developer")
                .isbn(generateUniqueIsbn())
                .editionYear(2025)
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",
                "Edition year cannot be in the future", "/book");
    }

    @Test(description = "reate book with invalid status")
    public void createBookWithInvalidStatusTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn(generateUniqueIsbn())
                .status("lido")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",
                "Invalid book status", "/book");
    }

    @Test(description = "Trying to create a book with a very long description")
    public void createBookWithLongDescriptionTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn(generateUniqueIsbn())
                .description("D".repeat(1001))
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",
                "Book description exceeds 1000 character limit", "/book");
    }

    @Test(description = "Try to create book with blank data")
    public void createBookWithBlankDataTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("   ")
                .author("   ")
                .isbn(generateUniqueIsbn())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",

                "Title and author cannot contain only white spaces", "/book");
    }

    @Test(description = "Trying to create a book with a very old edition year")
    public void createBookWithVeryOldYearTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Livro Antigo")
                .author("Autor Antigo")
                .isbn(generateUniqueIsbn())
                .editionYear(1449)
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",
                "Invalid edition year: must be after 1450", "/book");
    }
}
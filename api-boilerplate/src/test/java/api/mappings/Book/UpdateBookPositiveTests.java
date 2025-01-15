package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UpdateBookPositiveTests {

    private Integer bookId;


    @BeforeMethod
    public void setup() throws IOException {
        Book bookRequest = Book.builder()
                .title("Oliver na penacova")
                .author("Olivier")
                .isbn(String.valueOf(System.currentTimeMillis()))
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        bookId = Integer.parseInt(response.body().string());
    }

    @AfterMethod
    public void cleanup() {
        if (bookId != null) {
            deleteBook(bookId);
        }
    }

    @Test(description = "Update book status")
    public void updateBookStatusTest() throws IOException {
        Book bookUpdate = Book.builder()
                .status(BookStatus.RESERVED.toString())
                .build();

        Response<Book> response = updateBook(bookId, bookUpdate);
        assertOk(response);

        Book updatedBook = getBookById(bookId).body();
        assertThat(updatedBook.getStatus(), is(BookStatus.RESERVED.toString()));
    }

    @Test(description = "Update all book fields")
    public void updateAllBookFieldsTest() throws IOException {
        Book bookUpdate = Book.builder()
                .title("Cenas do bem")
                .author("Zé cAricas")
                .publisher("Editora das folhas")
                .editionYear(2022)
                .edition("2")
                .description("Não vai querer ler")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<Book> response = updateBook(bookId, bookUpdate);
        assertOk(response);

        Book updatedBook = getBookById(bookId).body();
        assertThat(updatedBook.getTitle(), is(bookUpdate.getTitle()));
        assertThat(updatedBook.getAuthor(), is(bookUpdate.getAuthor()));
        assertThat(updatedBook.getPublisher(), is(bookUpdate.getPublisher()));
        assertThat(updatedBook.getEditionYear(), is(bookUpdate.getEditionYear()));
        assertThat(updatedBook.getEdition(), is(bookUpdate.getEdition()));
        assertThat(updatedBook.getDescription(), is(bookUpdate.getDescription()));
        assertThat(updatedBook.getIsbn(), is(bookUpdate.getIsbn()));
        assertThat(updatedBook.getStatus(), is(bookUpdate.getStatus()));
    }
}
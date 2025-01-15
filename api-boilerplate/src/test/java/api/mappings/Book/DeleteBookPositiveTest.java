package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.Book.BookStatus;
import okhttp3.ResponseBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeleteBookPositiveTest {
    private Book testBook;

    @BeforeMethod
    public void setup() throws IOException {
        testBook = createTestBook();
    }

    @Test(description = "Delete book with AVAILABLE status")
    public void deleteAvailableBookTest() throws IOException {
        Response<ResponseBody> deleteResponse = deleteBook(testBook.getId(), false);
        assertNoContent(deleteResponse);


        Response<Book> getResponse = getBookById(testBook.getId());
        assertNotFound(getResponse);
    }

    @Test(description = "Delete book with force removal")
    public void deleteBookForceRemovalTest() throws IOException {
        Book reservedBook = createBookWithStatus(BookStatus.RESERVED);

        Response<ResponseBody> deleteResponse = deleteBook(reservedBook.getId(), true);
        assertNoContent(deleteResponse);


        Response<Book> getResponse = getBookById(reservedBook.getId());
        assertNotFound(getResponse);
    }

    @Test(description = "Delete multiple books sequentially")
    public void deleteMultipleBooksTest() throws IOException {
        List<Integer> bookIds = createMultipleBooks(5);

        for (Integer bookId : bookIds) {
            Response<ResponseBody> deleteResponse = deleteBook(bookId, false);
            assertNoContent(deleteResponse);


            Response<Book> getResponse = getBookById(bookId);
            assertNotFound(getResponse);
        }
    }

    @DataProvider(name = "validStatusesForDeletion")
    public Object[][] validStatusesForDeletion() {
        return new Object[][] {
                {BookStatus.AVAILABLE, false},
                {BookStatus.NOT_AVAILABLE, false},
                {BookStatus.RESERVED, true}
        };
    }

    @Test(dataProvider = "validStatusesForDeletion", description = "Delete books with different statuses")
    public void deleteBookDifferentStatusesTest(BookStatus status, boolean forceRemove) throws IOException {
        Book statusBook = createBookWithStatus(status);

        Response<ResponseBody> deleteResponse = deleteBook(statusBook.getId(), forceRemove);
        assertNoContent(deleteResponse);


        Response<Book> getResponse = getBookById(statusBook.getId());
        assertNotFound(getResponse);
    }

    @Test(description = "Delete book and verify in book list")
    public void deleteBookVerifyListTest() throws IOException {

        Response<List<Book>> initialListResponse = getAllBooks();
        assertOk(initialListResponse);
        int initialCount = initialListResponse.body().size();


        Response<ResponseBody> deleteResponse = deleteBook(testBook.getId(), false);
        assertNoContent(deleteResponse);


        Response<List<Book>> finalListResponse = getAllBooks();
        assertOk(finalListResponse);
        int finalCount = finalListResponse.body().size();

        assertThat(finalCount, is(initialCount - 1));
        assertThat(finalListResponse.body(), not(hasItem(hasProperty("id", is(testBook.getId())))));
    }

    @Test(description = "Delete and recreate book with same ISBN")
    public void deleteAndRecreateBookTest() throws IOException {
        String originalIsbn = testBook.getIsbn();


        Response<ResponseBody> deleteResponse = deleteBook(testBook.getId(), false);
        assertNoContent(deleteResponse);


        Book newBook = Book.builder()
                .title("New Book")
                .author("New Author")
                .isbn(originalIsbn)
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> createResponse = createBook(newBook);
        assertCreated(createResponse);

        // Verificar se o novo livro foi criado corretamente
        Response<Book> getResponse = getBookById(createResponse.body());
        assertOk(getResponse);
        assertThat(getResponse.body().getIsbn(), is(originalIsbn));
    }


    private Book createTestBook() throws IOException {
        Book book = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .publisher("Test Publisher")
                .editionYear(2023)
                .edition("1st Edition")
                .description("Test Description")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertCreated(response);
        Integer id = response.body();
        book.setId(id);
        return book;
    }

    private Book createBookWithStatus(BookStatus status) throws IOException {
        Book book = Book.builder()
                .title("Status Test Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(status)
                .build();

        Response<Integer> response = createBook(book);
        assertCreated(response);
        Integer id = response.body();
        book.setId(id);
        return book;
    }

    private List<Integer> createMultipleBooks(int count) throws IOException {
        List<Integer> bookIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Book book = Book.builder()
                    .title("Book " + i)
                    .author("Author " + i)
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE)
                    .build();

            Response<Integer> response = createBook(book);
            assertCreated(response);
            bookIds.add(response.body());
        }
        return bookIds;
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}
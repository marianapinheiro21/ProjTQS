package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.Book.BookStatus;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
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

public class GetBookPositiveTest {
    private List<Integer> bookIds;
    private Book testBook;

    @BeforeMethod
    public void setup() throws IOException {
        bookIds = new ArrayList<>();
        testBook = createTestBook();
    }

    @AfterMethod
    public void cleanup() throws IOException {
        for (Integer id : bookIds) {
            deleteBook(id, true);
        }
    }

    @Test(description = "Get book by valid ID")
    public void getBookByValidIdTest() throws IOException {
        Response<Book> response = getBookById(testBook.getId());
        assertOk(response);

        Book retrievedBook = response.body();
        assertThat(retrievedBook, notNullValue());
        assertBookEquals(retrievedBook, testBook);
    }

    @Test(description = "Get all books when empty")
    public void getAllBooksEmptyTest() throws IOException {

        cleanup();

        Response<List<Book>> response = getAllBooks();
        assertOk(response);

        List<Book> books = response.body();
        assertThat(books, notNullValue());
        assertThat(books, empty());
    }

    @Test(description = "Get all books with multiple records")
    public void getAllBooksMultipleTest() throws IOException {
        int numberOfBooks = 5;
        List<Book> createdBooks = createMultipleBooks(numberOfBooks);

        Response<List<Book>> response = getAllBooks();
        assertOk(response);

        List<Book> retrievedBooks = response.body();
        assertThat(retrievedBooks, hasSize(greaterThanOrEqualTo(numberOfBooks)));

        for (Book created : createdBooks) {
            assertThat(retrievedBooks, hasItem(hasProperty("isbn", is(created.getIsbn()))));
        }
    }

    @Test(description = "Get books by status")
    @DataProvider(name = "bookStatuses")
    public Object[][] bookStatuses() {
        return new Object[][] {
                {BookStatus.AVAILABLE},
                {BookStatus.NOT_AVAILABLE},
                {BookStatus.RESERVED}
        };
    }

    @Test(dataProvider = "bookStatuses")
    public void getBooksByStatusTest(BookStatus status) throws IOException {

        Book statusBook = createBookWithStatus(status);

        Response<List<Book>> response = getBooksByStatus(status);
        assertOk(response);

        List<Book> books = response.body();
        assertThat(books, hasItem(hasProperty("status", is(status))));
    }

    @Test(description = "Get books with pagination")
    public void getBooksPaginationTest() throws IOException {

        createMultipleBooks(20);


        int[][] pageSizeCombinations = {{0, 5}, {1, 10}, {2, 5}};

        for (int[] combination : pageSizeCombinations) {
            Response<List<Book>> response = getBooksWithPagination(combination[0], combination[1]);
            assertOk(response);

            List<Book> books = response.body();
            assertThat(books, hasSize(lessThanOrEqualTo(combination[1])));
        }
    }

    @Test(description = "Get books with sorting")
    public void getBooksSortingTest() throws IOException {
        createMultipleBooks(5);

        String[] sortFields = {"title", "author", "publishYear"};
        String[] sortDirections = {"asc", "desc"};

        for (String field : sortFields) {
            for (String direction : sortDirections) {
                Response<List<Book>> response = getBooksSorted(field, direction);
                assertOk(response);

                List<Book> books = response.body();
                assertThat(books, isSortedBy(field, direction));
            }
        }
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
        bookIds.add(id);
        book.setId(id);
        return book;
    }

    private List<Book> createMultipleBooks(int count) throws IOException {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            books.add(createTestBook());
        }
        return books;
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
        bookIds.add(id);
        book.setId(id);
        return book;
    }

    private void assertBookEquals(Book actual, Book expected) {
        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getTitle(), is(expected.getTitle()));
        assertThat(actual.getAuthor(), is(expected.getAuthor()));
        assertThat(actual.getPublisher(), is(expected.getPublisher()));
        assertThat(actual.getEditionYear(), is(expected.getEditionYear()));
        assertThat(actual.getEdition(), is(expected.getEdition()));
        assertThat(actual.getDescription(), is(expected.getDescription()));
        assertThat(actual.getIsbn(), is(expected.getIsbn()));
        assertThat(actual.getStatus(), is(expected.getStatus()));
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}
package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetBookPositiveTest {
    private List<Integer> bookIds;
    private Book testBook;

    @BeforeMethod
    public void setup() throws IOException {
        bookIds = new ArrayList<>();
        testBook = createDefaultTestBook();
        Response<ResponseBody> response = createBook(testBook);
        bookIds.add(Integer.parseInt(response.body().string()));
    }

    @AfterMethod
    public void cleanup() {
        for (Integer id : bookIds) {
            deleteBook(id);
        }
    }

    @Test(description = "Get book by ID with all fields populated")
    public void getBookByIdFullDetailsTest() throws IOException {
        Book fullBook = Book.builder()
                .title("Complete Book")
                .author("Complete Author")
                .publisher("Test Publisher")
                .editionYear(2020)
                .edition("Special Edition")
                .description("Detailed description")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(fullBook);
        Integer bookId = Integer.parseInt(createResponse.body().string());
        bookIds.add(bookId);

        Response<Book> response = getBookById(bookId);
        assertOk(response);

        Book bookResponse = response.body();
        assertFullBookDetails(bookResponse, fullBook);
    }

    @Test(description = "Get all books and verify pagination")
    public void getAllBooksWithPaginationTest() throws IOException {
        // Create multiple books for pagination
        for (int i = 0; i < 5; i++) {
            Book book = Book.builder()
                    .title("Book " + i)
                    .author("Author " + i)
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE.toString())
                    .build();
            Response<ResponseBody> response = createBook(book);
            bookIds.add(Integer.parseInt(response.body().string()));
        }

        Response<List<Book>> response = getAllBooks();
        assertOk(response);

        List<Book> books = response.body();
        assertThat("Books list should not be null", books, notNullValue());
        assertThat("Books list should not be empty", books, not(empty()));
        assertThat("Books list should contain at least 5 books", books.size(), greaterThanOrEqualTo(5));
    }

    @Test(description = "Get books by status")
    public void getBooksByStatusTest() throws IOException {
        // Create books with different statuses
        for (BookStatus status : BookStatus.values()) {
            Book book = Book.builder()
                    .title("Status Book")
                    .author("Status Author")
                    .isbn(generateUniqueIsbn())
                    .status(status.toString())
                    .build();
            Response<ResponseBody> response = createBook(book);
            bookIds.add(Integer.parseInt(response.body().string()));
        }

        for (BookStatus status : BookStatus.values()) {
            Response<List<Book>> response = getBooksByStatus(status.toString());
            assertOk(response);

            List<Book> books = response.body();
            assertThat("Books list should not be null", books, notNullValue());
            assertThat("All books should have status " + status,
                    books.stream().allMatch(b -> b.getStatus().equals(status.toString())));
        }
    }

    @Test(description = "Get books by author")
    public void getBooksByAuthorTest() throws IOException {
        String specificAuthor = "Special Author";

        // Create multiple books by same author
        for (int i = 0; i < 3; i++) {
            Book book = Book.builder()
                    .title("Book " + i)
                    .author(specificAuthor)
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE.toString())
                    .build();
            Response<ResponseBody> response = createBook(book);
            bookIds.add(Integer.parseInt(response.body().string()));
        }

        Response<List<Book>> response = getBooksByAuthor(specificAuthor);
        assertOk(response);

        List<Book> books = response.body();
        assertThat("Books list should not be null", books, notNullValue());
        assertThat("Should find at least 3 books", books.size(), greaterThanOrEqualTo(3));
        assertThat("All books should be by the specific author",
                books.stream().allMatch(b -> b.getAuthor().equals(specificAuthor)));
    }

    @Test(description = "Get books by publication year")
    public void getBooksByYearTest() throws IOException {
        int specificYear = 2020;

        // Create multiple books from same year
        for (int i = 0; i < 3; i++) {
            Book book = Book.builder()
                    .title("Year Book " + i)
                    .author("Year Author")
                    .editionYear(specificYear)
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE.toString())
                    .build();
            Response<ResponseBody> response = createBook(book);
            bookIds.add(Integer.parseInt(response.body().string()));
        }

        Response<List<Book>> response = getBooksByYear(specificYear);
        assertOk(response);

        List<Book> books = response.body();
        assertThat("Books list should not be null", books, notNullValue());
        assertThat("Should find at least 3 books", books.size(), greaterThanOrEqualTo(3));
        assertThat("All books should be from specific year",
                books.stream().allMatch(b -> b.getEditionYear() == specificYear));
    }

    @Test(description = "Search books by title keyword")
    public void searchBooksByTitleTest() throws IOException {
        String keyword = "unique";

        // Create books with keyword in title
        for (int i = 0; i < 3; i++) {
            Book book = Book.builder()
                    .title("A " + keyword + " Book " + i)
                    .author("Search Author")
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE.toString())
                    .build();
            Response<ResponseBody> response = createBook(book);
            bookIds.add(Integer.parseInt(response.body().string()));
        }

        Response<List<Book>> response = searchBooksByTitle(keyword);
        assertOk(response);

        List<Book> books = response.body();
        assertThat("Books list should not be null", books, notNullValue());
        assertThat("Should find at least 3 books", books.size(), greaterThanOrEqualTo(3));
        assertThat("All books should contain keyword in title",
                books.stream().allMatch(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase())));
    }

    @Test(description = "Get book details including loan history")
    public void getBookWithLoanHistoryTest() throws IOException {
        Book book = Book.builder()
                .title("Loan History Book")
                .author("History Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(book);
        Integer bookId = Integer.parseInt(createResponse.body().string());
        bookIds.add(bookId);

        Response<Book> response = getBookWithLoanHistory(bookId);
        assertOk(response);

        Book bookResponse = response.body();
        assertThat("Book should not be null", bookResponse, notNullValue());
        assertThat("Loan history should be present", bookResponse.getLoanHistory(), notNullValue());
    }

    @Test(description = "Get recently added books")
    public void getRecentlyAddedBooksTest() throws IOException {
        // Create multiple recent books
        for (int i = 0; i < 3; i++) {
            Book book = Book.builder()
                    .title("Recent Book " + i)
                    .author("Recent Author")
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE.toString())
                    .build();
            Response<ResponseBody> response = createBook(book);
            bookIds.add(Integer.parseInt(response.body().string()));
        }

        Response<List<Book>> response = getRecentlyAddedBooks();
        assertOk(response);

        List<Book> books = response.body();
        assertThat("Books list should not be null", books, notNullValue());
        assertThat("Should find recent books", books.size(), greaterThanOrEqualTo(3));
    }

    // Helper Methods
    private Book createDefaultTestBook() {
        return Book.builder()
                .title("Default Test Book")
                .author("Default Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }

    private void assertFullBookDetails(Book bookResponse, Book expectedBook) {
        assertThat("Book should not be null", bookResponse, notNullValue());
        assertThat("ID should not be null", bookResponse.getId(), notNullValue());
        assertThat("Title should match", bookResponse.getTitle(), is(expectedBook.getTitle()));
        assertThat("Author should match", bookResponse.getAuthor(), is(expectedBook.getAuthor()));
        assertThat("Publisher should match", bookResponse.getPublisher(), is(expectedBook.getPublisher()));
        assertThat("Edition year should match", bookResponse.getEditionYear(), is(expectedBook.getEditionYear()));
        assertThat("Edition should match", bookResponse.getEdition(), is(expectedBook.getEdition()));
        assertThat("Description should match", bookResponse.getDescription(), is(expectedBook.getDescription()));
        assertThat("ISBN should match", bookResponse.getIsbn(), is(expectedBook.getIsbn()));
        assertThat("Status should match", bookResponse.getStatus(), is(expectedBook.getStatus()));
    }
}
}
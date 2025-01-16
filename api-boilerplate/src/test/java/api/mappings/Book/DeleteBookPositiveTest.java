package api.mappings.Book;

import api.mappings.generic.Book;
<<<<<<< HEAD
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
=======
import api.mappings.generic.Member;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Books.*;
import static api.retrofit.Members.deleteMember;
import static api.retrofit.Members.getMemberByID;
import static api.validators.ResponseValidator.assertNoContent;
import static api.validators.ResponseValidator.assertNotFound;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeleteBookPositiveTest {
    Book bookRequest;
    Integer bookId1;

    @SneakyThrows
    @BeforeMethod
    public void setUpBook1(){
        bookRequest = Book.builder()
                .title("Wicked part 1")
                .author("Gregory Maguire")
                .publisher("william morrow company")
                .editionYear(2000)
                .edition("Third Edition")
                .description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz")
                .isbn("0060987103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assertThat(response.code(), is(201));
        String responseString= /*Integer.parseInt(*/response.body().string();
        bookId1 = Integer.parseInt(responseString);
        System.out.println(getBookById(bookId1));
    }
    @Test(description = "Delete Book with Success")
    public void deleteBookSuccess() {
        Response<ResponseBody> response = deleteBook(this.bookId1);
        assertNoContent(response);
        assert (response.code() == 204);
        Response<Member> responseGet = getMemberByID(bookId1);
        assertNotFound(responseGet);
    }

    /*@Test(description = "Delete Book with Success")
    public void deleteBookSuccess2() {
        Response<ResponseBody> response = deleteBook(this.bookId1, true);
        assertNoContent(response);
        assert (response.code() == 204);
        Response<Member> responseGet = getMemberByID(bookId1);
        assertNotFound(responseGet);
    }

    @Test(description = "Delete Book with Success")
    public void deleteBookSuccess3() {
        Response<ResponseBody> response = deleteBook(this.bookId1, false);
        assertNoContent(response);
        assert (response.code() == 204);
        Response<Member> responseGet = getMemberByID(bookId1);
        assertNotFound(responseGet);
    } */

}
>>>>>>> a6937f70dacf73c82f07b1adafa0bbc624692761

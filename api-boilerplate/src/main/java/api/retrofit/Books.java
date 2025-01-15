package api.retrofit;

import api.calls.BookCalls;
import api.mappings.generic.Book;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;
import java.util.List;

public class Books {
    private static final BookCalls bookCalls = new RetrofitBuilder().getRetrofit().create(BookCalls.class);

    @SneakyThrows
    public static Response<List<Book>> getAllBooks() {
        return bookCalls.getAllBooks().execute();
    }

    @SneakyThrows
    public static Response<Book> getBookById(Integer id) {
        return bookCalls.getBookById(id).execute();
    }

    @SneakyThrows
    public static Response<ResponseBody> createBook(Book book) {
        return bookCalls.createBook(book).execute();
    }

    @SneakyThrows
    public static Response<ResponseBody> deleteBook(Integer id) {
        return bookCalls.deleteBook(id).execute();
    }

    @SneakyThrows
    public static Response<Book> updateBook(Integer id, Book book) {
        return bookCalls.updateBook(id, book).execute();
    }
}
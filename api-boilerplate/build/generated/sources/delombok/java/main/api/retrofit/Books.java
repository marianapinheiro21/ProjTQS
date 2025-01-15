package api.retrofit;

import api.calls.BookCalls;
import api.mappings.generic.Book;
import okhttp3.ResponseBody;
import retrofit2.Response;
import java.util.List;

public class Books {
    private static final BookCalls bookCalls = new RetrofitBuilder().getRetrofit().create(BookCalls.class);

    public static Response<List<Book>> getAllBooks() {
        try {
            return bookCalls.getAllBooks().execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<Book> getBookById(Integer id) {
        try {
            return bookCalls.getBookById(id).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<ResponseBody> createBook(Book book) {
        try {
            return bookCalls.createBook(book).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<ResponseBody> deleteBook(Integer id) {
        try {
            return bookCalls.deleteBook(id).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<Book> updateBook(Integer id, Book book) {
        try {
            return bookCalls.updateBook(id, book).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }
}

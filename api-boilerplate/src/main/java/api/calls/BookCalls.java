package api.calls;

import api.mappings.generic.Book;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BookCalls {
    String BOOK = "book";
    String BOOK_ID = "book/{id}";

    @GET(BOOK)
    Call<List<Book>> getAllBooks();

    @GET(BOOK_ID)
    Call<Book> getBookById(@Path("id") Integer id);

    @POST(BOOK)
    Call<ResponseBody> createBook(@Body Book book);

    @DELETE(BOOK_ID)
    Call<ResponseBody> deleteBook(@Path("id") Integer id);

    @PUT(BOOK_ID)
    Call<Book> updateBook(@Path("id") Integer id, @Body Book book);
}
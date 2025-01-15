package api.calls;

import api.mappings.generic.Book;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface BookCalls {
    String BOOK = "book";
    String BOOK_ID = "book/{id}";

    @GET(BOOK_ID)
    Call<Book> getBookById(@Path("id") Integer id);

    @PUT(BOOK_ID)
    Call<Integer> updateBook(@Path("id") Integer id, @Body Book book);

    @DELETE(BOOK_ID)
    Call<ResponseBody> deleteBook(
            @Path("id") Integer id,
            @Query("forceRemove") Boolean forceRemove
    );

    @GET(BOOK)
    Call<List<Book>> getAllBooks();

    @POST(BOOK)
    Call<Integer> createBook(@Body Book book);
}
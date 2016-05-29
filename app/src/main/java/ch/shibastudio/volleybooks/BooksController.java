package ch.shibastudio.volleybooks;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import ch.shibastudio.volleybooks.books.BookApiFactory;
import ch.shibastudio.volleybooks.books.IBookApi;
import ch.shibastudio.volleybooks.books.beans.Book;

/**
 * Created by shibakaneki on 28.05.16.
 */
public class BooksController implements IBookController, Response.Listener, ErrorListener{

    private Context context;
    private IBookApi bookApi;
    private List<IBookControllerListener> listeners = new ArrayList<>();

    public BooksController(Context context){
        this.context = context;
        this.bookApi = BookApiFactory.createApi(context, 1, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        for(IBookControllerListener listener : this.listeners){
            listener.onError(error.getMessage());
        }
    }

    @Override
    public void onResponse(Object response) {
        List<Book> books = this.bookApi.deserializeBooks(response.toString());
        for(IBookControllerListener listener : this.listeners){
            listener.onBooksReceived(books);
        }
    }

    @Override
    public void searchBooks(String keywords) {
        this.bookApi.getBooksFromKeywords(keywords);
    }

    @Override
    public void getBookDetails(String bookId) {
        this.bookApi.getBook(bookId);
    }

    @Override
    public void stop() {
        this.bookApi.stop();
    }

    @Override
    public void addListener(IBookControllerListener listener) {
        if(!this.listeners.contains(listener)){
            this.listeners.add(listener);
        }
    }

    @Override
    public void removeListener(IBookControllerListener listener) {
        if(this.listeners.contains(listener)){
            this.listeners.remove(listener);
        }
    }
}

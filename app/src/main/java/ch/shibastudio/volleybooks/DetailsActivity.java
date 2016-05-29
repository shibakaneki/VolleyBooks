package ch.shibastudio.volleybooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import ch.shibastudio.volleybooks.books.beans.Book;
import ch.shibastudio.volleybooks.network.VolleyRequestQueue;

/**
 * Created by shibakaneki on 28.05.16.
 */
public class DetailsActivity extends AppCompatActivity implements IBookControllerListener{
    public final static String EXTRA_BOOK_ID = "extra:book:id";

    private TextView title;
    private TextView authors;
    private TextView description;
    private NetworkImageView thumbnail;

    private IBookController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        this.title = (TextView)findViewById(R.id.title);
        this.authors = (TextView)findViewById(R.id.authors);
        this.description = (TextView)findViewById(R.id.description);
        this.thumbnail = (NetworkImageView)findViewById(R.id.thumb);

        this.controller = new BooksController(getApplicationContext());
        this.controller.addListener(this);

        String bookId = getIntent().getExtras().getString(EXTRA_BOOK_ID, "");
        if(null != bookId && !bookId.isEmpty()){
            this.controller.getBookDetails(bookId);
        }

        getSupportActionBar().setTitle(R.string.title_details);
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.controller.removeListener(this);
        this.controller.stop();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBooksReceived(List<Book> books) {
        if(!books.isEmpty()){
            // In this activty, we query only one book.
            Book book = books.get(0);

            this.title.setText(book.getTitle());

            String auth = "";
            for(int i=0; i<book.getAuthors().size(); i++){
                String itAuthor = book.getAuthors().get(i);
                if(0 < i){
                    auth += "\n";
                }
                auth += itAuthor;
            }
            this.authors.setText(auth);

            this.description.setText(book.getDescription());
            this.thumbnail.setImageUrl(book.getThumbnail(), VolleyRequestQueue.getInstance(this).getImageLoader());
        }
    }
}

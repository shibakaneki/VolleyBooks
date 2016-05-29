package ch.shibastudio.volleybooks;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ch.shibastudio.volleybooks.books.beans.Book;

public class MainActivity extends AppCompatActivity implements IBookControllerListener {
	private TextView messageView;
	private Button searchButton;
	private EditText searchText;
	private RelativeLayout waitContainer;
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager recyclerViewLayoutManager;
	private BooksListAdapter adapter;

	private IBookController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.messageView = (TextView)findViewById(R.id.message);
		this.searchText = (EditText)findViewById(R.id.searchText);
		this.searchButton = (Button)findViewById(R.id.searchButton);
		this.waitContainer = (RelativeLayout)findViewById(R.id.waitContainer);
		this.recyclerView = (RecyclerView)findViewById(R.id.list);

		this.recyclerViewLayoutManager = new LinearLayoutManager(this);
		this.recyclerView.setLayoutManager(this.recyclerViewLayoutManager);
		this.adapter = new BooksListAdapter(this);
		this.recyclerView.setAdapter(this.adapter);

		this.controller = new BooksController(getApplicationContext());
		this.controller.addListener(this);

		this.searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				messageView.setVisibility(View.GONE);
				messageView.setText("");
				String keywords = searchText.getText().toString();
				if(!keywords.isEmpty()){
					setWaitVisibility(true);
					controller.searchBooks(keywords);
				}
			}
		});
	}

	@Override
	protected void onStop(){
		super.onStop();
		this.controller.removeListener(this);
		this.controller.stop();
	}

	@Override
	public void onError(String error) {
		setWaitVisibility(false);
		this.messageView.setVisibility(View.VISIBLE);
		this.messageView.setText(error);
		this.messageView.setTextColor(Color.RED);
	}

	@Override
	public void onBooksReceived(List<Book> books) {
		setWaitVisibility(false);
		this.messageView.setTextColor(Color.BLACK);
		this.adapter.setCurrentBooks(books);
	}

	/**
	 * Sets the visiblity of the wait spinner.
	 * @param isVisible as an indication whether the wait spinner will be visible.
	 */
	private void setWaitVisibility(boolean isVisible){
		this.waitContainer.setVisibility(isVisible ? View.VISIBLE : View.GONE);
	}
}

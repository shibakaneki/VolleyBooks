package ch.shibastudio.volleybooks.books;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ch.shibastudio.volleybooks.books.beans.Book;
import ch.shibastudio.volleybooks.network.VolleyRequestQueue;

/**
 * Created by didier on 5/27/16.
 */
public class BookApiV1 implements IBookApi{
	private final static int API_VERSION = 1;
	private final static String REQ_TAG = "req:tag:book:api:v1";
	private final static String QUERY_VOLUME_URI = "https://www.googleapis.com/books/v1/volumes";
	private final static String TAG_ITEMS = "items";
	private final static String TAG_TITLE = "title";
	private final static String TAG_ID = "id";
	private final static String TAG_VOLUME_INFO = "volumeInfo";
	private final static String TAG_IMAGELINKS = "imageLinks";
	private final static String TAG_SMALL_THUMB = "smallThumbnail";
	private final static String TAG_PUBLISHER = "publisher";
	private final static String TAG_AUTHORS = "authors";
	private final static String TAG_DESCRIPTION = "description";

	private Context context;
	private RequestQueue requestQueue;
	private Response.Listener responseListener;
	private Response.ErrorListener errorListener;

	public BookApiV1(Context context, Response.Listener responseListener, Response.ErrorListener errorListener){
		if(null == context){
			throw new NullPointerException("context");
		}

		if(null == responseListener){
			throw new NullPointerException("responseListener");
		}

		if(null == errorListener){
			throw new NullPointerException("errorListener");
		}

		this.context = context;
		this.requestQueue = VolleyRequestQueue.getInstance(context).getRequestQueue();
		this.responseListener = responseListener;
		this.errorListener = errorListener;
	}

	@Override
	public int getVersion() {
		return API_VERSION;
	}

	@Override
	public void getBooksFromKeywords(String keywords) {
		if(null == keywords){
			throw new NullPointerException("keywords");
		}

		// We restrict the fields to id, title and thumbnail in order to reduce the size of the
		// response. It is not necessary to have ALL the details of the books at this point, as we
		// only want to list the books. Full details will be queried when a specific book is
		// selected.
		String url = QUERY_VOLUME_URI + "?q="
				+keywords.replace(" ", "%20")
				+"&fields=items(id,volumeInfo(title,imageLinks(smallThumbnail)))";

		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), this.responseListener, this.errorListener);
		request.setTag(REQ_TAG);
		this.requestQueue.add(request);
	}

	@Override
	public void getBook(String bookId) {
		if(null == bookId){
			throw new NullPointerException("bookId");
		}

		String url = QUERY_VOLUME_URI + "/" +bookId;
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), this.responseListener, this.errorListener);
		request.setTag(REQ_TAG);
		this.requestQueue.add(request);
	}

	@Override
	public void stop() {
		this.requestQueue.cancelAll(REQ_TAG);
	}

	@Override
	public List<Book> deserializeBooks(String json) {
		List<Book> books = new ArrayList<>();

		if(null != json && !json.isEmpty()){
			try {
				JSONObject jsonObject = new JSONObject(json);
				if(jsonObject.has(TAG_ITEMS)){
					JSONArray items = jsonObject.getJSONArray(TAG_ITEMS);
					for(int i=0; i<items.length(); i++){
						books.add(this.generateBook(items.getJSONObject(i)));
					}
				}else{
					books.add(this.generateBook(jsonObject));
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return books;
	}

	/**
	 * Gets a book from the given JSON object.
	 * @param book as the given JSON object.
	 * @return a Book.
     */
	private Book generateBook(JSONObject book) throws JSONException{
		String id = book.getString(TAG_ID);
		String thumb = "";
		List<String> authors = new ArrayList<>();
		String publisher = "";
		String description = "";

		JSONObject volumeInfo = book.getJSONObject(TAG_VOLUME_INFO);
		String title = volumeInfo.getString(TAG_TITLE);

		if(volumeInfo.has(TAG_PUBLISHER))
			publisher = volumeInfo.getString(TAG_PUBLISHER);

		if(volumeInfo.has(TAG_DESCRIPTION))
			description = volumeInfo.getString(TAG_DESCRIPTION);

		if(volumeInfo.has(TAG_AUTHORS)){
			JSONArray authorArray = volumeInfo.getJSONArray(TAG_AUTHORS);
			for(int i=0; i<authorArray.length(); i++){
				authors.add(authorArray.getString(i));
			}
		}

		if(volumeInfo.has(TAG_IMAGELINKS)){
			JSONObject imageLinks = volumeInfo.getJSONObject(TAG_IMAGELINKS);
			thumb = imageLinks.getString(TAG_SMALL_THUMB);
		}

		return new Book(id, title, thumb, authors, publisher, description);
	}
}

package ch.shibastudio.volleybooks.books;

import android.content.Context;

import com.android.volley.Response;

/**
 * Created by didier on 5/27/16.
 */
public class BookApiFactory {
	/**
	 * Creates a Google Books API of the requested version.
	 * A Factory is used here to support the next versions of the API.
	 * @param version as the requested API version.
	 * @return an instance of the requested API version, or null if not available.
	 */
	public static IBookApi createApi(Context context, int version, Response.Listener responseListener, Response.ErrorListener errorListener){
		switch(version){
			case 1:
				return new BookApiV1(context, responseListener, errorListener);
			default:
				return null;
		}
	}
}

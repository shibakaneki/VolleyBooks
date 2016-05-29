package ch.shibastudio.volleybooks.books;

import java.util.List;

import ch.shibastudio.volleybooks.books.beans.Book;

/**
 * Created by didier on 5/27/16.
 */
public interface IBookApi {
	/**
	 * Gets the version of the Google Books API.
	 * @return the version.
	 */
	int getVersion();

	/**
	 * Gets the books containing the given keywords.
	 * @param keywords as a list of keywords used to select the books.
	 */
	void getBooksFromKeywords(String keywords);

	/**
	 * Gets the book with the given id.
	 * @param bookId as given the book id.
     */
	void getBook(String bookId);

	/**
	 * Stops any pending request.
	 */
	void stop();

	/**
	 * Deserialize the JSON string to return a list of books.
	 * @param json as the given JSON string.
	 * @return a list of books.
     */
	List<Book> deserializeBooks(String json);
}

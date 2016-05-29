package ch.shibastudio.volleybooks.books;

import java.util.List;

import ch.shibastudio.volleybooks.books.beans.Book;

/**
 * Created by didier on 5/27/16.
 */
public interface IBookApiListener {
	/**
	 * Notifies the
	 * @param books
	 */
	void bookReceived(List<Book> books);
}

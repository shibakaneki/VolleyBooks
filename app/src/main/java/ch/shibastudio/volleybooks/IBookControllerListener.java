package ch.shibastudio.volleybooks;

import java.util.List;

import ch.shibastudio.volleybooks.books.beans.Book;

/**
 * Created by shibakaneki on 28.05.16.
 */
public interface IBookControllerListener {
    /**
     * Notifies an error.
     * @param error as the error details.
     */
    void onError(String error);

    /**
     * Notifies the arrival of some books.
     * @param books as the arrived books.
     */
    void onBooksReceived(List<Book> books);
}

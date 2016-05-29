package ch.shibastudio.volleybooks;

/**
 * Created by shibakaneki on 28.05.16.
 */
public interface IBookController {
    /**
     * Search for the books matching the given keywords.
     * @param keywords as the given keywords.
     */
    void searchBooks(String keywords);

    /**
     * Gets the details of the given book.
     * @param bookId as the book ID.
     */
    void getBookDetails(String bookId);

    /**
     * Stops the controller.
     */
    void stop();

    /**
     * Adds a listener.
     * @param listener as the given listener.
     */
    void addListener(IBookControllerListener listener);

    /**
     * Removes a listener.
     * @param listener as the given listener.
     */
    void removeListener(IBookControllerListener listener);
}

package ch.shibastudio.volleybooks.books.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by didier on 5/27/16.
 */
public class Book {
	private String id;
	private String title;
	private String thumbnail;
	private List<String> authors = new ArrayList<>();
	private String publisher;
	private String description;

	public Book(){ }

	public Book(String id, String title, String thumbnail, List<String> authors, String publisher, String description){
		this.id = id;
		this.title = title;
		this.thumbnail = thumbnail;
		this.authors.addAll(authors);
		this.publisher = publisher;
		this.description = description;
	}

	/**
	 * Gets the id.
	 * @return the id.
     */
	public String getId(){ return this.id; }

	/**
	 * Gets the title.
	 * @return the title.
     */
	public String getTitle(){ return this.title; }

	/**
	 * Gets the thumbnail.
	 * @return the thumbnail.
     */
	public String getThumbnail(){ return this.thumbnail; }

	/**
	 * Gets the authors.
	 * @return the authors.
     */
	public List<String> getAuthors(){ return this.authors; }

	/**
	 * Gets the publisher.
	 * @return the publisher.
     */
	public String getPublisher(){ return this.publisher; }

	/**
	 * Gets the description.
	 * @return the description.
     */
	public String getDescription(){ return this.description; }
}

package android.ih.news.model;

import java.util.List;
import java.util.UUID;


/**
 * A single article. 
 * 
 * @author Peter
 *
 */
public abstract class Article implements Item{

	private UUID id;
	private String title;
	private String fullText;
	private String imageURL;
	private List<Comment> comments;
	// TODO: more fields will follow

	public Article(UUID id, String title, String fullText, String imageURL) {
		this.id = id;
		this.title = title;
		this.fullText = fullText;
		this.imageURL = imageURL;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFullText() {
		return fullText;
	}
	public void setFullText(String fullText) {
		this.fullText = fullText;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}

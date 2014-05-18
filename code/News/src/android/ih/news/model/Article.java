package android.ih.news.model;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A single article. 
 * 
 * @author Peter
 *
 */
public abstract class Article implements Item{

	private UUID id;		// nid in the json field
	private Date date;
	private String title;	// under content.title
	private String summery; // under content.intro
	private List<Comment> comments;

	// TODO: more fields will follow
	private List<AnnotatedImage> images;
	private URL mobileUrl;		// this is how we display the article, this page contains embedded images as well
	private Author author;
	//categories: [1] - 0:  "Crime" - don't think we need this
	

	public Article(UUID id, String title, String summery, List<AnnotatedImage> images) {
		this.id = id;
		this.title = title;
		this.summery = summery;
		this.images = images;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummery() {
		return summery;
	}
	public void setSummery(String summery) {
		this.summery = summery;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<AnnotatedImage> getImages() {
		return images;
	}

	public void setImages(List<AnnotatedImage> images) {
		this.images = images;
	}

	public URL getMobileUrl() {
		return mobileUrl;
	}

	public void setMobileUrl(URL mobileUrl) {
		this.mobileUrl = mobileUrl;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
}

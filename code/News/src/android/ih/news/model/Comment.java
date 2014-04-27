package android.ih.news.model;

/**
 * A simple user comment
 * 
 * @author Peter
 */
public class Comment {

	private String title;
	private String fullText;

	public Comment(String title, String fullText) {
		this.title = title;
		this.fullText = fullText;
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
}

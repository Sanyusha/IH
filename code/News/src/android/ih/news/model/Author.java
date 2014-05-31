package android.ih.news.model;

/**
 * The article's author
 * 
 * @author peter
 */
public class Author {

	private String name;
	private AnnotatedImage image; // this is optional
	
	public Author(String name, AnnotatedImage image) {
		super();
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AnnotatedImage getImage() {
		return image;
	}
	public void setImage(AnnotatedImage image) {
		this.image = image;
	}
}

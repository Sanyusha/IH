package android.ih.news.model;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.ih.news.api.JSONUtil;
import android.ih.news.api.JSONUtil.JSONParsableObject;
import android.ih.news.model.AnnotatedImage.ImageSize;
import android.util.JsonReader;

/**
 * A single article. 
 * 
 * @author Peter
 *
 */
public abstract class Article implements Item, JSONParsableObject {

	private UUID id;		// nid in the json field
	private Date date;
	private String title;	// under content.title
	private String summary; // under content.intro
	private List<Comment> comments;

	private List<AnnotatedImage> images;
	private URL mobileUrl;		// this is how we display the article, this page contains embedded images as well
	private Author author = new Author();

	private String content;
	//categories: [1] - 0:  "Crime" - don't think we need this
	

	public Article(UUID id, String title, String summary, List<AnnotatedImage> images) {
		this.id = id;
		this.title = title;
		this.summary = summary;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summery) {
		this.summary = summery;
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

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public void parse(JsonReader reader) throws IOException, InstantiationException, IllegalAccessException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("nid")) {
				this.setId(new UUID(0, JSONUtil.safeIntRead(reader)));
			} else if (name.equals("mobile_url")) {
				this.setMobileUrl(new URL(JSONUtil.safeStringRead(reader, true)));
			} else if (name.equals("content")) {
				readArticleContent(reader);
			} else if (name.equals("images")) {
				readArticleImages(reader);
			} else if (name.equals("author")) {
				readArticleAuthor(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}

	private void readArticleAuthor(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("name")) {
				this.getAuthor().setName(JSONUtil.safeStringRead(reader, true));
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}

	private void readArticleImages(JsonReader reader) throws IOException, InstantiationException, IllegalAccessException {
		this.setImages(new ArrayList<AnnotatedImage>());
		JSONUtil.readObjectArray(reader, this.getImages(), AnnotatedImage.class);
		
		for (AnnotatedImage image : this.getImages()) {
			if (this instanceof HeadArticle) {
				image.setSize(ImageSize.MAIN_ARTICLE);
			}
			else if (this instanceof SubArticle) {
				image.setSize(ImageSize.SUB_ARTICLE);
			}
		}
	}

	private void readArticleContent(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("title")) {
				this.setTitle(JSONUtil.safeStringRead(reader, true));
			} else if (name.equals("intro")) {
				this.setSummary(JSONUtil.safeStringRead(reader, true));
			} else if (name.equals("raw")) {
				readArticleRaw(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}
	
	private void readArticleRaw(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("body")) {
				this.setContent(JSONUtil.safeStringRead(reader, true));
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}
	
	@Override
	public String toString() {
		return this.getTitle();
	}
}

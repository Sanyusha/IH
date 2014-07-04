package android.ih.piemenu;

import java.net.URL;
import java.util.UUID;

import android.content.res.Resources;
import android.ih.news.model.AnnotatedImage;

/**
 *  Use only for root, see if possible to remove
 */
// TODO: remove this class	
public class TestPieMenuItem implements PieMenuItem {
	private AnnotatedImage img;
	private String title;
	URL mURL;
	private Resources resources;
	private UUID id;
	
	public TestPieMenuItem() {
		this.img = null;
		this.title = "No title";
		this.mURL = null;
		this.id = null;
	}
	
	public void setImage(AnnotatedImage img) {
		this.img = img;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setURL(URL url) {
		this.mURL = url;
	}
	
	public String getTitle() { 
		return title;
	}	
	
	public AnnotatedImage getImage() {
		return img;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public URL getUrl() {
		return mURL;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID getId() {
		return this.id;
	}
}

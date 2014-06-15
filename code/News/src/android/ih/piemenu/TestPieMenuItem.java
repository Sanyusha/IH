package android.ih.piemenu;

import android.content.res.Resources;
import android.ih.news.model.AnnotatedImage;

/**
 *  Use only for root, see if possible to remove
 */
// TODO: remove this class	
public class TestPieMenuItem implements PieMenuItem {
	private AnnotatedImage img;
	private String title;
	private Resources resources;
	
	public TestPieMenuItem() {
		
	}
	
	public void setImage(AnnotatedImage img) {
		this.img = img;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
}
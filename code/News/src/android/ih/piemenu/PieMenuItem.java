package android.ih.piemenu;

import java.net.URL;
import java.util.UUID;

import android.ih.news.model.AnnotatedImage;

public interface PieMenuItem { // implemented by article & category
	
	AnnotatedImage getImage();
	
	String getTitle();
	
	URL getUrl();
//	boolean isClickable();
	
	UUID getId();
	
	String getCode();
}

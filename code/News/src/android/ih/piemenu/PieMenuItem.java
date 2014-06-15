package android.ih.piemenu;

import android.ih.news.model.AnnotatedImage;

public interface PieMenuItem { // implemented by article & category
	
	AnnotatedImage getImage();
	
	String getTitle();
	
//	boolean isClickable();
}

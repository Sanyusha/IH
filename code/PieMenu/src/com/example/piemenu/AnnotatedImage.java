package com.example.piemenu;

//import java.awt.Image; // this may not compile in an android project, we can replace it with the equivalent
import java.net.URL;

import android.graphics.Bitmap;

/**
 * All the data needed to show an Image:
 * <li>The image label - shown until </li>
 * <li>The image URL - from where to fetch the image</li>
 * <li>The actual Image(jpg/bitmap) - to prevent it from being loaded several times</li>
 * To display the image correctly:
 * <li>If the image is not null show it</li>
 * <li>Else, show the label, while fetching the image from the URL, update the image field when fetched</li>
 *
 * @author PeterK
 */
public class AnnotatedImage {
	private String label;
	private URL url;
	private Bitmap image;

	public AnnotatedImage(String label, URL url, Bitmap image) {
		this.label = label;
		this.url = url;
		this.image = image;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
}

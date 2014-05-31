package android.ih.news.model;

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
	private String label;  // "caption" in the JSON fields
	private Bitmap image;
	private String url;	// will need to replace [DEFAULT] with one of this list http://api.app.israelhayom.co.il/images?key=nas987nh34
	/*
	 * {
sizes: [68]
0:  "100x70"
1:  "296x122"
2:  "324x192"
3:  "430x295"
4:  "433x295"
...
67:  "600x430"
-
} 
	 */
	
	public AnnotatedImage(String label, String url, Bitmap image) {
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
}

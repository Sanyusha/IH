package android.ih.news.model;

import java.io.IOException;

import android.graphics.Bitmap;
import android.ih.news.api.JSONUtil;
import android.ih.news.api.JSONUtil.JSONParsableObject;
import android.util.JsonReader;

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
public class AnnotatedImage implements JSONParsableObject{
	private static final String DEFAULT_PLACEHOLDER = "[DEFAULT]";
	private static final String HTTP_WWW_ISRAELHAYOM_CO_IL = "http://www.israelhayom.co.il/";
	private String label = null;  // "caption" in the JSON fields
	private Bitmap image = null;
	private String url = null;	// will need to replace [DEFAULT] with one of this list http://api.app.israelhayom.co.il/images?key=nas987nh34
	private ImageSize size = ImageSize.SUB_ARTICLE;

	public enum ImageSize{
		MAIN_ARTICLE ("241x148"), SUB_ARTICLE ("112x96"), PIE ("56x56");
	
		private String actualSize = null;
		
		ImageSize(String actualSize) {
			this.actualSize = actualSize;
		}
		
		public String getActualSize() {
			return actualSize;
		}
	}; 
	
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
	
	public AnnotatedImage() {
	}
	
	public AnnotatedImage(String label, String url, Bitmap image, ImageSize size) {
		this.label = label;
		this.url = url;
		this.image = image;
		this.size = size;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Use getProperURL.
	 */
	private String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public ImageSize getSize() {
		return size;
	}

	public void setSize(ImageSize size) {
		this.size = size;
	}

	public String getProperURL() {
		return HTTP_WWW_ISRAELHAYOM_CO_IL + getUrl().replace(DEFAULT_PLACEHOLDER, size.getActualSize());
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	public void parse(JsonReader reader) throws IOException {

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("caption")) {
				this.setLabel(JSONUtil.safeStringRead(reader, true));
			} else if (name.equals("path")) {
				this.setUrl(JSONUtil.safeStringRead(reader, false));
			} else if (name.equals("credit")) { // ignore value, since 'null' would cause us to crash
					JSONUtil.safeStringRead(reader, false);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}
}

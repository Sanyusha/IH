package android.ih.news.model;

import java.io.IOException;

import android.ih.news.api.JSONUtil;
import android.ih.news.api.JSONUtil.JSONParsableObject;
import android.util.JsonReader;

/**
 * A single category like 'Sports'.
 * 
 * @author Peter
 */
public class Category implements JSONParsableObject{

	public static String HEBREW_LANGUAGE = "he";
	public static String ENGLISH_LANGUAGE = "en";
	
	private String name = null;
	private int numberOfItems = 0; // this is the number of articles, will use it later for pagination
	private String code = null;	
	private String lang = null; // "he"/"en" - we would filter and keep Hebrew categories only

	public Category(String name, int numberOfItems, String code, String lang) {
		this.name = name;
		this.numberOfItems = numberOfItems;
		this.code = code;
		this.lang = lang;
	}

	public Category() {
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public void parse(JsonReader reader) throws IOException {

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("count")) {
				this.setNumberOfItems(JSONUtil.safeIntRead(reader));
			} else if (name.equals("code")) {
				this.setCode(JSONUtil.safeStringRead(reader, true));
			} else if (name.equals("user")) {
				this.setLang(JSONUtil.safeStringRead(reader, true));
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}
}

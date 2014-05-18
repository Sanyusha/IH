package android.ih.news.model;

/**
 * A single category like 'Sports'.
 * 
 * @author Peter
 */
public class Category {

	public static String HEBREW_LANGUAGE = "he";
	public static String ENGLISH_LANGUAGE = "en";
	
	private String name;
	private int numberOfItems; // this is the number of articles, will use it later for pagination
	private String code;	
	private String lang; // "he"/"en" - we would filter and keep Hebrew categories only

	public Category(String name, int numberOfItems, String code, String lang) {
		this.name = name;
		this.numberOfItems = numberOfItems;
		this.code = code;
		this.lang = lang;
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
}

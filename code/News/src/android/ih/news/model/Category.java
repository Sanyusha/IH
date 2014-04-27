package android.ih.news.model;

/**
 * A single category like 'Sports'.
 * 
 * @author Peter
 */
public class Category {

	private String name;
	private int numberOfItems; // this is the number of articles, will use it later for pagination
	private int code;	// don't think we'll need it, but it is part of the fields

	public Category(String name, int numberOfItems, int code) {
		this.name = name;
		this.numberOfItems = numberOfItems;
		this.code = code;
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
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}

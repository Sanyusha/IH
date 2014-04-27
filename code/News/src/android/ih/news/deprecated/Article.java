/*package android.ih.news.deprecated;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.ih.news.RowType;
import android.ih.news.model.Item;
import android.view.LayoutInflater;
import android.view.View;

abstract class Article implements Item{

	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SUMMARY = "summary";
	private static final String JSON_CONTENT = "content";
	private static final String JSON_IMGLINK = "imgLink";
	private static final String JSON_IMG = "img";

	UUID mId;
	String mTitle;
	String mSummary;
	String mContent;
	String mImgLink;
	Bitmap mImg;

	public Article(){
		mId = UUID.randomUUID();
	}

	public Article(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mTitle = json.getString(JSON_TITLE);
		mSummary = json.getString(JSON_SUMMARY);
		mContent = json.getString(JSON_CONTENT);
		mImgLink = json.getString(JSON_IMGLINK);
		mImg = (Bitmap) json.get(JSON_IMG);
	}

	public UUID getId() {
		return mId;
	}

	public void setId(UUID id) {
		mId = id;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getImgLink() {
		return mImgLink;
	}

	public void setImgLink(String imgLink) {
		mImgLink = imgLink;
	}

	public String getSummary() {
		return mSummary;
	}

	public void setSummary(String summary) {
		mSummary = summary;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	@Override
	public String toString(){
		return mTitle;
	}

	@Override
	public int getViewType() {
		return RowType.LIST_ITEM.ordinal();
	}

	@Override
	public abstract View getView(LayoutInflater inflater, View convertView);

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_CONTENT, mContent);
		json.put(JSON_SUMMARY, mSummary);
		json.put(JSON_IMGLINK, mImgLink);
		json.put(JSON_IMG, mImg);
		return json;
	}
}
*/
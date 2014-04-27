/*package android.ih.news.deprecated;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.ih.news.deprecated.Article;

public class NewsJSONSerializer {
	private Context mContext;
	private String mFilename;
	
	public NewsJSONSerializer(Context c, String f) {
		mContext = c;
		mFilename = f;
	}

	public ArrayList<Article> loadArticles() throws IOException, JSONException  {
		ArrayList<Article> articles = new ArrayList<Article>();
		BufferedReader reader = null;
		try {
			// Open and read the file into a StringBuilder
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			// Parse the JSON using JSONTokener
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
			.nextValue();
			// Build the array of crimes from JSONObjects
//			for (int i = 0; i < array.length(); i++) {
//				switch (articles.get(i).getViewType()) {
//				case HEADER_ITEM :
//					articles.add(new HeadArticle(array.getJSONObject(i)));
//				case LIST_ITEM :
//					articles.add(new SubArticle(array.getJSONObject(i)));
//				case CATEGORY_ITEM :
//					articles.add(new Category(array.getJSONObject(i)));
//				}
//			}
		} catch (FileNotFoundException e) {
			// Ignore this one; it happens when starting fresh
		} finally {
			if (reader != null)
				reader.close();
		}
		return articles;
	}

	public void saveArticles(ArrayList<Article> articles)
			throws JSONException, IOException {
		// Build an array in JSON
		JSONArray array = new JSONArray();
		for (Article i : articles)
			array.put(i.toJSON());
		// Write the file to disk
		Writer writer = null;
		try {
			OutputStream out = mContext
					.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
*/
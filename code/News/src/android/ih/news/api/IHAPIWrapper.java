package android.ih.news.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.ih.news.model.Article;
import android.ih.news.model.Category;
import android.ih.news.model.Comment;
import android.ih.news.model.HeadArticle;
import android.ih.news.model.Newsflash;
import android.ih.news.model.SubArticle;
import android.util.JsonReader;
import android.util.Log;

/**
 * This is the wrapper class for the Israel Hayom REST API.
 * 
 * @author Peter
 */
public class IHAPIWrapper {

	private String baseUrl;
	private String key;
	private boolean addSleepTime; // TODO: for debug only, remove when done
	
	private static IHAPIWrapper _IHAPIWrapper = null;
	
	private IHAPIWrapper(String url, String key, boolean addSleepTime) {
		this.baseUrl = url;
		this.key = key;
		this.addSleepTime = addSleepTime;
	}

	private static void closeQuietly(BufferedReader in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
	        	Log.e("REST", "Failed to close stream", e);
			}
		}
	}
	
	public static IHAPIWrapper getInstance(String url, String key, boolean addSleepTime) {
		if (_IHAPIWrapper == null) {
			_IHAPIWrapper = new IHAPIWrapper(url, key, addSleepTime);
		}
		
		return _IHAPIWrapper;
	}
	
	/**
	 * @return the baseUrl, this is the base path for all REST functions
	 */
	private String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * The Israel Hayom key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Adds the Israel Hayom key to the url. 
	 */
	private String addKey() {
		return "?key=" + getKey();
	}
	
	/**
	 * @return the categories which are the first level of the tree
	 */
	public List<Category> getAllCategories() {
		
		List<Category> categories = new ArrayList<Category>();
		URL url = null;
        BufferedReader in = null;
        try {
        	url = new URL(getBaseUrl() + "category" + addKey());

			JsonReader reader = new JsonReader(new InputStreamReader(url.openStream(), "UTF-8"));
			try {
				sleepIfNeededToSimulateNetworkTime();
				Category.setCategoriesFromReader(reader, categories);
				
				// filter non-hebrew categories
//				Iterator<Category> iterator = categories.iterator();
//				while (iterator.hasNext()) {
//					Category category = iterator.next();
//					if (!Category.HEBREW_LANGUAGE.equals(category.getLang())) {
//						iterator.remove();
//					}					
//				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
        } catch (Exception e) {
        	Log.e("REST", "Failed to get categories", e);
        } finally {
        	closeQuietly(in);
        }

		return categories;
	}
	
	/**
	 * @return the newsflash
	 * @param limit how many items to fetch
	 * @param pagination where to start
	 */
	public List<Newsflash> getAllNewsflash(int limit, int pagination) {
		
		List<Newsflash> newsflash = new ArrayList<Newsflash>();
		URL url = null;
        BufferedReader in = null;
        try {
        	url = new URL(getBaseUrl() + "content/newsflash" + addKey() + "&limit=" + limit + "&offset" + pagination);
			JsonReader reader = new JsonReader(new InputStreamReader(url.openStream(), "UTF-8"));
			try {
				sleepIfNeededToSimulateNetworkTime();
				JSONUtil.readObjectArray(reader, newsflash, Newsflash.class);
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
        } catch (Exception e) {
        	Log.e("REST", "Failed to get categories", e);
        } finally {
        	closeQuietly(in);
        }

		return newsflash;
	}
	
	/**
	 * @return the android home page.
	 */
	public List<Article> getMainPageArticles() {

		List<HeadArticle> primery = new ArrayList<HeadArticle>(); // should be only one
		List<SubArticle> secondery = new ArrayList<SubArticle>();
		List<SubArticle> news = new ArrayList<SubArticle>();
		List<SubArticle> other = new ArrayList<SubArticle>();
		
		URL url = null;
        BufferedReader in = null;
        try {
        	url = new URL(getBaseUrl() + "homepage/android" + addKey());

			JsonReader reader = new JsonReader(new InputStreamReader(url.openStream(), "UTF-8"));
			try {
				sleepIfNeededToSimulateNetworkTime();
				reader.beginObject();
				while (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("primary")) {
						JSONUtil.readObjectArray(reader, primery, HeadArticle.class);
					} else if (name.equals("secondary")) {
						JSONUtil.readObjectArray(reader, secondery, SubArticle.class);
					} else if (name.equals("news")) {
						JSONUtil.readObjectArray(reader, news, SubArticle.class);
					} else if (name.equals("other")) {
						JSONUtil.readObjectArray(reader, other, SubArticle.class);
					} else {
						reader.skipValue();
					}			
				}				
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
        } catch (Exception e) {
        	Log.e("REST", "Failed to get main home page (or some part of it)", e);
        } finally {
        	closeQuietly(in);
        }
        
        // final list
        List<Article> articles = new ArrayList<Article>();
        articles.addAll(primery);
        articles.addAll(secondery);
        articles.addAll(news);
        articles.addAll(other);
		return articles;
	}
	
	/**
	 * To save time, this function returns only the basic info, for the full article call getFullArticle  
	 * 
	 * @param category the category to which the articles belong
	 * @param startIndex the point from where to start fetching
	 * @param num number of articles to fetch
	 * @return the requested number of articles of the given category
	 */
	public List<Article> getCategoryArticles(String category, int startIndex, int count) {
		Log.d("getCategoryArticles", "category:::" + category);
		
		List<Article> categoryArticles = new ArrayList<Article>();
		
		URL url = null;
        BufferedReader in = null;
        try {
        	url = new URL(getBaseUrl() + "content/article" + addKey() + "&category=" + category + "&offset=" + startIndex + "&limit=" + count);
        	Log.d("getCategoryArticles", "url:::" + url);
        	
			JsonReader reader = new JsonReader(new InputStreamReader(url.openStream(), "UTF-8"));
			try {
				sleepIfNeededToSimulateNetworkTime();
				JSONUtil.readObjectArray(reader, categoryArticles, SubArticle.class);
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
        } catch (Exception e) {
        	Log.e("getCategoryArticles", e.getMessage());
        } finally {
        	closeQuietly(in);
        }
        
        Log.d("getCategoryArticles", "categoryArticles.size:::" + categoryArticles.size());
		return categoryArticles;
	}
	
	/**
	 * To save time, this function returns only the basic info, for the full article call getFullArticle  
	 * 
	 * @param category the category to which the articles belong
	 * @param startIndex the point from where to start fetching
	 * @param num number of articles to fetch
	 * @return the requested number of articles of the given category
	 */
	public List<Comment> getArticleComments(Article article) {
		
		// TODO: replace with a call to getBaseUrl + "comment/article.getId()?key=nas987nh34"
		// might not have any data: "Response does not contain any data."
		
		sleepIfNeededToSimulateNetworkTime();
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(new Comment("��� �����!", "��� �����"));
		comments.add(new Comment("���� ����", "���� �����"));
		comments.add(new Comment("���� ����������", "�����"));
		return comments;
	}
	
	/**
	 * Simulates the network time, up to 10 seconds
	 */
	private void sleepIfNeededToSimulateNetworkTime() {
		if (addSleepTime) {
			try {
				int secsToSleep = (int)(Math.random() * 10) + 1; // sleep for up to 10 seconds
				Thread.sleep(secsToSleep * 1000);
			} catch (InterruptedException e) {
				System.err.println("sleep failed, returning faster then expected");
			}
		}
	}
}

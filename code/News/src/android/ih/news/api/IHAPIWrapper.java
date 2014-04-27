package android.ih.news.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.ih.news.model.Article;
import android.ih.news.model.Category;
import android.ih.news.model.Comment;
import android.ih.news.model.HeadArticle;
import android.ih.news.model.SubArticle;

/**
 * This is the wrapper class for the Israel Hayom REST API.
 * 
 * @author Peter
 */
public class IHAPIWrapper {

	private String baseUrl;
	private boolean addSleepTime; // TODO: for debug only, remove when done
	
	private static IHAPIWrapper _IHAPIWrapper = null;
	
	private IHAPIWrapper(String url, boolean addSleepTime) {
		this.baseUrl = url;
		this.addSleepTime = addSleepTime;
	}
	
	public static IHAPIWrapper getInstance(String url, boolean addSleepTime) {
		if (_IHAPIWrapper == null) {
			_IHAPIWrapper = new IHAPIWrapper(url, addSleepTime);
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
	 * @return the categories which are the first level of the tree
	 */
	public List<Category> getAllCategories() {
		
		// TODO: replace with a call to getBaseUrl + "/category"
		
		sleepIfNeededToSimulateNetworkTime();
		List<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Sports", 50, 1));
		categories.add(new Category("Economics", 50, 2));
		categories.add(new Category("Weather", 50, 3));
		categories.add(new Category("Culture", 50, 4));
		categories.add(new Category("Opinions", 50, 5));
		return categories;
	}
	
	/**
	 * To save time, this function returns only the basic info, for the full article call getFullArticle  
	 * 
	 * @param num number of articles to fetch
	 * @return the requested number of articles of the main page
	 */
	public List<Article> getMainPageArticles(int num) {
		
		// TODO: replace with a call to getBaseUrl + "/popular?platform=andriod&limit=limit"
		
		sleepIfNeededToSimulateNetworkTime();
		List<Article> articles = new ArrayList<Article>();
		
		articles.add(new HeadArticle(UUID.randomUUID(), "Main title, " + 0, "Hello world", "http://www.israelhayom.co.il/sites/default/files/styles/770x319/public/images/articles/2014/03/22/13954726443363_b.jpg"));
		
		for (int i = 1; i < num; i++) {
			articles.add(new SubArticle(UUID.randomUUID(), "Main title, " + i, "Hello world", "http://www.israelhayom.co.il/sites/default/files/styles/770x319/public/images/articles/2014/03/22/13954726443363_b.jpg"));
		}
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
	public List<Article> getCategoryArticles(Category category, int startIndex, int num) {
		
		// TODO: replace with a call to getBaseUrl + "/content/article?category=name&offset=startIndex&limit=num&sort=desc"
		
		sleepIfNeededToSimulateNetworkTime();
		List<Article> articles = new ArrayList<Article>();
		for (int i = 0; i < num; i++) {
			articles.add(new SubArticle(UUID.randomUUID(), "Some title " + startIndex + ", " + i, null, null));
		}
		return articles;
	}

	/**
	 * Completes the article with additional fields.
	 * @param articleId the partial article
	 */
	public Article getFullArticle(UUID articleId) {

		// TODO: replace with a call to getBaseUrl + "/content/article?id=article.getId()"
		
		sleepIfNeededToSimulateNetworkTime();
		
		SubArticle sa = new SubArticle(articleId, "hgdfgdfgd", "bcvbcvbc", "http://www.israelhayom.co.il/sites/default/files/styles/770x319/public/images/articles/2014/03/22/13954726443363_b.jpg");
		
		sa.setComments(getArticleComments(articleId));
		
		return sa;
	}
	
	/**
	 * To save time, this function returns only the basic info, for the full article call getFullArticle  
	 * 
	 * @param category the category to which the articles belong
	 * @param startIndex the point from where to start fetching
	 * @param num number of articles to fetch
	 * @return the requested number of articles of the given category
	 */
	private List<Comment> getArticleComments(UUID articleId) {
		
		// TODO: replace with a call to getBaseUrl + "/comment/article.getId()"
		
		sleepIfNeededToSimulateNetworkTime();
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(new Comment("אני ראשון!", "אני ראשון"));
		comments.add(new Comment("כנסו כנסו", "משהו מתלהם"));
		comments.add(new Comment("משהו אינטליגנטי", "מגניב"));
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

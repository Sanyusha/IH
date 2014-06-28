package android.ih.news;

import java.net.URL;
import android.content.Context;
import android.content.Intent;

public class StartActivity {
	
	public static void startArticleActivity(Context context, URL articleURL) {
		Intent i;
		
		i = new Intent(context, ArticlePagerActivity.class);
		i.putExtra(ArticleFragment.EXTRA_ARTICLE_URL, articleURL);
		
		context.startActivity(i);
	}
}

package android.ih.news;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;

public class StartActivity {
	
	public static void startArticleActivity(Context context, UUID articleID) {
		Intent i;
		
		i = new Intent(context, ArticlePagerActivity.class);
		i.putExtra(ArticleFragment.ARTICLE, articleID);
		
		context.startActivity(i);
	}
}

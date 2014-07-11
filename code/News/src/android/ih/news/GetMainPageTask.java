package android.ih.news;

import java.util.List;

import android.ih.news.ArticleListFragment.ArticleAdapter;
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.os.AsyncTask;
import android.util.Log;


public class GetMainPageTask extends AsyncTask<ArticleAdapter, Void, List<Article>> {
	ArticleAdapter articleAdapt = null;

    @Override
    protected List<Article> doInBackground(ArticleAdapter... adapter) {
    	if (adapter != null && adapter.length > 0) {
    		this.articleAdapt = adapter[0];
    	}
    	
    	return IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getMainPageArticles();
    }

    @Override
    protected void onPostExecute(List<Article> result) {
    	if(result == null){
    		Log.d("lilach", "result null");
    	}
    	if (articleAdapt != null) {
    		articleAdapt.clear();
    		articleAdapt.addAll(result);
    	}
    }
}

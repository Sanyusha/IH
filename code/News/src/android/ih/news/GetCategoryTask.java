package android.ih.news;

import java.util.List;

import android.ih.news.CategoryFragment.ArticleAdapter;
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.ih.piemenu.PieMenu;
import android.os.AsyncTask;
import android.util.Log;

public class GetCategoryTask extends AsyncTask<ArticleAdapter, Void, List<Article>> {
	
	//TODO: add cache
	ArticleAdapter articleAdapt = null;

    @Override
    protected List<Article> doInBackground(ArticleAdapter... adapter) {
    	this.articleAdapt = adapter[0];
    	return IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getCategoryArticles(PieMenu.getSelectedCategory(), 1, 20);
    }

    @Override
    protected void onPostExecute(List<Article> result) {
    	if(result == null){
    		Log.d("lilach", "result null");
    	}
        articleAdapt.clear();
        articleAdapt.addAll(result);
    }
}

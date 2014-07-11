package android.ih.news;

import java.util.List;

import android.ih.news.CategoryFragment.ArticleAdapter;
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.ih.piemenu.PieMenu;
import android.ih.piemenu.PieMenuItem;
import android.os.AsyncTask;
import android.util.Log;

public class GetCategoryTask extends AsyncTask<ArticleAdapter, Void, List<Article>> {
	
	ArticleAdapter articleAdapt = null;

    @Override
    protected List<Article> doInBackground(ArticleAdapter... adapter) {
    	this.articleAdapt = adapter[0];
    	PieMenuItem myCategory = PieMenu.getSelectedCategory();
    	if (myCategory != null) {
    		return IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getCategoryArticles(myCategory.getCode(), 0, 20, false);
    	}
    	
    	return null;
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

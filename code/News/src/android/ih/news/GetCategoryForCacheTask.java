package android.ih.news;

import android.ih.news.api.IHAPIWrapper;
import android.ih.piemenu.PieMenuItem;
import android.os.AsyncTask;

public class GetCategoryForCacheTask extends AsyncTask<PieMenuItem, Void, Object> {
	
    @Override
    protected Object doInBackground(PieMenuItem... categories) {
    	for (int i = 0; i < categories.length; i++) {
    		IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getCategoryArticles(categories[i].getCode(), 0, 20, false);
		}
    	return null;
    }

    @Override
    protected void onPostExecute(Object result) {
    	// no real result
    }
}

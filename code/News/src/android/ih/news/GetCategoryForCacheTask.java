package android.ih.news;

import android.ih.news.api.IHAPIWrapper;
import android.os.AsyncTask;

public class GetCategoryForCacheTask extends AsyncTask<String, Void, Object> {
	
    @Override
    protected Object doInBackground(String... categories) {
    	for (int i = 0; i < categories.length; i++) {
    		IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getCategoryArticles(categories[i], 0, 20, false);
		}
    	return null;
    }

    @Override
    protected void onPostExecute(Object result) {
    	// no real result
    }
}

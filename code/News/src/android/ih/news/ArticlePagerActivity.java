package android.ih.news;

import java.net.URL;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ArticlePagerActivity extends SingleFragmentActivity {
	
	protected Fragment createFragment() {
		URL mblURL = (URL) getIntent().getSerializableExtra(ArticleFragment.EXTRA_ARTICLE_URL);
		Log.d("createFragment", "mblUrl:::" + mblURL);
		return ArticleFragment.newInstance(mblURL);
	}
}

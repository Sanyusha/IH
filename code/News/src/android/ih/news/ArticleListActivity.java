package android.ih.news;

import android.support.v4.app.Fragment;

public class ArticleListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ArticleListFragment();
	}

}

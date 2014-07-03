package android.ih.news;

import java.util.UUID;

import android.support.v4.app.Fragment;
import android.util.Log;

public class ArticlePagerActivity extends SingleFragmentActivity {
	
	protected Fragment createFragment() {
		UUID articleId = (UUID) getIntent().getSerializableExtra(ArticleFragment.ARTICLE);
		Log.d("createFragment", "article:::" + articleId);
		return ArticleFragment.newInstance(articleId);
	}
}

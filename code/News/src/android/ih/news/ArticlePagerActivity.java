package android.ih.news;

import java.util.UUID;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;

public class ArticlePagerActivity extends SingleFragmentActivity {
	private ArticleFragment alf;
	
	protected Fragment createFragment() {
		UUID articleId = (UUID) getIntent().getSerializableExtra(ArticleFragment.ARTICLE);
		Log.d("createFragment", "article:::" + articleId);
		alf = ArticleFragment.newInstance(articleId);
		return alf;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_MENU) {
    	   alf.showPieDialog();
       }
	       
       return super.onKeyDown(keyCode, event);
	}
}

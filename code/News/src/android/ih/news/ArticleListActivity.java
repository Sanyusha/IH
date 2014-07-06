package android.ih.news;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

public class ArticleListActivity extends SingleFragmentActivity {
	
	private ArticleListFragment alf;
	
	@Override
	protected Fragment createFragment() {
		alf = new ArticleListFragment();
		return alf;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_MENU) {
    	   alf.showPieDialog();
       }
	       
       return super.onKeyDown(keyCode, event);
	}
}

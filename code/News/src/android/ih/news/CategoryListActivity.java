package android.ih.news;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

public class CategoryListActivity extends SingleFragmentActivity {

private CategoryFragment alf;
	
	@Override
	protected Fragment createFragment() {
		alf = new CategoryFragment();
		return alf;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_MENU) {
    	   alf.showPieDialog();
       }
	       
       return super.onKeyDown(keyCode, event);
	}

}

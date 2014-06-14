package android.ih.news;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class ArticlePagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private List<Article> mArticles;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);

//		mArticles = IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getMainPageArticles(10);
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public int getCount() {
				return mArticles.size();
			}

			@Override
			public Fragment getItem(int pos) {
				Article a = mArticles.get(pos);
				return ArticleFragment.newInstance(a.getId());
			}
		});

		UUID crimeId = (UUID)getIntent()
				.getSerializableExtra(ArticleFragment.EXTRA_ARTICLE_ID);
		for (int i = 0; i < mArticles.size(); i++) {
			if (mArticles.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}

	}
}

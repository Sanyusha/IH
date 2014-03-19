package com.example.news;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class ArticleLab {

	private static ArticleLab sLab; 
	private Context mAppContext;
	private ArrayList<Article> mArticles;
	private static final String FILENAME = "items.json";
	private NewsJSONSerializer mSerializer;
	private static final String TAG = "ArticleLab";
	
	private Parser p;

	private ArticleLab(Context appContext){
//		mAppContext = appContext;
//		mSerializer = new NewsJSONSerializer(mAppContext, FILENAME);
		
//		try {
//			mArticles = mSerializer.loadArticles();
//			} catch (Exception e) {
			mArticles = new ArrayList<Article>();
//			Log.e(TAG, "Error loading articles: ", e);
//			}
		
		Article c = new HeadArticle();
		c.setTitle("כתבה כתבה כתבה ניסיון #" + 0);
		c.setSummary("בלה בלה בלה סוקול ספארטק מנציסטר יונייטד בלה בלה בלה ניסיון... #" + 0);
		c.setContent("blaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa #"+0 );
		c.setImgLink("http://www.israelhayom.co.il/sites/default/files/styles/241x148/public/images/articles/2014/03/06/1394141622462_b.jpg");
		mArticles.add(c);
		for (int i = 1; i < 100; i++) {
			if (i % 10 == 0) {
				
			} else {
			c = new SubArticle();
			c.setTitle("כתבה כתבה כתבה ניסיון #" + i);
			c.setSummary("בלה בלה בלה סוקול ספארטק מנציסטר יונייטד בלה בלה בלה ניסיון... #" + i);
			c.setContent("blaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa #"+i );
			c.setImgLink("http://www.israelhayom.co.il/sites/default/files/styles/241x148/public/images/articles/2014/03/06/1394141622462_b.jpg");
			mArticles.add(c);
			}}

	}

	public static ArticleLab get(Context c){
		if(sLab == null){
			sLab = new ArticleLab(c.getApplicationContext());
		}
		return sLab;
	}

	public ArrayList<Article> getArticles(){
		return mArticles;
	}

	public Article getArticle(UUID id) {
		for (Article a : mArticles) {
			if (a.getId().equals(id))
				return a;
		}
		return null;
	}
	
	public boolean saveArticles() {
		try {
		mSerializer.saveArticles(mArticles);
		Log.d(TAG, "articles saved to file");
		return true;
		} catch (Exception e) {
		Log.e(TAG, "Error saving articles: ", e);
		return false;
		}
		}

}

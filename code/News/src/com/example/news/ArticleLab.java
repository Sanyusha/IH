package com.example.news;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class ArticleLab {

	private static ArticleLab sLab; 
	private Context mAppContext;
	private ArrayList<Article> mArticles;

	private ArticleLab(Context appContext){
		mAppContext = appContext;
		mArticles = new ArrayList<Article>();
		
		HeadArticle ha = new HeadArticle();
		ha.setTitle("מבוקש פעיל חמאס נהרג בפעילות צה''ל בג'נין");
		ha.setSummary("היה מעורב בפיגועי ירי ובהנחת מטענים נגד צה''ל • יחד איתו נהרגו 3 פלשתינים נוספים • שני לוחמים נפצעו קל...");
		ha.setImgLink("http://www.israelhayom.co.il/sites/default/files/styles/770x319/public/images/articles/2014/03/22/13954726443363_b.jpg");
		
		mArticles.add(ha);
		
		SubArticle sa = new SubArticle();
		sa.setTitle("השופטת מהקרמלין");
		sa.setSummary("כך הפכה השופטת בדימוס עדינה פורת, 87, למטהר האוויר של רן ארז ואנשיו (ב')...");
		sa.setImgLink("http://www.israelhayom.co.il/sites/default/files/styles/241x148/public/images/articles/2014/03/21/13953529854051_b.jpg");
		
		mArticles.add(sa);
		
		sa = new SubArticle();
		sa.setTitle("הולכת נגד הרוח");
		sa.setSummary("בין תפקידי האלמנה השחורה לחייזרית טורפת גברים, ג'והנסון מנצלת את פאריס לרגעים רומנטיים עם ארוסה הצרפתי...");
		sa.setImgLink("http://www.israelhayom.co.il/sites/default/files/styles/241x148/public/images/articles/2014/03/20/13952680672056_b.jpg");
		
		mArticles.add(sa);
		
		sa = new SubArticle();
		sa.setTitle("מרגול ופנינה - החברות הכי טובות");
		sa.setSummary("צפו: קוסם מפינלנד בדק איך מגיבים כלבים כשהחטיף נעלם מתחת לאף...");
		sa.setImgLink("http://www.israelhayom.co.il/sites/default/files/styles/241x148/public/images/articles/2014/03/22/1395479841652_b.jpg");
		
		mArticles.add(sa);
		
		sa = new SubArticle();
		sa.setTitle("ולתפארת מדינת ישראל");
		sa.setSummary("כששחר פאר התבשרה שנבחרה להדליק משואה בערב יום העצמאות, היא רעדה מהתרגשות ופרצה בבכי...");
		sa.setImgLink("http://www.israelhayom.co.il/sites/default/files/styles/566x349/public/images/articles/2014/03/20/13952677007395_b.jpg");
		
		mArticles.add(sa);
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

}

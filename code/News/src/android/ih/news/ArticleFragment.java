// test push
package android.ih.news;

import java.net.URL;

///fujgfh,ilh
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

public class ArticleFragment extends Fragment {

	public static final String EXTRA_ARTICLE_ID = "andorid.ih.news.article_id";
	public static final String EXTRA_ARTICLE_URL = "article_url";
	private URL mMblURL;
	
	public static ArticleFragment newInstance(URL articleURL){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_ARTICLE_URL, articleURL);
		
		ArticleFragment fragment = new ArticleFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//UUID articleId = (UUID)getArguments().getSerializable(EXTRA_ARTICLE_ID);
		//mArticle = IHAPIWrapper.getInstance("fdsfadsfas", true).getFullArticle(articleId);
		mMblURL = (URL) getArguments().getSerializable(EXTRA_ARTICLE_URL);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v;
		WebView myWebView;
		String mblURL;
		ImageView logoImage;
		String htmlText;
		
		htmlText = "<html>" + "<head>" + "<style type=\"text/css\">" + "body {background-color: #000000;}" + "p {background-color: #000000;}" + "div {background-color: #000000;}" + "head {background-color: #000000;}" + "</style>" + "</head>";
		
		mblURL = this.mMblURL.toString();
		Log.d("onCreateView", "mblURL:::" + mblURL);
		
		v = inflater.inflate(R.layout.fragment_article, parent, false);
		
		logoImage = (ImageView) v.findViewById(R.id.logo_image);
	    logoImage.setImageResource(R.drawable.black_logo);
	    
		myWebView = (WebView) v.findViewById(R.id.webview);
		String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + mblURL;
	    // lets assume we have /assets/style.css file
	    //myWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
	 
		//myWebView.loadData(htmlText, "text/html", "utf-8");
		myWebView.loadUrl(mblURL);
		
		//myWebView.loadDataWithBaseURL(htmlText, mblURL, "text/html", "utf-8", null);
		
		return v;
	}

	@Override
	public void onPause() {
		super.onPause();
		//ArticleLab.get(getActivity()).saveArticles();
	}

}

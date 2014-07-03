package android.ih.news;

import java.util.UUID;

import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.AnnotatedImage.ImageSize;
import android.ih.news.model.Article;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

public class ArticleFragment extends Fragment {

	public static final String ARTICLE = "article_param";
	private Article mArticle;
	
	public static ArticleFragment newInstance(UUID articleId){
		Bundle args = new Bundle();
		args.putSerializable(ARTICLE, articleId);
		
		ArticleFragment fragment = new ArticleFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mArticle = IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false)
					.getArticleById((UUID) getArguments().getSerializable(ARTICLE));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v;
		WebView myWebView;
		ImageView logoImage;
		
		v = inflater.inflate(R.layout.fragment_article, parent, false);
		
		logoImage = (ImageView) v.findViewById(R.id.logo_image);
	    logoImage.setImageResource(R.drawable.black_logo);
	    
		myWebView = (WebView) v.findViewById(R.id.webview);
		String htmlData = null;
		
		if (mArticle.getImages() != null && mArticle.getImages().size() > 0) {
			htmlData = String.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + 
				"<body class=\"rtl\">" +
					"<article id=\"article-article\"><h1 class=\"article_title\">%s</h1><header><div class=\"secondary_title\">%s</div>%s</header>" +
					"<div id=\"image_box\"><img src=\"%s\" width=\"310\"><div id=\"image_description\" class=\"figcaption\">%s</div></div>" +
					"<div id=\"article_body\" class=\"article_body\">%s</div></body>", 
				TextUtils.htmlEncode(mArticle.getTitle()), TextUtils.htmlEncode(mArticle.getSummary()), TextUtils.htmlEncode(mArticle.getAuthor().getName()), 
				mArticle.getImages().get(0).getProperURLForSize(ImageSize.ARTICLE_PAGE), TextUtils.htmlEncode(mArticle.getImages().get(0).getLabel()), mArticle.getContent()); 
		} else {
			htmlData = String.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + 
					"<body class=\"rtl\">" +
						"<article id=\"article-article\"><h1 class=\"article_title\">%s</h1><header><div class=\"secondary_title\">%s</div>%s</header>" +
						"<div id=\"article_body\" class=\"article_body\">%s</div></body>", 
					TextUtils.htmlEncode(mArticle.getTitle()), TextUtils.htmlEncode(mArticle.getSummary()), TextUtils.htmlEncode(mArticle.getAuthor().getName()), 
					mArticle.getContent()); 
		}		
	    
	    // we have /assets/style.css file
	    myWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", mArticle.getMobileUrl().toString());
		return v;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}

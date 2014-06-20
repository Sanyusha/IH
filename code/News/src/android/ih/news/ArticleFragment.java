package android.ih.news;

///fujgfh,ilh
import java.util.UUID;

import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {

	public static final String EXTRA_ARTICLE_ID = "andorid.ih.news.article_id";
	private Article mArticle;
	private TextView mTitleTextView;
	private TextView mContentTextView;

	public static ArticleFragment newInstance(UUID articleId){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_ARTICLE_ID, articleId);
		ArticleFragment fragment = new ArticleFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UUID articleId = (UUID)getArguments().getSerializable(EXTRA_ARTICLE_ID);
		//mArticle = IHAPIWrapper.getInstance("fdsfadsfas", true).getFullArticle(articleId);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_article, parent, false);

		mTitleTextView = (TextView)v.findViewById(R.id.article_titleTextView);
		mTitleTextView.setText(mArticle.getTitle());

		mContentTextView = (TextView)v.findViewById(R.id.article_contentTextView);
		mContentTextView.setText(mArticle.getSummary());

		return v;
	}

	@Override
	public void onPause() {
		super.onPause();
		//ArticleLab.get(getActivity()).saveArticles();
	}

}

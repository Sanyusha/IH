package android.ih.news;

import java.util.List;

import android.content.Intent;
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleListFragment extends ListFragment implements OnLongClickListener {
	private static final String TAG = "ArticleListFragment";

	private List<Article> mArticles;
	View view;
	
	private GestureDetector gestureDetector;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.articles_title);
		
		
		//mArticles = ArticleLab.get(getActivity()).getArticles();
		mArticles = IHAPIWrapper.getInstance("Hello world", true).getMainPageArticles(10);
		
		ArticleAdapter adapter = new ArticleAdapter(mArticles);
		setListAdapter(adapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
	    view = inflater.inflate(R.layout.list_article_fragment, container, false);
	    
	    ImageView logoImage = (ImageView) view.findViewById(R.id.logo_image);
	    logoImage.setImageResource(R.drawable.black_logo);
	    
	    setTicker();
	    
	    //logoImage.setOnLongClickListener(this);
	    
//	    final GestureDetector gestureDetector = new GestureDetector(view.getContext(), new GestureDetector.SimpleOnGestureListener() {
//	        public void onLongPress(MotionEvent e) {
//	        	//if(e.getAction() == MotionEvent.ACTION_DOWN) {
//                    String text = "You click at x = " + e.getX() + " and y = " + e.getY();
//                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
//                //}
//	        	
//	            Log.e("longpress", "Longpress detected");
//	        }
//	    });
	    
	    gestureDetector = new GestureDetector(view.getContext(), new UserGestureDetector(view.getContext()));
	    
	    // using TouchListener provides us coordinates of press
	    view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
            	gestureDetector.onTouchEvent(event);
                return true;
            }
	    });
	    
	    
	    
	    return view;
	}
	
	private void setTicker() {
		String scrollingText;
		scrollingText ="  �  " + "����: ����� �� ���� �� ���� ����� ����� (������)" + "               " +
				"  �  " + "������ ������� ����� ����� ����� 30 ����� ���� ��� ����� ����� (�������� �������)";
		
		TextView tv = (TextView) view.findViewById(R.id.scrollingTicker);
		tv.setText(scrollingText);
		
		tv.setSelected(true);
	}
	
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id){
//		Article a = ((ArticleAdapter)getListAdapter()).getItem(position);
//		Log.d(TAG, a.getTitle() + " was clicked" );
//		
//		Intent i = new Intent(getActivity(), ArticlePagerActivity.class);
//		i.putExtra(ArticleFragment.EXTRA_ARTICLE_ID, a.getId());
//		startActivity(i);
//		
//	}
	
	private class ArticleAdapter extends ArrayAdapter<Article>{
//		public enum RowType {
//	        // Here we have two items types, you can have as many as you like though
//	        LIST_ITEM, HEADER_ITEM
//	    }
		
		public ArticleAdapter(List<android.ih.news.model.Article> mArticles){
			super(getActivity(), 0 , mArticles);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			/*
			Log.d("adapter", "positionOUT:::" + position);
			
			View view;
			
			if(convertView == null){
				Log.d("adapter", "position:::" + position);
				if (position == 0) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_first_item_article, null);
				} else {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_article, null);
				}
				
			}
			
			Article article = getItem(position);
			
//			Button supposedToBePicture = (Button)convertView.findViewById(R.id.supposed_to_be_picture);
//			supposedToBePicture.setText("picture");

			ImageView articleImageView;
			articleImageView = (ImageView)convertView.findViewById(R.id.list_item_imageView);
			
			articleImageView.setTag(article.getImgLink());
			new DownloadImagesTask().execute(articleImageView);
			
			//articleImageView.setImageResource(R.drawable.images_logo2_he);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.article_list_item_titleTextView);
			titleTextView.setText(article.getTitle());
			
			TextView summaryTextView = (TextView)convertView.findViewById(R.id.article_list_item_summaryTextView);
			summaryTextView.setText(article.getSummary());
			
						
			return convertView;
			*/
			
			// Use getView from the Item interface
	        return mArticles.get(position).getView(getActivity().getLayoutInflater(), convertView);
		}
		
		@Override
	    public int getViewTypeCount() {
	        // Get the number of items in the enum
	        return 2;
	 
	    }
	 
	    /*@Override
	    public int getItemViewType(int position) {
	        // Use getViewType from the Item interface
	        return mArticles.get(position).getViewType();
	    }*/

	}

	/*@Override
    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent object holds X-Y values
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
 
        return super.onTouchEvent(event);
    }*/
	
	public boolean onLongClick(View arg0) {
		Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_LONG).show();
		return false;
	}
}

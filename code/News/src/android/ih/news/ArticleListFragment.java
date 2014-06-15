package android.ih.news;

import java.util.ArrayList;
import java.util.List;


import android.app.Dialog;
import android.content.Intent;

import android.graphics.Point; //********************************* #0 added by lilach
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
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

	private List<Article> mArticles = new ArrayList<Article>();
	View view;
	
	private GestureDetector gestureDetector;
	
	//**************************** #1 added by lilach- start
	boolean stillDown;
	private static Point lastTouch;
	//**************************** #1 added by lilach- end
	
	
	private static final float PIE_DIALOG_ALPHA = (float) 0.85;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.articles_title);
		
		
		//mArticles = ArticleLab.get(getActivity()).getArticles();
//		mArticles = IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getMainPageArticles(10);
		lastTouch = new Point(); //added by lilach
		ArticleAdapter adapter = new ArticleAdapter(mArticles);
		setListAdapter(adapter);
		new GetMainPageTask().execute(adapter);
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
	    
	    
	    //*************************************** #2 deleted by lilach - start
//	    // using TouchListener provides us coordinates of press
//	    view.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//            	gestureDetector.onTouchEvent(event);
//                return true;
//            }
//	    });
	    //*************************************** #2 deleted by lilach - end
	    
	    //**************************************** #3 added by lilach- start 
	    class touchList implements OnTouchListener
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				final int action = event.getAction();
				switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN: {
					lastTouch.x = (int) event.getX();
					lastTouch.y = (int) event.getY();
					Thread th = new Thread(new waitAndStartDialog());
					th.start();
					break;
				}
				case MotionEvent.ACTION_UP:
					stillDown = false;
				}
				return true;
			}
		}
		view.setOnTouchListener(new touchList());
		ListView listView = (ListView)view.findViewById(android.R.id.list);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
//				int[] location = new int[2];
//				arg1.getLocationOnScreen(location);
//				Dialog dialog = new Dialog(getActivity());
//				WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
//				WMLP.height += 50;
//				WMLP.gravity = Gravity.TOP;// | Gravity.LEFT;
//				WMLP.x = location[0];   //x position
//				WMLP.y = location[1];   //y position
//				dialog.getWindow().setAttributes(WMLP);
//				dialog.setContentView(R.layout.pie_dlg);
//				dialog.setTitle("yeyyyyyy!!!!!!!!");
//				dialog.show();
				
				showPieDialog();
				
				return true;
			}
		});
		//****************************************** #3 added by lilach -end 
		
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
	
	public class ArticleAdapter extends ArrayAdapter<Article>{
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
	
	//******************************************* #4 added by lilach
	private class waitAndStartDialog implements Runnable
	{
		@Override
		public void run() {
			stillDown = true;
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (stillDown)
			{
				ArticleListFragment.this.getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Dialog dialog = new Dialog(getActivity());
						WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
						WMLP.gravity = Gravity.TOP | Gravity.LEFT;
						WMLP.height += 50;
						WMLP.x = lastTouch.x;   //x position
						WMLP.y = lastTouch.y;   //y position
						dialog.getWindow().setAttributes(WMLP);
						dialog.setContentView(R.layout.pie_dlg);
						dialog.show();
					}
				});
			}
		}
	}
	//*********************************************** #4 added by lilach- end
	
	private void showPieDialog() {
		Dialog dialog = new Dialog(getActivity(), R.style.full_screen_dialog);
		WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
		//WMLP.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		//WMLP.gravity = Gravity.TOP | Gravity.LEFT;
		//WMLP.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		//WMLP.height += 50;
		//WMLP.x = lastTouch.x;   //x position
		//WMLP.y = lastTouch.y;   //y position
		WMLP.alpha = PIE_DIALOG_ALPHA;
		//WMLP.width = WindowManager.LayoutParams.MATCH_PARENT;
		//WMLP.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(WMLP);
		//dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
		//		ViewGroup.LayoutParams.MATCH_PARENT);
		dialog.setContentView(R.layout.pie_dlg);
		dialog.show();
	}
}

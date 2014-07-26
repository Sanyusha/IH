package android.ih.news;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Dialog;
import android.graphics.Point;
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.ih.piemenu.PieMenu;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//********************************* #0 added by lilach

public class CategoryFragment extends ListFragment implements OnLongClickListener, OnScrollListener {
	//private static final String TAG = "ArticleListFragment";

	private List<Article> mArticles = new ArrayList<Article>();
	View view;

	//private GestureDetector gestureDetector;

	private ArticleAdapter adapter;
	//**************************** #1 added by lilach- start
	boolean stillDown;
	private static Point lastTouch;
	//**************************** #1 added by lilach- end

	private static final float PIE_DIALOG_ALPHA = (float) 0.85;

	TextView mTextView;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.articles_title);
		//mArticles = ArticleLab.get(getActivity()).getArticles();
		//		mArticles = IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getMainPageArticles(10);
		lastTouch = new Point(); //added by lilach
		adapter = new ArticleAdapter(mArticles);
		setListAdapter(adapter);
		new GetCategoryTask().executeOnExecutor(IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getCategoryArticleExecutor(), adapter);
		//TestPieMenuItem root = new TestPieMenuItem();
		//root.setResources(getResources());
		//root.setImage(new AnnotatedImage("img1", "local", 
		//		BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		//PieMenu.getMenu().getRoot().setData(root);
		//new SetTreeCategoriesTask().execute(PieMenu.getMenu());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.category_list_fragment, container, false);

		ImageView logoImage = (ImageView) view.findViewById(R.id.logo_image);
		logoImage.setImageResource(R.drawable.black_logo);
		logoImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showPieDialog();
			}
		});

		setTicker();

		TextView tv = (TextView) view.findViewById(R.id.category_titleTextView);
		tv.setText(PieMenu.getSelectedCategory().getTitle());

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

		/*gestureDetector = */new GestureDetector(view.getContext(), new UserGestureDetector(view.getContext()));

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
		//	    class touchList implements OnTouchListener
		//		{
		//			@Override
		//			public boolean onTouch(View v, MotionEvent event) 
		//			{
		//				final int action = event.getAction();
		//				switch (action & MotionEvent.ACTION_MASK) {
		//				case MotionEvent.ACTION_DOWN: {
		//					lastTouch.x = (int) event.getX();
		//					lastTouch.y = (int) event.getY();
		//					Thread th = new Thread(new waitAndStartDialog());
		//					th.start();
		//					break;
		//				}
		//				case MotionEvent.ACTION_UP:
		//					stillDown = false;
		//				}
		//				return true;
		//			}
		//		}
		//		view.setOnTouchListener(new touchList());
		//		ListView listView = (ListView)view.findViewById(android.R.id.list);
		//listView.setOnItemLongClickListener(new OnItemLongClickListener() {

		//			@Override
		//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		//					int arg2, long arg3) {
		//				
		//				showPieDialog();
		//				
		//				return true;
		//			}
		//		});
		//****************************************** #3 added by lilach -end 

		return view;
	}

	public void onListItemClick(ListView l, View v, int position, long id){
		Article a;

		a = ((ArticleAdapter)getListAdapter()).getItem(position);

		StartActivity.startArticleActivity(getActivity(), a.getId());
	}



	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getListView().setOnScrollListener(this);
	}

	private void setTicker() {
		mTextView = (TextView) view.findViewById(R.id.scrollingTicker);

		AsyncTask<String, Integer, String> setTask = new SetNewsFlashTask().execute();
		String scrollingText = "";
		try {
			scrollingText = setTask.get(); // TODO: this is waiting until the task is completed and blocks the UI
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mTextView.setText(scrollingText);
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
				CategoryFragment.this.getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						showPieDialog();
					}
				});
			}
		}
	}
	//*********************************************** #4 added by lilach- end

	public void showPieDialog() {
		final Dialog dialog = new Dialog(getActivity(), R.style.full_screen_dialog);
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

		PieMenu pm = (PieMenu) dialog.findViewById(R.id.pieMenu);
		pm.setDlg(dialog);
		//		
		//		pm.setOnLongClickListener(new OnLongClickListener() {
		//			public boolean onLongClick(View v) {
		//				Toast.makeText(dialog.getContext(), "Long click on pie", Toast.LENGTH_SHORT).show();
		//				return false;
		//			}
		//		});

		dialog.show();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (totalItemCount > 0 && !GetCategoryTask.updateInProgress && firstVisibleItem+visibleItemCount > totalItemCount - 2)
		{
			new GetCategoryTask().executeOnExecutor(IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getCategoryArticleExecutor(), adapter);
		}
	}
}

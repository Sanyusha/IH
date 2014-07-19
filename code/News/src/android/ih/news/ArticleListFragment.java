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
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//********************************* #0 added by lilach

public class ArticleListFragment extends ListFragment implements OnLongClickListener {
	//private static final String TAG = "ArticleListFragment";

	TextView mTextView;
	HorizontalScrollView hScroll;
	Handler hHandler;
	
	private List<Article> mArticles = new ArrayList<Article>();
	View view;
	int scroll_pos;
	
	//private String sFlashNews;
	
	//private GestureDetector gestureDetector;

	//**************************** #1 added by lilach- start
	boolean stillDown;

	private Dialog pieDialog;
	private static Point lastTouch;
	//**************************** #1 added by lilach- end

	public static final float PIE_DIALOG_ALPHA = (float) 0.85;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.articles_title);
		//mArticles = ArticleLab.get(getActivity()).getArticles();
		//		mArticles = IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getMainPageArticles(10);
		lastTouch = new Point(); //added by lilach
		ArticleAdapter adapter = new ArticleAdapter(mArticles);
		//ArticleAdapter adapter = new ArticleAdapter(IHAPIWrapper.categoryArticlesCache.get(IHAPIWrapper.HOMEPAGE_CATEGORY));
		setListAdapter(adapter);
		new GetMainPageTask().execute(adapter);
		pieDialog = null;
	}

	public void onListItemClick(ListView l, View v, int position, long id){
		Article a;

		a = ((ArticleAdapter)getListAdapter()).getItem(position);

		StartActivity.startArticleActivity(getActivity(), a.getId());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.list_article_fragment, container, false);

		ImageView logoImage = (ImageView) view.findViewById(R.id.logo_image);
		logoImage.setImageResource(R.drawable.black_logo);

		setTicker();

		//**************************************** #3 added by lilach- start 
		class touchList implements OnTouchListener
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if (pieDialog != null)
				{
					pieDialog.onTouchEvent(event);
				}
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
				showPieDialog();
				return true;
			}
		});
		//****************************************** #3 added by lilach -end 

		return view;
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

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		//savedInstanceState.putParcelableArrayList(KEY_ARTICLES, mArticles);
	}


	public class ArticleAdapter extends ArrayAdapter<Article>{
		private static final int TYPE_ITEM = 0;
		private static final int TYPE_MAIN = 1;
		private static final int TYPE_MAX_COUNT = 2;

		private LayoutInflater mInflater;

		public ArticleAdapter(List<android.ih.news.model.Article> mArticles){
			super(getActivity(), 0 , mArticles);

			mInflater = getActivity().getLayoutInflater();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			int type = getItemViewType(position);

			//System.out.println("getView " + position + " " + convertView + " type = " + type);

			if (convertView == null) {
				holder = new ViewHolder();

				switch (type) {
				case TYPE_MAIN:
					convertView = mInflater.inflate(R.layout.list_first_item_article, null);

					holder.titleTextView = (TextView) convertView.findViewById(R.id.article_list_item_titleTextView);
					holder.articleImageView = (ImageView) convertView.findViewById(R.id.list_item_imageView);
					holder.summaryTextView = (TextView) convertView.findViewById(R.id.article_list_item_summaryTextView);

					break;
				case TYPE_ITEM:
					convertView = mInflater.inflate(R.layout.list_item_article, null);

					holder.titleTextView = (TextView) convertView.findViewById(R.id.article_list_item_titleTextView);
					holder.articleImageView = (ImageView) convertView.findViewById(R.id.list_item_imageView);
					holder.summaryTextView = (TextView) convertView.findViewById(R.id.article_list_item_summaryTextView);

					break;
				}

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}

			holder.titleTextView.setText(mArticles.get(position).getTitle());

			if (mArticles.get(position).getImages() != null && mArticles.get(position).getImages().size() > 0) {
				holder.articleImageView.setTag(mArticles.get(position).getImages().get(0).getProperURL());
				//holder.articleImageView.setTag(mArticles.get(position).getImages().get(0).getImage());
				new DownloadImagesTask().execute(holder.articleImageView);
			}

			holder.summaryTextView.setText(mArticles.get(position).getSummary());

			return convertView;

			// Use getView from the Item interface
			//return mArticles.get(position).getView(getActivity().getLayoutInflater(), convertView);
		}

		public int getViewTypeCount() {
			return TYPE_MAX_COUNT;
		}

		public int getItemViewType(int position) {
			return position == 0 ? TYPE_MAIN : TYPE_ITEM;
		}

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
						showPieDialog();
					}
				});
			}
		}
	}
	//*********************************************** #4 added by lilach- end

	public void showPieDialog() {
		pieDialog = new Dialog(getActivity(),
				R.style.full_screen_dialog);
		WindowManager.LayoutParams WMLP = pieDialog.getWindow()
				.getAttributes();
		//WMLP.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		//WMLP.gravity = Gravity.TOP | Gravity.LEFT;
		//WMLP.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		//WMLP.height += 50;
		//WMLP.x = lastTouch.x;   //x position
		//WMLP.y = lastTouch.y;   //y position
		WMLP.alpha = PIE_DIALOG_ALPHA;
		//WMLP.width = WindowManager.LayoutParams.MATCH_PARENT;
		//WMLP.height = WindowManager.LayoutParams.WRAP_CONTENT;
		pieDialog.getWindow().setAttributes(WMLP);
		//dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
		//		ViewGroup.LayoutParams.MATCH_PARENT);
		pieDialog.setContentView(R.layout.pie_dlg);
		PieMenu pm = (PieMenu) pieDialog.findViewById(R.id.pieMenu);
		pm.setDlg(pieDialog);
		pieDialog.show();

	}
}

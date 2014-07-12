package android.ih.news;

import java.util.List;

import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Newsflash;
import android.os.AsyncTask;

//added by lilach 21/6- start
	public class setNewsFlashTask extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String... params) {
			String scrollingText = "";
			
			List<Newsflash> newsflashList =  IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false)
					.getAllNewsflash(10, 0);
			
			for (Newsflash newsflash : newsflashList) {
				scrollingText = scrollingText.concat(newsflash.getTitle() + "\n\n");
			}
			return scrollingText;
		}

	}

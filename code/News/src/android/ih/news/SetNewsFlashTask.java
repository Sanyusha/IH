package android.ih.news;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.os.AsyncTask;

public class SetNewsFlashTask extends AsyncTask<String, Integer, String>
{
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  dd/MM  ", Locale.getDefault());
	
	@Override
	protected String doInBackground(String... params) {
		StringBuilder scrollingText = new StringBuilder();
		
		sdf.setTimeZone(TimeZone.getTimeZone("Israel"));
		
		List<Article> newsflashList =  IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false)
				.getAllNewsflash(10, 0);
		
		for (Article newsflash : newsflashList) {
			scrollingText.append(sdf.format(newsflash.getDate())).append(newsflash.getTitle()).append("\n\n");
		}
		return scrollingText.toString();
	}

}
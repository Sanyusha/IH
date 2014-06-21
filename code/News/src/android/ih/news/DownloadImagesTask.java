package android.ih.news;

import android.graphics.Bitmap;
import android.ih.news.model.AnnotatedImage;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {
	
    ImageView imageView = null;

    @Override
    protected Bitmap doInBackground(ImageView... imageViews) {
        this.imageView = imageViews[0];
        return AnnotatedImage.downloadImage((String)imageView.getTag());
    }

    @Override
    protected void onPostExecute(Bitmap result) {
    	if(result == null){
    		Log.d("lilach", "result null");
    	}
        imageView.setImageBitmap(result);
    }
}

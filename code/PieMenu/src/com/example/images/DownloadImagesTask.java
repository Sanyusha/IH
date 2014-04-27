package com.example.images;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.piemenu.PieMenu;

public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {
    PieMenu view;
    Bitmap bmp1;
    int i;
    
    public DownloadImagesTask(PieMenu view, int i) {
		this.view = view;
		this.i = i;
	}
    
    @Override
    protected Bitmap doInBackground(String... params) {
    	Log.d("Sanya", params[0]);
    	return download_Image(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
    	if(result == null){
    		Log.d("lilach", "result nullllllllllllll");
    	}
    	if (result != null) {
    		Log.d("Andrey", "result NOT nullllllllllllll");
    		view.circleBmp[i] = result;
    	}
        //imageView.setImageBitmap(result);
        view.SOKOL = 1;
        view.invalidate();
    }

    private Bitmap download_Image(String url) {

        Bitmap bmp =null;
        try{
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

            }catch(Exception e){
            	Log.e("blabla", "blublu", e);
            }
//        if (bmp == null) {
//        	Log.d("download", "вн лап NULL");
//        }
        return bmp;
    }
}

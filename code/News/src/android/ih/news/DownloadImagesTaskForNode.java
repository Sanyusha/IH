package android.ih.news;

import android.graphics.Bitmap;
import android.ih.news.model.AnnotatedImage;
import android.ih.news.model.AnnotatedImage.ImageSize;
import android.ih.piemenu.BasicTree.Node;
import android.ih.piemenu.PieMenuItem;
import android.ih.piemenu.TestPieMenuItem;
import android.os.AsyncTask;
import android.util.Log;


public class DownloadImagesTaskForNode extends AsyncTask<Node<PieMenuItem>, Void, Bitmap> {
	
	TestPieMenuItem node = null;

    @Override
    protected Bitmap doInBackground(Node<PieMenuItem>... node) {
        this.node = (TestPieMenuItem) node[0].getData();
        this.node.getImage().setSize(ImageSize.PIE);
        return AnnotatedImage.downloadImage(this.node.getImage().getProperURL());
    }

    @Override
    protected void onPostExecute(Bitmap result) {
    	if(result == null){
    		Log.d("lilach", "result null");
    	}
    	else {
	    	synchronized (this.node) {
	    		node.getImage().setImage(result);
			}
    	}
    }
}

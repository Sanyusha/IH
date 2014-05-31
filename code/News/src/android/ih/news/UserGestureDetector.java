package android.ih.news;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class UserGestureDetector extends GestureDetector.SimpleOnGestureListener {
	private Context mContext;
	
	public UserGestureDetector(Context context) {
		this.mContext = context;
	}
	
    public void onLongPress(MotionEvent e) {
    	//if(e.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + e.getX() + " and y = " + e.getY();
            
            Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
        //}
    	
        Log.e("longpress", "Longpress detected");
    }    
}

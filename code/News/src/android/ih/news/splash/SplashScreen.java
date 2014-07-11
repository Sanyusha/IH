package android.ih.news.splash;

import android.app.Activity;
import android.content.Intent;
import android.ih.news.ArticleListActivity;
import android.ih.news.R;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class SplashScreen extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 10000;
    
    private Handler myHandler;
    
    private Runnable myRun = new Runnable() {
    	 
        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */

        @Override
        public void run() {
            // This method will be executed once the timer is over
            // Start your app main activity
            Intent i = new Intent(SplashScreen.this, ArticleListActivity.class);
            startActivity(i);

            // close this activity
            finish();
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        myHandler = new Handler();
        myHandler.postDelayed(myRun, SPLASH_TIME_OUT);
        
        Button btnSkip = (Button) findViewById(R.id.btnSkip);
        
        btnSkip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myHandler.removeCallbacks(myRun);
				myRun.run();
			}
		});
    }
 
}

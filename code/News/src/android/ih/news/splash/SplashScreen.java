package android.ih.news.splash;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.ih.help.HelpFragmentActivity;
import android.ih.news.ArticleListFragment.ArticleAdapter;
import android.ih.news.GetMainPageTask;
import android.ih.news.R;
import android.ih.news.SetNewsFlashTask;
import android.ih.news.SetTreeCategoriesTask;
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.AnnotatedImage;
import android.ih.news.model.AnnotatedImage.ImageSize;
import android.ih.news.model.Article;
import android.ih.piemenu.PieMenu;
import android.ih.piemenu.TestPieMenuItem;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
 
public class SplashScreen extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    
    private Handler myHandler;
    
    private Runnable myRun = new Runnable() {
    	 
        /*
         * Showing splash screen with a timer.
         */

        @Override
        public void run() {
        	loadHomePageArticles();
        	
            // This method will be executed once the timer is over
            // Start your app main activity
            Intent i = new Intent(SplashScreen.this, HelpFragmentActivity.class);
            startActivity(i);

            // close this activity
            finish();
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new SetNewsFlashTask().execute();
        
        TestPieMenuItem root = new TestPieMenuItem();
		root.setResources(getResources());
		root.setImage(new AnnotatedImage("img1", "local", 
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), ImageSize.PIE));
		PieMenu.getMenu().getRoot().setData(root);
		
        
        myHandler = new Handler();
        myHandler.postDelayed(myRun, SPLASH_TIME_OUT);
    }
    
    
    /**
     * Starts loading homepage articles and waits until the task is completed
     */
    private void loadHomePageArticles() {
    	AsyncTask<ArticleAdapter, Void, List<Article>> setTask = new GetMainPageTask().execute();
    	
		try {
			setTask.get(); // TODO: this is waiting until the task is completed and blocks the UI
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

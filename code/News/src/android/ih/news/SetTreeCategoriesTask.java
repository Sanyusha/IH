package android.ih.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.AnnotatedImage;
import android.ih.news.model.Category;
import android.ih.news.model.AnnotatedImage.ImageSize;
import android.ih.piemenu.BasicTree;
import android.ih.piemenu.PieMenuItem;
import android.ih.piemenu.BasicTree.Node;
import android.ih.piemenu.TestPieMenuItem;
import android.os.AsyncTask;
import android.util.Log;


public class SetTreeCategoriesTask extends AsyncTask<BasicTree<PieMenuItem>, Void, List<Category>> {
	
	//TODO: add cache
	BasicTree<PieMenuItem> menu = null;

    @Override
    protected List<Category> doInBackground(BasicTree<PieMenuItem>... menu) {
    	this.menu = menu[0];
    	return IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getAllCategories();
    }

    @Override
    protected void onPostExecute(List<Category> result) {
    	if(result == null){
    		Log.d("lilach", "result null");
    	}
    	
    	// add children
    	synchronized (menu) {
    		TestPieMenuItem root = (TestPieMenuItem) menu.getRoot().getData();
    		Resources resources = root.getResources();
    		List<Node<PieMenuItem>> children = new ArrayList<Node<PieMenuItem>>();
    		TestPieMenuItem catInNode2 = new TestPieMenuItem();
    		catInNode2.setTitle("Sokol");
    		catInNode2.setResources(resources);
    		catInNode2.setImage(new AnnotatedImage("img1", "local", 
					BitmapFactory.decodeResource(resources, R.drawable.graph), ImageSize.PIE));
    		Node<PieMenuItem> node2 = new Node<PieMenuItem>(catInNode2);
		    children.add(node2);
    		
    		for (Category category : result) {
    			TestPieMenuItem catInNode = new TestPieMenuItem();
    			catInNode.setTitle(category.getName());
    			catInNode.setResources(resources);
    			
    			// TODO: put image for category
    			Random rand = new Random();
    		    // nextInt is normally exclusive of the top value,
    		    // so add 1 to make it inclusive
    		    int randomNum = rand.nextInt((10 - 1) + 1) + 1;
    		    Log.d("setTree", "randomNum:::" + randomNum);
    		    if (randomNum % 2 == 0) {
    		    	catInNode.setImage(new AnnotatedImage("img1", "local", 
    					BitmapFactory.decodeResource(catInNode.getResources(), R.drawable.globe), ImageSize.PIE));
    		    }
    		    
    			Node<PieMenuItem> node = new Node<PieMenuItem>(catInNode);
    		    children.add(node);
			}
    		menu.getRoot().setChildren(children);
		}
    		
		// TODO: change number of categories?
		for (int i = 0; i < 20 && i < menu.getRoot().getChildren().size(); i++) {
			new SetTreeSingleCategoryTask().execute(menu.getRoot().getChildren().get(i));				
		}
    }
}

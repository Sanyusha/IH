package android.ih.news;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.AnnotatedImage;
import android.ih.news.model.Category;
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
    		for (Category category : result) {
    			TestPieMenuItem catInNode = new TestPieMenuItem();
    			catInNode.setTitle(category.getName());
    			// TODO: put image for category
    			catInNode.setImage(new AnnotatedImage("img1", "local", 
    					BitmapFactory.decodeResource(resources, R.drawable.globe)));
    			Node<PieMenuItem> node = new Node<PieMenuItem>(catInNode);
				children.add(node);
			}
    		menu.getRoot().setChildren(children);
		}
    }
}

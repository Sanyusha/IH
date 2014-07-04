package android.ih.news;

import java.util.ArrayList;
import java.util.List;

import android.ih.news.api.IHAPIWrapper;
import android.ih.news.model.Article;
import android.ih.piemenu.BasicTree.Node;
import android.ih.piemenu.PieMenuItem;
import android.ih.piemenu.TestPieMenuItem;
import android.os.AsyncTask;
import android.util.Log;


public class SetTreeSingleCategoryTask extends AsyncTask<Node<PieMenuItem>, Void, List<Article>> {
	
	Node<PieMenuItem> menuCat = null;

    @Override
    protected List<Article> doInBackground(Node<PieMenuItem>... menu) {
    	this.menuCat = menu[0];
    	return IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getCategoryArticles(this.menuCat.getData().getTitle(), 0, 3, true);
    }

    @Override
    protected void onPostExecute(List<Article> result) {
    	if(result == null){
    		Log.d("lilach", "result null");
    		// TODO: remove category from tree if no articles found    			
    	}
    	
    	// add children
    	List<Node<PieMenuItem>> children = new ArrayList<Node<PieMenuItem>>();
    	synchronized (menuCat) {
    		for (Article article : result) {
    			TestPieMenuItem catInNode = new TestPieMenuItem();
    			catInNode.setTitle(article.getTitle());
    			catInNode.setResources(((TestPieMenuItem)menuCat.getData()).getResources());
    			if (article.getImages() != null && article.getImages().size() > 0) {
    				catInNode.setImage(article.getImages().get(0));
    			}
    			catInNode.setURL(article.getMobileUrl());
    			catInNode.setId(article.getId());
    			
    			Node<PieMenuItem> node = new Node<PieMenuItem>(catInNode);
				children.add(node);
			}
    		
    		menuCat.setChildren(children);
		}
    	
    	for (Node<PieMenuItem> node : children) {
    		new DownloadImagesTaskForNode().execute(node);
		}
    	new GetCategoryForCacheTask().executeOnExecutor(IHAPIWrapper.getInstance("http://api.app.israelhayom.co.il/", "nas987nh34", false).getCategoryArticleExecutor(), this.menuCat.getData().getTitle());
    }
}

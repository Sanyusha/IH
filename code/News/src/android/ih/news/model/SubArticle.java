package android.ih.news.model;

import java.util.List;
import java.util.UUID;

import android.ih.news.DownloadImagesTask;
import android.ih.news.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SubArticle extends Article {
	
	public SubArticle(UUID articleId, String title, String fullText, List<AnnotatedImage> images) {
		super(articleId, title, fullText, images);
	}
	
	public SubArticle() {
		super(UUID.randomUUID(), null, null, null);
	}

	public View getView(LayoutInflater inflater, View convertView)
	 {
	    	//if(convertView == null){
				convertView = (View) inflater.inflate(R.layout.list_item_article, null);
			//}
			
			//Article article = getItem(position);
			
//			Button supposedToBePicture = (Button)convertView.findViewById(R.id.supposed_to_be_picture);
//			supposedToBePicture.setText("picture");

			ImageView articleImageView;
			articleImageView = (ImageView)convertView.findViewById(R.id.list_item_imageView);
			
			if (this.getImages() != null && this.getImages().size() > 0) {
				articleImageView.setTag(this.getImages().get(0).getUrl());
				new DownloadImagesTask().execute(articleImageView);
			}
			
			//articleImageView.setImageResource(R.drawable.images_logo2_he);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.article_list_item_titleTextView);
			titleTextView.setText(this.getTitle());
			
			TextView summaryTextView = (TextView)convertView.findViewById(R.id.article_list_item_summaryTextView);
			summaryTextView.setText(this.getSummary());
				
			return convertView;
	    }
}


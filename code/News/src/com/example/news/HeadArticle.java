package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HeadArticle extends Article {
	
	public View getView(LayoutInflater inflater, View convertView)
	 {
	    	//if(convertView == null){
				convertView = (View) inflater.inflate(R.layout.list_first_item_article, null);
			//}
			
			//Article article = getItem(position);
			
//			Button supposedToBePicture = (Button)convertView.findViewById(R.id.supposed_to_be_picture);
//			supposedToBePicture.setText("picture");

			ImageView articleImageView;
			articleImageView = (ImageView)convertView.findViewById(R.id.list_item_imageView);
			
			articleImageView.setTag(this.mImgLink);
			new DownloadImagesTask().execute(articleImageView);
			
			//articleImageView.setImageResource(R.drawable.images_logo2_he);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.article_list_item_titleTextView);
			titleTextView.setText(this.mTitle);
			
			TextView summaryTextView = (TextView)convertView.findViewById(R.id.article_list_item_summaryTextView);
			summaryTextView.setText(this.mSummary);
			
						
			return convertView;
	    }
}

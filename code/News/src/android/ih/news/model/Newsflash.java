package android.ih.news.model;

import java.util.List;
import java.util.UUID;

import android.view.LayoutInflater;
import android.view.View;

public class Newsflash extends Article {
	
	public Newsflash(UUID id, String title, String fullText, List<AnnotatedImage> images) {
		super(id, title, fullText, images);
	}
	
	public Newsflash() {
		super(UUID.randomUUID(), null, null, null);
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		// TODO Do we need this for news flash?
		return null;
	}
}

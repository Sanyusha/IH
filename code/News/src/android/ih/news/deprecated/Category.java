/*package android.ih.news.deprecated;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.news.R;

import android.ih.news.RowType;
import android.ih.news.model.Item;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Category implements Item {
	String name;
	
	public Category(String name) {
		this.name = name;
	}
	
	public View getView(LayoutInflater inflater, View convertView)
	 {
	    	//if(convertView == null){
				convertView = (View) inflater.inflate(R.layout.list_item_article, null);
			//}
			
			
			
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.category_text);
			titleTextView.setText(this.name);
				
			return convertView;
	    }

	@Override
	public int getViewType() {
		return RowType.CATEGORY_ITEM.ordinal();
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
}

*/
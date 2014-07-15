package android.ih.news.model;

import android.view.LayoutInflater;
import android.view.View;

public interface Item {
    //public int getViewType();
    
    public View getView(LayoutInflater inflater, View convertView);
    
    //public JSONObject toJSON() throws JSONException;
}

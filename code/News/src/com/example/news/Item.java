package com.example.news;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;

public interface Item {
    public int getViewType();
    
    public View getView(LayoutInflater inflater, View convertView);
    
    public JSONObject toJSON() throws JSONException;
}

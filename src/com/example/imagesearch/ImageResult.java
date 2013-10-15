package com.example.imagesearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable {

	private static final long serialVersionUID = -2909990026664183888L;
	private String fullUrl;
	private String thumbUrl;
	
	public ImageResult(JSONObject json) {
		try {
			fullUrl = json.getString("url");
			thumbUrl = json.getString("tbUrl");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String  getFullUrl() {
		return fullUrl;
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	@Override
	public String toString() {
		return thumbUrl;
	}
	
	public static List<ImageResult> fromJSONArray(JSONArray json) {
	    List<ImageResult> imageResults = new ArrayList<ImageResult>();	
	    for(int i=0;i<json.length();i++) {
	    	try {
				imageResults.add(new ImageResult((JSONObject)json.get(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    return imageResults;
	}
	
}

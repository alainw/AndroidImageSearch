package com.example.imagesearch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {

	public final int REQ_OPTIONS_OK = 2;
	public final int MAX_PER_REQUEST = 8;
	
	private final List<ImageResult> imageResultList = new ArrayList<ImageResult>();
	private final AsyncHttpClient httpClient = new AsyncHttpClient();
	// Reset the listener loading status if we do a new search in case not getting an expected reply
	private final EndlessScrollListener scrollListener = new EndlessScrollListener() {
		
		@Override
		public void loadMore(int page, int totalItemsCount) {
		    doQuery(totalItemsCount);	
			
		}	
	};;
	
	private EditText etQuery;
	private GridView gvResults;
	private ImageResultArrayAdapter imageResultArrayAdapter; 
	private SearchOptions searchOptions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setupViews();
		searchOptions = new SearchOptions();
		imageResultArrayAdapter = new ImageResultArrayAdapter(this, imageResultList);
		gvResults.setAdapter(imageResultArrayAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
			    Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
			    ImageResult imageResult = imageResultList.get(position);
			    i.putExtra("result", imageResult);
			    startActivity(i);
			}	
		});

		gvResults.setOnScrollListener(scrollListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}

	private void setupViews() {
		etQuery = (EditText)findViewById(R.id.etQuery);
		gvResults = (GridView)findViewById(R.id.gvResults);
	}
	
	public void onImageSearch(View v) {
    	imageResultArrayAdapter.clear();
    	scrollListener.setLoading(false);
		doQuery(0);
	}
	
	private void doQuery(int pos) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
		String formattedQuery = getFormattedQuery(query, pos);
		Log.d("DEBUG", "Pos is: " + pos + " Query is: " + formattedQuery); 
		httpClient.get(formattedQuery, 
				new JsonHttpResponseHandler() {
			        @Override
			        public void onSuccess(JSONObject response) {
						try {
				        	JSONArray results = response.getJSONObject("responseData").getJSONArray("results");
				        	List<ImageResult> resultsList = ImageResult.fromJSONArray(results);
				            imageResultArrayAdapter.addAll(resultsList);
						} catch (JSONException e) {
							e.printStackTrace();
						} 
			        }
			        @Override 
			        public void onFailure(Throwable e, JSONObject response) {	
			            Log.e("FAILED", "Failure: " + e.getMessage());	
			        }
		  		}
				);	
	}
	
	public void onClickSettings(MenuItem mi) {
	    Intent i = new Intent(getApplicationContext(), SearchOptionsActivity.class);
	    i.putExtra("searchOptions", searchOptions);
	    startActivityForResult(i, REQ_OPTIONS_OK);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK && requestCode == REQ_OPTIONS_OK) {
		     Toast.makeText(this, data.getSerializableExtra("searchOptions").toString(),
		        Toast.LENGTH_SHORT).show();
		     searchOptions = (SearchOptions) data.getSerializableExtra("searchOptions");
	    }
	}
	
	private String getFormattedQuery(String query, int pos) {
		StringBuilder builder = new StringBuilder();
		builder.append("https://ajax.googleapis.com/ajax/services/search/images?rsz="+ MAX_PER_REQUEST +"&start=");
		builder.append(pos);
		builder.append("&v=1.0&q=");
		builder.append(Uri.encode(query));
		// If site filter is specified, can't specify other options
		if (searchOptions.getSiteFilter() != null && !searchOptions.getSiteFilter().isEmpty()) {
			//builder.append("&as_lq=");
			builder.append("link%3A");
            builder.append(Uri.encode(searchOptions.getSiteFilter()));
		} else {	
			if (searchOptions.getImageSize() != null && !searchOptions.getImageSize().isEmpty()) {
	            builder.append("&imgsz=");
	            builder.append(Uri.encode(searchOptions.getImageSize()));
			}	
			if (searchOptions.getImageColor() != null && !searchOptions.getImageColor().isEmpty()) {
	            builder.append("&imgcolor=");
	            builder.append(Uri.encode(searchOptions.getImageColor()));
			}	
			if (searchOptions.getImageType() != null && !searchOptions.getImageType().isEmpty()) {
	            builder.append("&imgtype=");
	            builder.append(Uri.encode(searchOptions.getImageType()));
			}	
		}
        return builder.toString();
	}
	
}

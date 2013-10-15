package com.example.imagesearch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchOptionsActivity extends Activity {

	private Spinner spImageSize;
	private Spinner spImageColor;
	private Spinner spImageType;
	private EditText etSiteFilter;
	private SearchOptions searchOptions;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		spImageColor = (Spinner) findViewById(R.id.spImageColor);
		spImageType = (Spinner) findViewById(R.id.spImageType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		searchOptions = (SearchOptions) getIntent().getSerializableExtra("searchOptions");
		if (searchOptions != null) {
			if (searchOptions.getImageSize() != null) {
			    spImageSize.setSelection(searchOptions.getImageSizePos());	
			}
			if (searchOptions.getImageColor() != null) {
				spImageColor.setSelection(searchOptions.getImageColorPos());	
			}
			if (searchOptions.getImageType() != null) {
				spImageType.setSelection(searchOptions.getImageTypePos());	
			}
			if (searchOptions.getSiteFilter() != null) {
			    etSiteFilter.setText(searchOptions.getSiteFilter());	
			}			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
		return true;
	}

	public void onSave(View v) {	
	    SearchOptions searchOptions = new SearchOptions();
	    searchOptions.setImageSize(spImageSize.getSelectedItem().toString(), spImageSize.getSelectedItemPosition());
	    searchOptions.setImageColor(spImageColor.getSelectedItem().toString(), spImageColor.getSelectedItemPosition());
	    searchOptions.setImageType(spImageType.getSelectedItem().toString(), spImageType.getSelectedItemPosition());
	    searchOptions.setSiteFilter(etSiteFilter.getText().toString());
	    
	    Intent i = new Intent();
	    i.putExtra("searchOptions", searchOptions);
	    setResult(RESULT_OK, i);
	    finish();
	}
	
}

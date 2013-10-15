package com.example.imagesearch;

import java.util.concurrent.atomic.AtomicBoolean;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {

	private int visibleThreshold = 3;
	private int currentPage = 0;
	private int previousTotalItemCount = 0;
	private AtomicBoolean loading = new AtomicBoolean(true);
	private final int startingPageIndex = 0;
	
	public EndlessScrollListener() {		
	}
	
	public EndlessScrollListener(int visibleThreshold) {
		this.visibleThreshold = visibleThreshold;
	}	
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		Log.d("ON SCROLL", "Loading: " + loading + "on scroll entered first visi: " + firstVisibleItem + " visi item count: " + visibleItemCount + " total item count: " + totalItemCount);
	    if (!loading.get() && totalItemCount < previousTotalItemCount) {
	    	this.currentPage = this.startingPageIndex;
	    	this.previousTotalItemCount = totalItemCount;
	    	this.loading.set(true);
	    }	    
	    if (loading.get()) {
	    	if (totalItemCount > previousTotalItemCount) {
	    		loading.set(false);
	    		previousTotalItemCount = totalItemCount;
	    		currentPage++;
	    	}
	    }
	    if (!loading.get() && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
	        loadMore(currentPage + 1, totalItemCount);
	        loading.set(true);
	    }
	}
	
	public abstract void loadMore(int page, int totalItemsCount);

	public void setLoading(boolean status) {
		loading.set(status);
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
	
}

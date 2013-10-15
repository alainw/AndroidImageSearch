package com.example.imagesearch;

import java.io.Serializable;

public class SearchOptions implements Serializable {

	private static final long serialVersionUID = 2315173747593607434L;
	private String imageSize;
	private int imageSizePos;
	private String imageColor;
	private int imageColorPos;
    private String imageType;
    private int imageTypePos;
    private String siteFilter;
	
    public SearchOptions() {}
    
    public String getImageSize() {
    	return imageSize;
    }

    public int getImageSizePos() {
        return imageSizePos;	
    }
    
    public void setImageSize(String imageSize, int pos) {
    	this.imageSize = imageSize;
    	this.imageSizePos = pos;
    }
    
    public String getImageColor() {
    	return imageColor;
    }

    public int getImageColorPos() {
        return imageColorPos;	
    }
    
    public void setImageColor(String imageColor, int pos) {
    	this.imageColor = imageColor;
    	this.imageColorPos = pos;
    }
    
    public String getImageType() {
    	return imageType;
    }

    public int getImageTypePos() {
        return imageTypePos;	
    }
    
    public void setImageType(String imageType, int pos) {
    	this.imageType = imageType;
    	this.imageTypePos = pos;
    }
    
    public String getSiteFilter() {
    	return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
    	this.siteFilter = siteFilter;
    }
    
    @Override
    public String toString() {
        return  "Size: " + nonNullAttr(imageSize) + 
        	   " Color: " + nonNullAttr(imageColor) + 
           	   " Type: " + nonNullAttr(imageType) +
        	   " Site Filter: " + nonNullAttr(siteFilter);	
    }
    
    private String nonNullAttr(String attr) {
    	return attr==null?"null":attr;
    }
    
}

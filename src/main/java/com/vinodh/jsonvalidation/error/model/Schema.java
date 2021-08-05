package com.vinodh.jsonvalidation.error.model;

public class Schema {
	
	private String loadingURI;
    private String pointer;
    
	public String getLoadingURI() {
		return loadingURI;
	}
	public void setLoadingURI(String loadingURI) {
		this.loadingURI = loadingURI;
	}
	public String getPointer() {
		return pointer;
	}
	public void setPointer(String pointer) {
		this.pointer = pointer;
	}
	
	@Override
	public String toString() {
		return "Schema [loadingURI=" + loadingURI + ", pointer=" + pointer + "]";
	}
    
}

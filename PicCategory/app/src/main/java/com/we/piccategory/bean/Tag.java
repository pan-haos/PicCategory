package com.we.piccategory.bean;


public class Tag{
	private String type;
	private int tag_confidence;
	private String tag_name;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTag_confidence() {
		return tag_confidence;
	}
	public void setTag_confidence(int tag_confidence) {
		this.tag_confidence = tag_confidence;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	
}

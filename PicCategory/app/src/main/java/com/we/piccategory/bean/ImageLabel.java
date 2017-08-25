package com.we.piccategory.bean;

public class ImageLabel {
    private String imageUrl;
    private String label;


    public ImageLabel() {
    }

    public ImageLabel(String imageUrl, String label) {
        super();
        this.imageUrl = imageUrl;
        this.label = label;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

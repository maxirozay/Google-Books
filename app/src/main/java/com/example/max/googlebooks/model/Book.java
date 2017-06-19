package com.example.max.googlebooks.model;

/**
 * Created by max on 6/15/17.
 */

public class Book {

    private String kind;
    private String id;
    private String etag;
    private String selfLink;
    private VolumeInfo volumeInfo;
    private AccessInfo accessInfo;
    private boolean hasDetails = false;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public String getCoverThumbnailLink(){
        if (volumeInfo.getImageLinks() != null) return volumeInfo.getImageLinks()
                .get("smallThumbnail");
        return null;
    }

    public String getCoverImageLink(){
        if (volumeInfo.getImageLinks() != null) return volumeInfo.getImageLinks()
                .get("smallThumbnail").replace("zoom=5", "zoom=2");
        return null;
    }

    public boolean hasDetails() {
        return hasDetails;
    }

    public void setHasDetails(boolean hasDetails) {
        this.hasDetails = hasDetails;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }
}

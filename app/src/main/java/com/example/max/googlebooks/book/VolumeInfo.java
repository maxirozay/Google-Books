package com.example.max.googlebooks.book;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.max.googlebooks.util.Util;

import java.util.List;
import java.util.Map;

/**
 * Created by max on 6/15/17.
 */

public class VolumeInfo extends BaseObservable {

    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private int printedPageCount;
    private List<String> categories;
    private Map<String, String> imageLinks;
    private float averageRating;
    private int ratingsCount;
    private String language;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getAuthors() {
        if (authors == null) return null;
        String authorsString = "";
        for (String author : authors)
            authorsString += author + ", ";
        return authorsString.substring(0, authorsString.length() - 2);
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @Bindable
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Bindable
    public String getPublishedDate() {
        return Util.formatDate(publishedDate);
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Bindable
    public String getDescription() {
        if (description == null) return "";
        return Util.htmlToText(description);
    }

    public void setDescription(String description) { this.description = description; }

    @Bindable
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Bindable
    public int getPrintedPageCount() {
        return printedPageCount;
    }

    public void setPrintedPageCount(int printedPageCount) {
        this.printedPageCount = printedPageCount;
    }

    @Bindable
    public String getCategories() {
        if (categories == null) return null;
        String categoriesString = "";
        for (String category : categories)
            categoriesString += category + "\n";
        return categoriesString;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Map<String, String> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(Map<String, String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    @Bindable
    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    @Bindable
    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    @Bindable
    public String getLanguage() {
        return Util.languageCodeToName(language);
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}

package com.example.max.googlebooks.model;

import android.text.Html;
import android.util.Log;

import com.example.max.googlebooks.data.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by max on 6/15/17.
 */

public class VolumeInfo {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        if (authors == null) return new ArrayList<>();
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return Util.formatDate(publishedDate);
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        if (description == null) return "";
        return Util.htmlToText(description);
    }

    public void setDescription(String description) { this.description = description; }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPrintedPageCount() {
        return printedPageCount;
    }

    public void setPrintedPageCount(int printedPageCount) {
        this.printedPageCount = printedPageCount;
    }

    public List<String> getCategories() {
        if (categories == null) return new ArrayList<>();
        return categories;
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

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public String getLanguage() {
        return Util.languageCodeToName(language);
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}

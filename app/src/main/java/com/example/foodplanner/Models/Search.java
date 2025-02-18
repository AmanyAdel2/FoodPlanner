package com.example.foodplanner.Models;

import java.io.Serializable;

public class Search implements Serializable {
    String searchName;
    String searchImage;

    public Search(){}

    public Search(String searchName, String searchImage) {
        this.searchName = searchName;
        this.searchImage = searchImage;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchImage() {
        return searchImage;
    }

    public void setSearchImage(String searchImage) {
        this.searchImage = searchImage;
    }
}

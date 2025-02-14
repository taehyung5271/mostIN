package com.example.mostin;

import java.util.List;

public class MenuItems {
    private String title;
    private List<String> subItems;

    public MenuItems(String title, List<String> subItems) {
        this.title = title;
        this.subItems = subItems;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getSubItems() {
        return subItems;
    }
}

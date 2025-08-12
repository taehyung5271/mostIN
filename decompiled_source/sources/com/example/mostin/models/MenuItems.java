package com.example.mostin.models;

import java.util.List;

/* loaded from: classes6.dex */
public class MenuItems {
    private List<String> subItems;
    private String title;

    public MenuItems(String title, List<String> subItems) {
        this.title = title;
        this.subItems = subItems;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getSubItems() {
        return this.subItems;
    }
}

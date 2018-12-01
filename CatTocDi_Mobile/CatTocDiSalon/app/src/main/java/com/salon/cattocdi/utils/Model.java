package com.salon.cattocdi.utils;

import java.util.HashMap;
import java.util.List;

public class Model {
    private String item;
    private int position;
    private List<String> services;
    private HashMap<String, List<String>> categories;

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public HashMap<String, List<String>> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, List<String>> categories) {
        this.categories = categories;
    }

    public Model(String item) {
        this.item = item;
    }

    public Model() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    /**
     * the position of the item in the list
     */


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private boolean isChecked;

public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}


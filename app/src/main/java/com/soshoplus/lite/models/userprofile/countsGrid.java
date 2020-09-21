/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.userprofile;

public class countsGrid {
    private int icon;
    private String count;
    private String count_info;

    public countsGrid() {

    }

    public countsGrid(int icon, String count, String count_info) {
        this.icon = icon;
        this.count = count;
        this.count_info = count_info;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount_info() {
        return count_info;
    }

    public void setCount_info(String count_info) {
        this.count_info = count_info;
    }
}

/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.userprofile;

public class infoList {
    private int icon;
    private String title;
    private String info;
    
    public infoList () {
    
    }
    
    public infoList (int icon, String title, String info) {
        this.icon = icon;
        this.title = title;
        this.info = info;
    }
    
    public int getIcon () {
        return icon;
    }
    
    public void setIcon (int icon) {
        this.icon = icon;
    }
    
    public String getTitle () {
        return title;
    }
    
    public void setTitle (String title) {
        this.title = title;
    }
    
    public String getInfo () {
        return info;
    }
    
    public void setInfo (String info) {
        this.info = info;
    }
}

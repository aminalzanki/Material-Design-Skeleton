package com.aminalzanki.materialskeleton.ui;

/**
 * Created by nikmuhammad on 6/21/15.
 */
public class Items
{
    private String title;
    private int    icon;

    public Items(final String title, final int icon)
    {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }
}

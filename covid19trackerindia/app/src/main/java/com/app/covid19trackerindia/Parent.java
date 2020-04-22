package com.app.covid19trackerindia;

import java.util.ArrayList;

public class Parent
{
    private String state;
    private String confirmed;
    private String active;
    private String recovered;
    private String deaths;

    // ArrayList to store child objects
    private ArrayList<Child> children;

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
    public String getConfirmed()
    {
        return confirmed;
    }

    public void setConfirmed(String confirmed)
    {
        this.confirmed = confirmed;
    }

    public String getActive()
    {
        return active;
    }

    public void setActive(String active)
    {
        this.active = active;
    }
    public String getRecovered()
    {
        return recovered;
    }

    public void setRecovered(String recovered)
    {
        this.recovered = recovered;
    }


    public String getDeaths()
    {
        return deaths;
    }
    public void setDeaths(String deaths)
    {
        this.deaths = deaths;
    }

    // ArrayList to store child objects
    public ArrayList<Child> getChildren()
    {
        return children;
    }

    public void setChildren(ArrayList<Child> children)
    {
        this.children = children;
    }
}

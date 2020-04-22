package com.app.covid19trackerindia;

public class Child
{
    private String district;

 /*   private String active;
    private String recovered;
    private String deaths;
*/
 private String confirmed;
    Child(){
        district="Unknown";
        confirmed="0";
    }

    // ArrayList to store child objects


    public String getdistrict()
    {
        return district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }
    public String getConfirmed(){return confirmed;}
    public void setConfirmed(String confirmed){
        this.confirmed=confirmed;
    }


   /* public String getActive()
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
*/
}
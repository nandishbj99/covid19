package com.app.covid19trackerindia;

public class statedata {
    String state;
    String confirmed;
    String recovered;
    String deaths;
    String active;
    public statedata(){

    }
    public statedata(String state, String confirmed, String recovered, String deaths, String active){
        this.active=active;
        this.confirmed=confirmed;
        this.deaths=deaths;
        this.recovered=recovered;
        this.state=state;
    }
    public String getState(){
        return state;
    }
    public  String getConfirmed(){return confirmed;}
public String getRecovered(){
        return recovered;

}
public String getDeaths(){
        return deaths;
}
public String getActive(){
        return active;
}
public void setState(String state){
        this.state=state;
}
public void setConfirmed(String confirmed){
        this.confirmed=confirmed;
}
public void setRecovered(String recovered){
        this.recovered=recovered;
}
public void setDeaths(String deaths){
        this.deaths=deaths;
}
public  void setActive(String active){
        this.active=active;
}
}

package com.example.quaakereport;

public class Earthquake {

    private Double mMagnitude;
    private String mLocation;
    private String mDate;
    private String murl;

    public Earthquake(Double magnitude,String location, String date,String url)
    {
        mMagnitude=magnitude;
        mLocation=location;
        mDate=date;
        murl=url;
    }
    public Double getMagnitude(){return mMagnitude;}
    public String getLocation(){return mLocation;}
    public String getDate(){ return mDate;}
    public String getUrl(){return murl;};

}

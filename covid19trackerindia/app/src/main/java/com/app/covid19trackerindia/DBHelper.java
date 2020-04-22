package com.app.covid19trackerindia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "covid19.db";
    public static final String TABLE_NAME = "india";





    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table india " +
                        "(id String primary key , reportedon text,city text,district text,state text,status text,notes text)"
        );
        db.execSQL(
                "create table statedata " +
                        "(state String primary key , confirmed text,active text,recovered text,death text)"
        );
        db.execSQL(
                "create table country " +
                        "(countryname String primary key , confirmed text,recovered text,deaths text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
    public void dropandcreate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS india");

        db.execSQL(
                "create table india " +
                        "(id String primary key , reportedon text,city text,district text,state text,status text,notes text)"
        );

    }
    public void dropandcreatestate(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS statedata");
        db.execSQL(
                "create table statedata " +
                        "(state String primary key , confirmed text,active text,recovered text,death text)"
        );
    }
    public void dropandcreatecountry() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS country");

        db.execSQL(
                "create table country " +
                        "(countryname String primary key , confirmed text,recovered text,deaths text)"
        );
    }

        public boolean insertData (String id,String reportedon,String city,String district,String state,String status,String notes) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);

        contentValues.put("reportedon", reportedon);
     contentValues.put("city", city);
        contentValues.put("district", district);
        contentValues.put("state", state);
        contentValues.put("status", status);
        contentValues.put("notes",notes);


        db.insert("india", null, contentValues);
        return true;
    }
    public boolean insertStateData (String statename,String confirmed,String active,String recovered,String death) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("state", statename);

        contentValues.put("confirmed", confirmed);
        contentValues.put("active", active);
        contentValues.put("recovered", recovered);
        contentValues.put("death", death);
        //db.update("statedata",contentValues,"state="+statename,null);



        db.insert("statedata", null, contentValues);
        return true;
    }
    public boolean insertCountryData (String country,String confirmed,String recovered,String death) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("countryname", country);

        contentValues.put("confirmed", confirmed);

        contentValues.put("recovered", recovered);
        contentValues.put("deaths", death);
        //db.update("statedata",contentValues,"state="+statename,null);



        db.insert("country", null, contentValues);
        return true;
    }

    public Cursor getcountData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(id) from india", null );
               // Cursor res =  db.rawQuery( "select * from india", null );
        return res;
    }
    public Cursor getAllData(String state) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select district,count(*) from india where state='"+state+"' group by district order by 2 desc", null );
        // Cursor res =  db.rawQuery( "select * from india", null );
        return res;
    }
    public Cursor getDistrictData(String district,String state){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from india where district='"+district+"' and state='"+state+"'", null );
        // Cursor res =  db.rawQuery( "select * from india", null );
        return res;

    }


    public Cursor getconfirmincbydistrict(String date,String state) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(district),district,state from india where reportedon='"+date+"' and state='"+state+"' group by district", null );
        // Cursor res =  db.rawQuery( "select * from india", null );
        return res;
    }

    public Cursor getstatuscountdate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(id) from india where reportedon='"+date+"' ", null );
        // Cursor res =  db.rawQuery( "select * from india", null );
        return res;
    }
    public Cursor getstatuscountstate(String date,String state) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(id) from india where reportedon='"+date+"' and state='"+state+"'", null );
        // Cursor res =  db.rawQuery( "select * from india", null );
        return res;
    }
    public Cursor getstatuscount(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(id) from india where status='"+status+"' ", null );
        // Cursor res =  db.rawQuery( "select * from india", null );
        return res;
    }
    public Cursor getdatastatewise(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(id),state from india where status='"+status+"' group by state; ", null );
        // Cursor res =  db.rawQuery( "select * from india", null );
        return res;
    }
    public Cursor getdatastatewise() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from statedata; ", null );
        // Cursor res =  db.rawQuery( "select * from india", null );
        return res;
    }


    public Cursor getcountrydata(String Country) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        if(Country.equals("All")){
      res =  db.rawQuery( "select * from country ; ", null );

        }
        else {
            res = db.rawQuery("select * from country where countryname='" + Country + "'; ", null);
        } // Cursor res =  db.rawQuery( "select * from india", null );

        return res;
    }
    public Cursor getcountryname() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;

            res = db.rawQuery("select countryname from country order by countryname asc ; ", null);


        return res;
    }








}
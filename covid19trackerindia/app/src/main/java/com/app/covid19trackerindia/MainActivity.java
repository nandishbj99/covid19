package com.app.covid19trackerindia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private static int confirmedcase=0,recoveredcase=0,activecase=0,deceasedcase=0;
    DBHelper db=new DBHelper(MainActivity.this);
    private static JSONArray statewisedata;
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();


    // URL to get contacts JSON
    private static String url1 = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org";
    private static String url2 = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new GetindiaReport().execute();
    }
    public static Integer getconfirmedcase(){
        return  confirmedcase;
    }
    public static Integer getactive(){
        return activecase;
    }
    public static Integer getrecovered(){
        return recoveredcase;
    }
    public static Integer getdeaths(){
        return deceasedcase;
    }


    private class GetindiaReport extends AsyncTask<Void, Void, Void> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url1);
            String jsonStr1=sh.makeServiceCall(url2);




            Log.e(TAG, "Response from url: " + jsonStr);


            if (jsonStr != null) {
                int i=0;
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject dataobject=jsonObj.getJSONObject("data");
                    JSONArray patientdata = dataobject.getJSONArray("rawPatientData");
                    String id;




                    db.dropandcreate();
                    for (i =0; i < patientdata.length(); i++) {
                        // if(i==4374){
                        JSONObject c = patientdata.getJSONObject(i);
                        Iterator<String> iter=c.keys();
                        PatientData p=new PatientData();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            String value = c.getString(key);
                            p.set(key,value);

                        }


                        db.insertData(p.getId(), p.getReportedOn(), p.getCity(), p.getDistrict(), p.getState(), p.getStatus(), p.getNotes());








                    }




                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage()+"i:"+i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"loading error",Toast.LENGTH_LONG).show();

                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"Couldn't get json from server.",Toast.LENGTH_LONG).show();
                    }
                });

            }
            if (jsonStr1 != null) {
                try {
                    JSONObject jsonObj1 = new JSONObject(jsonStr1);
                    JSONArray statewisedata;

                    JSONObject dataobj=jsonObj1.getJSONObject("data");
                    JSONObject totalobj = dataobj.getJSONObject("total");
                    confirmedcase=totalobj.getInt("confirmed");
                    recoveredcase=totalobj.getInt("recovered");
                    deceasedcase=totalobj.getInt("deaths");
                    activecase=totalobj.getInt("active");




                    statewisedata=dataobj.getJSONArray("statewise");
                    db.dropandcreatestate();
                    for(int i=0;i<statewisedata.length();i++){
                        JSONObject j=statewisedata.getJSONObject(i);
                        statedata s=new statedata();
                        db.insertStateData(j.getString("state"),j.getString("confirmed"),j.getString("recovered"),j.getString("deaths"),j.getString("active"));
                    }












                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            Intent intent=new Intent(MainActivity.this,MainActivity1.class);
            startActivity(intent);











        }

    }


}

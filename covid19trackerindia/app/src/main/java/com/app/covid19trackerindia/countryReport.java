package com.app.covid19trackerindia;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class countryReport extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    Toolbar t1;
    Spinner spinner;

    private ProgressDialog pDialog;
    private ListView lv;


    private static String url = "https://covid2019-api.herokuapp.com/current";

    ArrayList<HashMap<String, String>> datalist;
    List<String> countrylist;

    DBHelper db = new DBHelper(countryReport.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_report);
        t1=(Toolbar)findViewById(R.id.country_report_toolbar);
        t1.setTitle("Country Report");

        countrylist=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.spinner);

        datalist= new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetReport().execute();
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }


    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            datalist.clear();
            Cursor cursor=db.getcountrydata(parent.getItemAtPosition(pos).toString());
            if(cursor.moveToFirst()){
                do{
                    HashMap<String, String> country_list = new HashMap<>();


                    country_list.put("country", cursor.getString(0));
                    country_list.put("confirmed", cursor.getString(1));
                    country_list.put("recovered", cursor.getString(2));
                    country_list.put("deaths", cursor.getString(3));


                    datalist.add(country_list);
                }while (cursor.moveToNext());
            }
            ListAdapter adapter = new SimpleAdapter(
                    countryReport.this, datalist,
                    R.layout.list_item_country_report, new String[]{"country","confirmed","recovered","deaths"
            }, new int[]{R.id.country_name
                    ,R.id.confirm_case_d, R.id.recovered_d,R.id.death_d});

            lv.setAdapter(adapter);

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    private class GetReport extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(countryReport.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            db.dropandcreatecountry();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Iterator iterator = jsonObj.keys();
                    while (iterator.hasNext()) {
                        String country = (String) iterator.next();
                        JSONObject c = jsonObj.getJSONObject(country);

                        String confirm_case = c.getString("confirmed");
                        String recovered = c.getString("recovered");
                        String deaths = c.getString("deaths");
                        db.insertCountryData(country,confirm_case,recovered,deaths);






                    HashMap<String, String> country_list = new HashMap<>();


                    country_list.put("country", country);
                    country_list.put("confirmed", confirm_case);
                    country_list.put("recovered", recovered);
                    country_list.put("deaths", deaths);


                    datalist.add(country_list);
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

            if (pDialog.isShowing())
                pDialog.dismiss();
            Cursor res=db.getcountryname();
            countrylist.add("All");
            if (res.moveToFirst()) {
                do {
                    countrylist.add(res.getString(0));
                    Log.e("coutryreport",res.getString(0));

                } while (res.moveToNext());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(countryReport.this,
                    android.R.layout.simple_spinner_item, countrylist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            ListAdapter adapter = new SimpleAdapter(
                    countryReport.this, datalist,
                    R.layout.list_item_country_report, new String[]{"country","confirmed","recovered","deaths"
                    }, new int[]{R.id.country_name
                   ,R.id.confirm_case_d, R.id.recovered_d,R.id.death_d});

            lv.setAdapter(adapter);
        }

    }
}


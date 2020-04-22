package com.app.covid19trackerindia;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class patient_details_district extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<HashMap<String, String>> datalistt;
    ListView lv1;
    DBHelper db=new DBHelper(patient_details_district.this);
    private String TAG = MainActivity.class.getSimpleName();
TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_district);
        toolbar=(Toolbar) findViewById(R.id.patient_district);

        Bundle extras=getIntent().getExtras();
        String District=extras.getString("district");
        String state=extras.getString("state");


        toolbar.setTitle(District);
        if(District.equals("Unknown")){
            District="";
        }

        Cursor cursor=db.getDistrictData(District,state);

        datalistt=new ArrayList<>();
        lv1=(ListView)findViewById(R.id.patientlist);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> patient_list = new HashMap<>();
                patient_list.put("id",cursor.getString(0));
                patient_list.put("date",cursor.getString(1));

                patient_list.put("city",cursor.getString(2));

                patient_list.put("status",cursor.getString(5));
                patient_list.put("notes",cursor.getString(6));

                datalistt.add(patient_list);


            } while (cursor.moveToNext());
        }
        ListAdapter adapter = new SimpleAdapter(
                patient_details_district.this, datalistt,
                R.layout.patient_details_district_wise, new String[]{"id","date","city","status","notes"
        }, new int[]{R.id.pid,R.id.date
                ,R.id.city,R.id.status,R.id.notes});


        lv1.setAdapter(adapter);


    }
}

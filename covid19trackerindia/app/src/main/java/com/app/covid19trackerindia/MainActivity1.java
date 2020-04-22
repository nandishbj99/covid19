package com.app.covid19trackerindia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tomer.fadingtextview.FadingTextView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class
MainActivity1 extends AppCompatActivity {

    FadingTextView message_to_user;
    private ProgressDialog pDialog;


    DBHelper db = new DBHelper(MainActivity1.this);
    Button country, refresh_data;
    JSONArray statewisedata;
    private ArrayList<Parent> deptList = new ArrayList<Parent>();
    HashMap<String, Parent> parentHashMap = new HashMap<>();
    ArrayList<String> statelist;
    int confirmedcase = 0, activecase = 0, recoveredcase = 0, deceasedcase = 0;


    private ExpandableListView myList;
    private MylistAdapter listAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDateandTime = sdf.format(new Date());

    private static String url1 = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org";
    private static String url2 = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";


    private String TAG = MainActivity1.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        message_to_user = (FadingTextView) findViewById(R.id.message_to_user);
        String[] message_texts = {"Clean your hands often", "Avoid touching your eyes, nose, and mouth with unwashed hands.", "Avoid close contact with people who are sick", "Cover your coughs and sneezes with a tissue, and then dispose of the tissue and clean your hands immediately."};
        message_to_user.setTexts(message_texts);
        message_to_user.setTimeout(8, FadingTextView.SECONDS);

        statelist = new ArrayList<>();
        myList = (ExpandableListView) findViewById(R.id.statelist);

        country = (Button) findViewById(R.id.country);

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity1.this, countryReport.class);
                startActivity(i);
            }
        });
        refresh_data = (Button) findViewById(R.id.refresh_data);

        refresh_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

Intent iiii=new Intent(MainActivity1.this,MainActivity.class);
startActivity(iiii);
            }
        });


        confirmedcase=MainActivity.getconfirmedcase();
        activecase=MainActivity.getactive();
        recoveredcase=MainActivity.getrecovered();
        deceasedcase=MainActivity.getdeaths();



        myList.setOnChildClickListener(myListItemClicked);

        myList.setOnGroupClickListener(myListGroupClicked);

        loadData();

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void drawChart() {
        PieChart pieChart = findViewById(R.id.pieChart);


        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(confirmedcase, "Confirm", 0));
        yvalues.add(new PieEntry(recoveredcase, "Recovered", 1));
        yvalues.add(new PieEntry(activecase, "Active", 2));

        yvalues.add(new PieEntry(deceasedcase, "Deceased", 3));


        PieDataSet dataSet = new PieDataSet(yvalues, "");
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(68f);
        pieChart.setHoleRadius(45f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.DKGRAY);

    }


    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
    }


    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.collapseGroup(i);
        }
    }


    private void loadData() {


        addProduct();


        TextView confirmed_d = (TextView) findViewById(R.id.confirmed_d);
        TextView active_d = (TextView) findViewById(R.id.active_d);
        TextView recovered_d = (TextView) findViewById(R.id.recovered_d);
        TextView deceased_d = (TextView) findViewById(R.id.deceased_d);
        confirmed_d.setText(String.valueOf(confirmedcase));
        active_d.setText(String.valueOf(activecase));
        recovered_d.setText(String.valueOf(recoveredcase));
        deceased_d.setText(String.valueOf(deceasedcase));

        Cursor res = db.getstatuscountdate(currentDateandTime);

        int confirm_count = 0;
        if (res.moveToFirst()) {
            confirm_count = res.getInt(0);

        }


        int pre_data = Integer.parseInt(confirmed_d.getText().toString());
        String tttt = "(+" + String.valueOf(confirm_count) + ")   " + pre_data;
        confirmed_d.setText(tttt);
        drawChart();


    }


    private ExpandableListView.OnChildClickListener myListItemClicked = new ExpandableListView.OnChildClickListener() {

        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {


            Parent parent1 = deptList.get(groupPosition);

            Child child = parent1.getChildren().get(childPosition);

            Intent district_report = new Intent(MainActivity1.this, patient_details_district.class);
            district_report.putExtra("district", child.getdistrict());
            district_report.putExtra("state", parent1.getState());
            startActivity(district_report);
            return false;
        }

    };


    private ExpandableListView.OnGroupClickListener myListGroupClicked = new ExpandableListView.OnGroupClickListener() {

        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {

            Parent parent1 = deptList.get(groupPosition);


            return false;
        }

    };



    private void addProduct() {
        Cursor c = db.getdatastatewise();

        if (c.moveToFirst()) {
            do {

                Parent parent = new Parent();
                String state = c.getString(0);
                String countstate = c.getString(1);
                statelist.add(state);
                parent.setState(state);
                Cursor c4 = db.getstatuscountstate(currentDateandTime, state);

                int confirm_inc_state = 0;
                if (c4.moveToFirst()) {
                    confirm_inc_state = c4.getInt(0);
                }
                // Log.e(TAG, "confirm inc state: " + confirm_inc_state + "date" + currentDateandTime + "state" + state);
                if (confirm_inc_state == 0) {
                    parent.setConfirmed(countstate);

                } else {
                    String con_data = "(+" + confirm_inc_state + ") " + countstate;
                    parent.setConfirmed(con_data);
                }
                parentHashMap.put(state, parent);
    //            confirmedcase += Integer.valueOf(countstate);

                String activecountstate = c.getString(4);


                parent.setActive(String.valueOf(activecountstate));
  //              activecase += Integer.valueOf(activecountstate);


                String recovercountstate = c.getString(2);

                parent.setRecovered(String.valueOf(recovercountstate));
//                recoveredcase += Integer.valueOf(recovercountstate);


                String deathcountstate = c.getString(3);

                parent.setDeaths(String.valueOf(deathcountstate));
                //deceasedcase += Integer.valueOf(deathcountstate);
            } while (c.moveToNext());


            Log.e(TAG, "in addproduct: " + statelist);

            String state;
            for (int ii = 0; ii < statelist.size(); ii++) {
                state = statelist.get(ii);
                // Log.e(TAG, "in addproduct: " + state);


                HashMap<String, Child> district_map_obj = new HashMap<>();

                Parent parentobj = parentHashMap.get(state);
                Cursor cursor = db.getAllData(state);
                parentobj.setChildren(new ArrayList<Child>());
                if (cursor.moveToFirst()) {
                    do {
                        Child child = new Child();
                        String District = cursor.getString(0);
                        if (District.equals("")) {
                            child.setDistrict("Unknown");
                        } else {
                            child.setDistrict(District);
                        }
                        child.setConfirmed(String.valueOf(cursor.getInt(1)));

                        district_map_obj.put(District, child);
                        parentobj.getChildren().add(child);
                    } while (cursor.moveToNext());
                }
                Cursor c11 = db.getconfirmincbydistrict(currentDateandTime, state);


                if (parentobj.getState().equals(state)) {
                    if (c11.moveToFirst()) {
                        do {
                            Child child = district_map_obj.get(c11.getString(1));
                            String precon=child.getConfirmed();
                            String incdata="(+"+c11.getInt(0)+")  "+precon;
                            child.setConfirmed(incdata);
                        } while (c11.moveToNext());
                    }
                /*Collection<Child> list1=district_map_obj.values();
                Iterator itr=list1.iterator();
                parentobj.setChildren(new ArrayList<Child>());

                while (itr.hasNext()){
                    Child ccc= (Child) itr.next();
                    parentobj.getChildren().add(ccc);

                }*/


                }
                deptList.add(parentobj);

            }


        }
        listAdapter = new MylistAdapter(MainActivity1.this, deptList);


        myList.setAdapter(listAdapter);



    }
}
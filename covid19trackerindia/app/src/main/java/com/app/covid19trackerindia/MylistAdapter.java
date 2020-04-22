package com.app.covid19trackerindia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MylistAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Parent> deptList;

    public MylistAdapter(Context context, ArrayList<Parent> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> childrenlist = deptList.get(groupPosition).getChildren();
        return childrenlist.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        Child child = (Child) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.childrow, null);
        }

        ((TextView) view.findViewById(R.id.district)).setText(child.getdistrict());

        ((TextView) view.findViewById(R.id.confirmed_case_district)).setText(child.getConfirmed());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<Child> childrenlist = deptList.get(groupPosition).getChildren();
        return childrenlist.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        Parent parent1 = (Parent) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.statelist_view, null);
        }

        ((TextView) view.findViewById(R.id.statee)).setText(parent1.getState());
        ((TextView) view.findViewById(R.id.confirm_case)).setText(parent1.getConfirmed());
        ((TextView) view.findViewById(R.id.active_case)).setText(parent1.getActive());
        ((TextView) view.findViewById(R.id.recovered_case)).setText(parent1.getRecovered());
        ((TextView) view.findViewById(R.id.death_case)).setText(parent1.getDeaths());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
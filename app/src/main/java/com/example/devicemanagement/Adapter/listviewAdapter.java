package com.example.devicemanagement.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.devicemanagement.Entity.ChiTietSD;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class listviewAdapter extends BaseAdapter {

    public ArrayList<ChiTietSD> CTSDList;
    Activity activity;

    public listviewAdapter(Activity activity, ArrayList<ChiTietSD> CTSDList) {
        super();
        this.activity = activity;
        this.CTSDList = CTSDList;
    }

    @Override
    public int getCount() {
        return CTSDList.size();
    }

    @Override
    public Object getItem(int position) {
        return CTSDList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView sttu;
        TextView ngaySuDung;
        TextView tenThietBi;
        TextView soluong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_row_huyen, null);
            holder = new ViewHolder();
            holder.sttu = (TextView) convertView.findViewById(R.id.sNo);
            holder.ngaySuDung = (TextView) convertView.findViewById(R.id.dateuse);
            holder.tenThietBi = (TextView) convertView
                    .findViewById(R.id.deviceid);
            holder.soluong = (TextView) convertView.findViewById(R.id.qnty);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChiTietSD item = CTSDList.get(position);
        holder.sttu.setText(item.getNgaySuDung().toString());
        holder.ngaySuDung.setText(item.getMaThietBi().toString());
        holder.tenThietBi.setText(item.getMaPhong().toString());
        holder.soluong.setText(item.getSoLuong().toString());

        return convertView;
    }
}
package com.example.devicemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class AdapterPhongHoc extends ArrayAdapter<PhongHoc> {
    Context context;
    int resource;
    ArrayList<PhongHoc> data;

    public AdapterPhongHoc(@NonNull Context context, int resource, @NonNull ArrayList<PhongHoc> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvMaPhong = convertView.findViewById(R.id.tvMaPhong);
        TextView tvLoaiPhong = convertView.findViewById(R.id.tvLoaiPhong);
        TextView tvTang = convertView.findViewById(R.id.tvTang);
        PhongHoc phongHoc = data.get(position);
        tvMaPhong.setText(phongHoc.getMaPhong());
        tvLoaiPhong.setText(phongHoc.getLoaiPhong());
        tvTang.setText(phongHoc.getTang());
        return convertView;
    }
}

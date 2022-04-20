package com.example.devicemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class AdapterLoaiThietBi extends ArrayAdapter<LoaiThietBi> {
    Context context;
    int resource;
    ArrayList<LoaiThietBi> data;

    public AdapterLoaiThietBi(@NonNull Context context, int resource, @NonNull ArrayList<LoaiThietBi> data) {
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
        TextView tvLoaiTB = convertView.findViewById(R.id.tvLoaiTB);
        TextView tvTenTB = convertView.findViewById(R.id.tvTenTB);
        LoaiThietBi loaiThietBi = data.get(position);
        tvLoaiTB.setText(loaiThietBi.getMaLoai());
        tvTenTB.setText(loaiThietBi.getTenLoai());
        return convertView;
    }
}

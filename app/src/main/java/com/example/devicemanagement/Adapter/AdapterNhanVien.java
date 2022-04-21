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

import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class AdapterNhanVien extends ArrayAdapter<NhanVien> {
    Context context;
    int resource;
    ArrayList<NhanVien> data;

    public AdapterNhanVien(@NonNull Context context, int resource, @NonNull ArrayList<NhanVien> data) {
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
        TextView tvMaNV = convertView.findViewById(R.id.tvMaNV);
        TextView tvTenNV = convertView.findViewById(R.id.tvTenNV);
        LinearLayout llItemNhanVien = convertView.findViewById(R.id.llItemNhanVien);
        NhanVien nhanVien = data.get(position);
        tvMaNV.setText(nhanVien.getTenDangNhap());
        tvTenNV.setText(nhanVien.getHoTen());
        llItemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "XXXXXXXXXX", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}

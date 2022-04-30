package com.example.devicemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.R;

import java.util.List;

public class AdapterMaPhong extends ArrayAdapter<PhongHoc> {
    public AdapterMaPhong(@NonNull Context context, int resource, @NonNull List<PhongHoc> object) {
        super(context, resource, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_maphong, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tvSelectedMaPhong);
        PhongHoc phongHoc = (PhongHoc) this.getItem(position);
        if(phongHoc != null){
            tvSelected.setText(phongHoc.getMaPhong());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_maphong, parent, false);
        TextView tvPhongHoc = convertView.findViewById(R.id.tvMaPhongSpinner);
        PhongHoc phongHoc = (PhongHoc) this.getItem(position);
        if(phongHoc != null){
            tvPhongHoc.setText(phongHoc.getMaPhong());
        }
        return convertView;
    }
}

package com.example.devicemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.LoaiPhong;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.R;

import java.util.List;

public class AdapterTenLoaiThietBi extends ArrayAdapter<LoaiThietBi> {
    public AdapterTenLoaiThietBi(@NonNull Context context, int resource, @NonNull List<LoaiThietBi> object) {
        super(context, resource, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinnermaloaitb, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tvSelected_maloaitb);
        LoaiThietBi loaiThietBi = (LoaiThietBi) this.getItem(position);
        if(loaiThietBi != null){
            tvSelected.setText(loaiThietBi.getTenLoai());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thietbi_spinner, parent, false);
        TextView tvThietBi = convertView.findViewById(R.id.tvThietBi);
        LoaiThietBi loaiThietBi = (LoaiThietBi) this.getItem(position);
        if(loaiThietBi != null){
            tvThietBi.setText(loaiThietBi.getTenLoai());
        }
        return convertView;
    }
}

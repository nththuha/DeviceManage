package com.example.devicemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.util.List;

public class AdapterMaThietBi extends ArrayAdapter<ThietBi> {
    public AdapterMaThietBi(@NonNull Context context, int resource, @NonNull List<ThietBi> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_mathietbi, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tvSelectedMaThietBi);
        ThietBi thietBi = (ThietBi) this.getItem(position);
        if(thietBi != null)
            tvSelected.setText(thietBi.getMaThietBi());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_mathietbi, parent, false);
        TextView tvThietBi = convertView.findViewById(R.id.tvMaThietBiSpinner);
        ThietBi thietBi = (ThietBi) this.getItem(position);
        if(thietBi != null)
            tvThietBi.setText(thietBi.getMaThietBi());
        return convertView;
    }
}

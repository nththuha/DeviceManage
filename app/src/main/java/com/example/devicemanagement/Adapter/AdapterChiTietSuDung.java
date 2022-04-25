package com.example.devicemanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterChiTietSuDung extends ArrayAdapter<ThietBi> {
    Context context;
    int resource;
    ArrayList<ThietBi> data;
    TextView tvMatb, tvTentb, tvSoluong;

    public AdapterChiTietSuDung(@NonNull Context context, int resource, @NonNull ArrayList<ThietBi> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    public void setFilterList(ArrayList<ThietBi> filter) {
        this.data = filter;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        tvMatb = convertView.findViewById(R.id.tvMaThietBi);
        tvTentb = convertView.findViewById(R.id.tvTenThietBi);
        tvSoluong = convertView.findViewById(R.id.tvSoLuongMuon);
        ThietBi thietBi = data.get(position);
        tvMatb.setText(thietBi.getMaThietBi());
        tvTentb.setText(thietBi.getTenThietBi());
        tvSoluong.setText(thietBi.getSoLuong());

        return convertView;
    }
}

package com.example.devicemanagement.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Controller.ChiTietSuDungActivity;
import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.ChiTietSD;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.sql.Date;
import java.util.ArrayList;

public class AdapterChiTietSuDung extends ArrayAdapter<ChiTietSD> {
    Context context;
    int resource;
    ArrayList<ChiTietSD> data;
    DBChiTietSD dbChiTietSD;
    ChiTietSD chiTietSD;
    TextView tvMaPhongSD, tvMaThietBiSD, tvSoLuongMuon;
    Integer sldu = 0;

    public AdapterChiTietSuDung(@NonNull Context context, int resource, @NonNull ArrayList<ChiTietSD> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    public void setFilterList(ArrayList<ChiTietSD> filter) {
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
        DBThietBi dbThietBi = new DBThietBi(context);
        tvMaPhongSD = convertView.findViewById(R.id.tvMaPhongSD);
        tvMaThietBiSD = convertView.findViewById(R.id.tvMaThietBiSD);
        tvSoLuongMuon = convertView.findViewById(R.id.tvSoLuongMuon);
        chiTietSD = data.get(position);

        tvMaPhongSD.setText(chiTietSD.getMaPhong());
        tvMaThietBiSD.setText(chiTietSD.getMaThietBi());
        tvSoLuongMuon.setText(chiTietSD.getSoLuong()+"/"+dbThietBi.laySLThietBi(chiTietSD.getMaThietBi()));

        return convertView;
    }
}

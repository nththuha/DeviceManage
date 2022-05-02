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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Controller.LoaiThietBiActivity;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class AdapterNhanVien extends ArrayAdapter<NhanVien> {
    Context context;
    int resource;
    ArrayList<NhanVien> data;
    DBNhanVien dbNhanVien;

    public AdapterNhanVien(@NonNull Context context, int resource, @NonNull ArrayList<NhanVien> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public void setFilterList(ArrayList<NhanVien> filter){
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
        TextView tvMaNV = convertView.findViewById(R.id.tvMaNV);
        TextView tvTenNV = convertView.findViewById(R.id.tvTenNV);
        RelativeLayout rlItemClick = convertView.findViewById(R.id.rlItemClick);
        NhanVien nhanVien = data.get(position);
        tvMaNV.setText(nhanVien.getTenDangNhap());
        tvTenNV.setText(nhanVien.getHoTen());
        rlItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThongTin(Gravity.CENTER, nhanVien);
            }
        });
        return convertView;
    }

    private void hienThongTin(int gravity, NhanVien nhanVien){
        dbNhanVien = new DBNhanVien(context);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_thongtin_nhanvien);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //click ra bên ngoài để tắt dialog
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(true);
        }

        TextView tvMaNV = dialog.findViewById(R.id.tvMaNV);
        TextView tvTenNV = dialog.findViewById(R.id.tvTenNV);
        TextView tvEmail = dialog.findViewById(R.id.tvEmail);

        tvMaNV.setText(nhanVien.getTenDangNhap());
        tvTenNV.setText(nhanVien.getHoTen());
        tvEmail.setText(nhanVien.getMail());

        dialog.show();
    }


}

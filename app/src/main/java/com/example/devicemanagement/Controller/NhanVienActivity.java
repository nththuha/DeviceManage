package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devicemanagement.Adapter.AdapterLoaiThietBi;
import com.example.devicemanagement.Adapter.AdapterNhanVien;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class NhanVienActivity extends AppCompatActivity {
    ListView lvNV;
    ImageButton imbBack;
    SearchView svNV;
    AdapterNhanVien adapterNhanVien;
    ArrayList<NhanVien> DSNV;
    DBNhanVien dbNhanVien;
    ArrayList<NhanVien> filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dbNhanVien = new DBNhanVien(this);
        DSNV = dbNhanVien.layDSNhanVien();
        adapterNhanVien = new AdapterNhanVien(this, R.layout.layout_nhan_vien, DSNV);
        lvNV.setAdapter(adapterNhanVien);

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        svNV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getFilter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getFilter(s);
                return false;
            }
        });
    }

    private void getFilter(String s){
        filter = new ArrayList<>();
        for (NhanVien nv: DSNV) {
            if(nv.getHoTen().toLowerCase().contains(s.toLowerCase())){
                filter.add(nv);
            }
        }
        adapterNhanVien.setFilterList(filter);
        if(filter.isEmpty()){
            // Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không có dữ liệu để hiển thị!")
                    .setCancelable(true)
                    .show();
        }
    }

    private void setControl() {
        lvNV = findViewById(R.id.lvNV);
        imbBack = findViewById(R.id.imbBack);
        svNV = findViewById(R.id.svNV);
    }


}
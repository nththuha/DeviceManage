package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.devicemanagement.Adapter.AdapterLoaiThietBi;
import com.example.devicemanagement.Adapter.AdapterNhanVien;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class NhanVienActivity extends AppCompatActivity {
    ListView lvNV;
    ImageButton imbBack;
    SearchView svNV;
    AdapterNhanVien adapterNhanVien;
    ArrayList<NhanVien> DSNV;
    DBNhanVien dbNhanVien;

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
    }

    private void setControl() {
        lvNV = findViewById(R.id.lvNV);
        imbBack = findViewById(R.id.imbBack);
        svNV = findViewById(R.id.svNV);
    }
}
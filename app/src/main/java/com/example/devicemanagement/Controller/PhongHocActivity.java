package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.devicemanagement.Adapter.AdapterNhanVien;
import com.example.devicemanagement.Adapter.AdapterPhongHoc;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class PhongHocActivity extends AppCompatActivity {
    ListView lvPH;
    ImageButton imbBack;
    SearchView svPH;
    AdapterPhongHoc adapterPhongHoc;
    ArrayList<PhongHoc> DSPH;
    DBPhongHoc dbPhongHoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_hoc);
        setEvent();
        setControl();
    }

    private void setEvent() {
        dbPhongHoc = new DBPhongHoc(this);
        DSPH = dbPhongHoc.layDSPhongHoc();
        adapterPhongHoc = new AdapterPhongHoc(this, R.layout.layout_nhan_vien, DSPH);
        lvPH.setAdapter(adapterPhongHoc);

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setControl() {
        lvPH = findViewById(R.id.lvPH);
        imbBack = findViewById(R.id.imbBack);
        svPH = findViewById(R.id.svPH);
    }
}
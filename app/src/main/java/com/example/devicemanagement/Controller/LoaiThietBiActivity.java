package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.devicemanagement.Adapter.AdapterLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class LoaiThietBiActivity extends AppCompatActivity {
    ListView lvLTB;
    ImageButton imbThemLTB;
    SearchView svLTB;
    AdapterLoaiThietBi adapterLoaiThietBi;
    ArrayList<LoaiThietBi> DSLTB;
    DBLoaiThietBi dbLoaiThietBi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_thiet_bi);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dbLoaiThietBi = new DBLoaiThietBi(this);
        DSLTB = dbLoaiThietBi.layDanhSachLTB();
        adapterLoaiThietBi = new AdapterLoaiThietBi(this, R.layout.layout_loai_thiet_bi, DSLTB);
        lvLTB.setAdapter(adapterLoaiThietBi);
    }

    private void setControl() {
        lvLTB = findViewById(R.id.lvLTB);
        imbThemLTB = findViewById(R.id.imbThemLTB);
        svLTB = findViewById(R.id.svLTB);
    }
}
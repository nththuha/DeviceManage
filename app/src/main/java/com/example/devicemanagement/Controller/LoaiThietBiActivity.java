package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
    ImageButton imbThemLTB, imbBack;
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

//        dbLoaiThietBi.themLTB(new LoaiThietBi("CS", "Chiếu sáng"));
//        dbLoaiThietBi.themLTB(new LoaiThietBi("DC", "Dụng cụ dạy học"));
//        dbLoaiThietBi.themLTB(new LoaiThietBi("DH", "Điều hòa"));
//        dbLoaiThietBi.themLTB(new LoaiThietBi("DT", "Điện tử"));

        DSLTB = dbLoaiThietBi.layDanhSachLTB();
        adapterLoaiThietBi = new AdapterLoaiThietBi(this, R.layout.layout_loai_thiet_bi, DSLTB);
        lvLTB.setAdapter(adapterLoaiThietBi);

        imbThemLTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setControl() {
        lvLTB = findViewById(R.id.lvLTB);
        imbThemLTB = findViewById(R.id.imbThemLTB);
        svLTB = findViewById(R.id.svLTB);
        imbBack = findViewById(R.id.imbBack);
    }
}
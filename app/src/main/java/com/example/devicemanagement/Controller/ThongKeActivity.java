package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.devicemanagement.Adapter.listviewAdapter;
import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.ChiTietSD;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ThongKeActivity extends AppCompatActivity {
    ImageButton imbBack;
    Button btnChart, btnOutExcel;
    DBChiTietSD dbChiTietSD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setControl();
        setEvent();
        displayHistory();
    }
    private void setEvent() {

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKeActivity.this,ChartActivity.class);

                // move to new activity
                startActivity(intent);
            }
        });

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void displayHistory(){
        dbChiTietSD = new DBChiTietSD(this);
        ArrayList<ChiTietSD>  historyList = new ArrayList<>();
//        historyList.add(new ChiTietSD("1","2","3","4"));
//        historyList.add(new ChiTietSD("44","44","455566","4"));
//        historyList.add(new ChiTietSD("44","44","455566","4"));
        historyList = dbChiTietSD.layDSChiTietSD();
        ListView lview = (ListView) findViewById(R.id.listviewhuyen);
        listviewAdapter adapter = new listviewAdapter(this, historyList);
        lview.setAdapter(adapter);

    }
    private void setControl() {

        imbBack = findViewById(R.id.imbBack);

        btnChart = findViewById(R.id.btnChart);
        btnOutExcel = findViewById(R.id.btnReport);
    }
}
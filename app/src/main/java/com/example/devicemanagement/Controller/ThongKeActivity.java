package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.devicemanagement.Adapter.listviewAdapter;
import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.Entity.ChiTietSD;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {
    ImageButton imbBack;
    Button btnChart, btnOutExcel, btnLoc;
    DBChiTietSD dbChiTietSD;
    ArrayList<ChiTietSD>  historyList = null;
    int ngayBD, ngayKT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setControl();
        setEvent();
        displayHistory();
    }
    private void setEvent() {


        dbChiTietSD = new DBChiTietSD(this);
        historyList = dbChiTietSD.layDSChiTietSD();
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKeActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });
    btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              fillter();
              displayHistory();
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
        ListView lview = (ListView) findViewById(R.id.listviewhuyen);
        listviewAdapter adapter = new listviewAdapter(this, historyList);
        lview.setAdapter(adapter);
    }
    public int getDateSS(String date){
        date = date.replace("-","");
        System.out.println(date);
        return Integer.parseInt(date);
    }
    public void fillter(){
        EditText tvNgayBD = findViewById(R.id.ngyBD);
        EditText tvNgayKT = findViewById(R.id.ngyKT);
        ngayBD = getDateSS(tvNgayBD.getText().toString());
        ngayKT = getDateSS(tvNgayKT.getText().toString());
        ArrayList<ChiTietSD>  newHistoryList = new ArrayList<>();
        historyList = new DBChiTietSD(this).layDSChiTietSD();
        if(historyList != null)
        {
            for (ChiTietSD ct: historyList  ) {
                if(getDateSS(ct.getNgaySuDung().trim()) >= ngayBD && getDateSS(ct.getNgaySuDung().trim()) <= ngayKT){
                    newHistoryList.add(ct);
                    System.out.println(ngayBD + "-" + ngayKT + " ??? " + ct.getNgaySuDung());
                }
//                newHistoryList.add(new ChiTietSD(getDateSS(ct.getNgaySuDung().trim())+"",ngayKT+"",ngayBD+"","Aaaaa"));

            }

        }
        historyList = newHistoryList;
         }
     public void reportExcel(){
     }
    private void setControl() {

        imbBack = findViewById(R.id.imbBack);
        btnChart = findViewById(R.id.btnChart);
        btnOutExcel = findViewById(R.id.btnReport);
        btnLoc = findViewById(R.id.btnFilter);
    }
}
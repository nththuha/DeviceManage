package com.example.devicemanagement.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.ChiTietSD;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    // ArrayList<ChiTietSD> historyList = null;
    ImageButton imbBack;
    private PieChart mChart;
    Switch next;
    private static ArrayList<ChiTietSD> listCTSD;

    private static DBThietBi dbtbi;
    private static int ngayBD=0, ngayKT=99999999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbtbi = new DBThietBi(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        // get historyList to listCTSD
        Intent intent =  getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            ngayBD = bundle.getInt("ngay_bd",0);
            ngayKT = bundle.getInt("ngay_kt",0);
        }
        fillter();


        setControl();
        setEvent();
//        listCTSD =new ArrayList<>();
//        new DBThietBi(this).themThietBi(new ThietBi("11","MAY LANH","Trung Quoc","90","MICRO"));
//        new DBThietBi(this).themThietBi(new ThietBi("21","Quat TRAN","Trung Quoc","90","MICRO"));
//        new DBThietBi(this).themThietBi(new ThietBi("31","MOCRO 2","Trung Quoc","90","MICRO"));
//        new DBThietBi(this).themThietBi(new ThietBi("41","MICRO PIN","Trung Quoc","90","MICRO"));
//        new DBChiTietSD(this).xoaChiTietSD();
//        listCTSD.add(new ChiTietSD("1","2","2021-02-2","5"));
//
//        listCTSD.add(new ChiTietSD("1","1","2020-04-2","6"));
        mChart = (PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("PieChart");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);
        addDataSet(mChart);

        mChart.setOnChartValueSelectedListener(this);
    }
    private static void addDataSet(PieChart pieChart) {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        HashMap<String,Integer> hm = new HashMap<String,Integer>();


        for (ChiTietSD x:listCTSD) {
            if(!hm.containsKey(x.getMaThietBi())){
                hm.put(x.getMaThietBi(), Integer.parseInt(x.getSoLuong()));
            }
            else
            {
                hm.put(x.getMaThietBi(), hm.get(x.getMaThietBi())+Integer.parseInt(x.getSoLuong())) ;
            }
        }
        int id = 0;
        for(Map.Entry<String, Integer> entry : hm.entrySet()) {
//            String key = entry.getKey();
            xEntrys.add(entry.getKey());
            yEntrys.add(new PieEntry(entry.getValue(),id++)); ;
        }

//            Integer[] yData = (Integer[]) hm.values().toArray();
//        String[] xData= (String[]) hm.keySet().toArray();
//        for (int i = 0; i < yData.length;i++){
//            yEntrys.add(new PieEntry(yData[i],i));
//        }
//        for (int i = 0; i < xData.length;i++){
//            xEntrys.add(xData[i]);
//        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Thiết Bị Được Mượn");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.WHITE);

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Tổng mượn: "
                + e.getY()
                + ", Thiết Bị: "
                +  dbtbi.layTenThietBi(listCTSD.get((int)h.getX()).getMaThietBi())
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
    private void setEvent() {

//        next.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Intent intent = new Intent(PieChartActivity.this, CombinedChartActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PieChartActivity.this, CombinedChartActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setControl() {

        imbBack = findViewById(R.id.imbBack);
//        imbBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    public int getDateSS(String date) {
        date = date.replace("-", "");
        // System.out.println(date);
        return Integer.parseInt(date);
    }

    public void fillter() {
        ArrayList<ChiTietSD> newHistoryList = new ArrayList<>();
        listCTSD = new DBChiTietSD(this).layDSChiTietSD();
        if (listCTSD != null) {
            for (ChiTietSD ct : listCTSD) {
                if (getDateSS(ct.getNgaySuDung().trim()) >= ngayBD && getDateSS(ct.getNgaySuDung().trim()) <= ngayKT) {
//                    ct.setMaThietBi(dbtbi.layTenThietBi(ct.getMaThietBi()));
                    newHistoryList.add(ct);
                }
            }

        }
        listCTSD = newHistoryList;
    }

}

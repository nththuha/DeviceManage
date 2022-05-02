package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {
    ImageButton imbBack;
    Button btnChart, btnOutExcel, btnLoc, btnReport;
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
        btnOutExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    reportExcel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
//     public void reportPDF(){
//         //permission
//         ActivityCompat.requestPermissions(this, new String[]{
//                 Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//
//
//
//     }

    private void setControl() {

        imbBack = findViewById(R.id.imbBack);
        btnChart = findViewById(R.id.btnChart);
        btnOutExcel = findViewById(R.id.btnReport);
        btnLoc = findViewById(R.id.btnFilter);
//        btnReport = findViewById(R.id.btnReport);
    }
    public void reportExcel() throws IOException {
        //Creating the workbook

        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Report");
        Row row1 = sheet.createRow(0);
//        Row row2 = sheet.createRow(1);
        row1.createCell(0).setCellValue("Test");
        row1.createCell(1).setCellValue("Test");
        row1.createCell(2).setCellValue("Test");
        row1.createCell(3).setCellValue("Test");
//        row2.createCell(0).setCellValue("test");
        fillter();
        int col =  historyList.size();




        for (int i = 0; i < col; i++) {
            Row row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(historyList.get(i).getMaPhong());
            row.createCell(1).setCellValue(historyList.get(i).getMaThietBi());
            row.createCell(2).setCellValue(historyList.get(i).getNgaySuDung());
            row.createCell(3).setCellValue(historyList.get(i).getSoLuong());
        }


        String namefile = "report-ctsd";
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+namefile+".xlsx");
            workbook.write(file);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
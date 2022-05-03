package com.example.devicemanagement.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.Adapter.listviewAdapter;
import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.ChiTietSD;
import com.example.devicemanagement.R;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {
    ImageButton imbBack;
    Button btnChart, btnOutExcel, btnLoc, btnReport;
    DBChiTietSD dbChiTietSD;
    ArrayList<ChiTietSD> historyList = null;
    int ngayBD, ngayKT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setControl();
        setEvent();
        fillter();
        displayHistory();
    }

    private void setEvent() {

        dbChiTietSD = new DBChiTietSD(this);
        historyList = dbChiTietSD.layDSChiTietSD();
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKeActivity.this, PieChartActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("ngay_bd",ngayBD);
                bundle.putInt("ngay_kt",ngayKT);
                intent.putExtras(bundle);
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
                    thongBaoThanhCong(Gravity.CENTER, "Xuất file Excel thành công!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void displayHistory() {
        ListView lview = (ListView) findViewById(R.id.listviewhuyen);
        listviewAdapter adapter = new listviewAdapter(this, historyList);
        lview.setAdapter(adapter);
    }

    public int getDateSS(String date) {
        date = date.replace("-", "");
        System.out.println(date);
        return Integer.parseInt(date);
    }

    public void fillter() {

        DBThietBi dbtbi = new DBThietBi(this);
        EditText tvNgayBD = findViewById(R.id.ngyBD);
        EditText tvNgayKT = findViewById(R.id.ngyKT);
        ngayBD = getDateSS(tvNgayBD.getText().toString());
        ngayKT = getDateSS(tvNgayKT.getText().toString());
        ArrayList<ChiTietSD> newHistoryList = new ArrayList<>();
        historyList = new DBChiTietSD(this).layDSChiTietSD();
        if (historyList != null) {
            for (ChiTietSD ct : historyList) {
                if (getDateSS(ct.getNgaySuDung().trim()) >= ngayBD && getDateSS(ct.getNgaySuDung().trim()) <= ngayKT) {
                    ct.setMaThietBi(dbtbi.layTenThietBi(ct.getMaThietBi()));
                    newHistoryList.add(ct);

                }

            }

        }
        historyList = newHistoryList;
    }
    // public void reportPDF(){
    // //permission
    // ActivityCompat.requestPermissions(this, new String[]{
    // Manifest.permission.WRITE_EXTERNAL_STORAGE},
    // PackageManager.PERMISSION_GRANTED);
    //
    //
    //
    // }

    private void setControl() {

        imbBack = findViewById(R.id.imbBack);
        btnChart = findViewById(R.id.btnChart);
        btnOutExcel = findViewById(R.id.btnReport);
        btnLoc = findViewById(R.id.btnFilter);
    }

    public void reportExcel() throws IOException {
        // Creating the workbook

        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Report");
        Row row1 = sheet.createRow(0);
        // Row row2 = sheet.createRow(1);
        row1.createCell(0).setCellValue("Mã Phòng");
        row1.createCell(1).setCellValue("Tên Thiết Bị");
        row1.createCell(2).setCellValue("Ngày Sử Dụng");
        row1.createCell(3).setCellValue("Số Lượng Mượn");
        // row2.createCell(0).setCellValue("test");
        fillter();
        int col = historyList.size();

        for (int i = 0; i < col; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(historyList.get(i).getMaPhong());
            row.createCell(1).setCellValue(historyList.get(i).getMaThietBi());
            row.createCell(2).setCellValue(historyList.get(i).getNgaySuDung());
            row.createCell(3).setCellValue(historyList.get(i).getSoLuong());
        }

        String namefile = "thongke-sudung-"+ngayBD+"-"+ngayKT;
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + namefile + ".xlsx");
            workbook.write(file);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void thongBaoThanhCong(int gravity, String text) {
        // xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_tbthanhcong);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        // click ra bên ngoài để tắt dialog
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(true);
        }
        TextView tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText(text);
        dialog.show();

    }
}
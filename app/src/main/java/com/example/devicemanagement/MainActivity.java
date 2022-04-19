package com.example.devicemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;

public class MainActivity extends AppCompatActivity {
    private DBNhanVien dbNhanVien;
    private DBLoaiThietBi dbLoaiThietBi;
    private DBThietBi dbThietBi;
    private DBPhongHoc dbPhongHoc;
    private DBChiTietSD dbChiTietSD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getControl();
    }

    private void getControl() {
        dbNhanVien = new DBNhanVien(getApplicationContext(),"nhanvien",null,1);
        dbNhanVien.getReadableDatabase();
        dbLoaiThietBi = new DBLoaiThietBi(getApplicationContext(),"loaithietbi",null,1);
        dbLoaiThietBi.getReadableDatabase();
        dbThietBi = new DBThietBi(this,"thietbi",null,1);
        dbThietBi.getReadableDatabase();
        dbPhongHoc = new DBPhongHoc(this,"phonghoc",null,1);
        dbPhongHoc.getReadableDatabase();
        dbChiTietSD = new DBChiTietSD(this,"chitietsudung",null,1);
        dbChiTietSD.getReadableDatabase();
    }
}
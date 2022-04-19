package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private DBNhanVien dbNhanVien;
    private DBLoaiThietBi dbLoaiThietBi;
    private DBThietBi dbThietBi;
    private DBPhongHoc dbPhongHoc;
    private DBChiTietSD dbChiTietSD;

    EditText txtUser, txtPass;
    Button btnLogin;
    TextView tvForgotPass;

    ArrayList<NhanVien> DSNV = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbNhanVien = new DBNhanVien(this);
        getDataBase();
        getControl();
    }

    private void getControl() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvForgotPass);
    }

    private void getDataBase() { // ai chưa tạo CSDL ban đầu thì bỏ cmt nha!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        dbNhanVien = new DBNhanVien(this);
//        dbNhanVien.getReadableDatabase();
//        dbLoaiThietBi = new DBLoaiThietBi(this);
//        dbLoaiThietBi.getReadableDatabase();
//        dbThietBi = new DBThietBi(this);
//        dbThietBi.getReadableDatabase();
//        dbPhongHoc = new DBPhongHoc(this);
//        dbPhongHoc.getReadableDatabase();
//        dbChiTietSD = new DBChiTietSD(this);
//        dbChiTietSD.getReadableDatabase();
    }
}
package com.example.devicemanagement.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class HomeActivity extends AppCompatActivity {

    private DBThietBi dbThietBi;
    private DBLoaiThietBi dbLoaiThietBi;
    BottomNavigationView bottomNavigationView;

    HomeFragmentActivity homeFragmentActivity = new HomeFragmentActivity();
    TaiKhoanFragmentActivity taiKhoanFragmentActivity = new TaiKhoanFragmentActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setControl();
        //thêm dữ liệu
//        dbThietBi = new DBThietBi(this);
//        dbThietBi.themThietBi(new ThietBi("HDMI","Cổng HDMI","2","China","CS"));
//        dbLoaiThietBi = new DBLoaiThietBi(this);
//        dbLoaiThietBi.themLTB(new LoaiThietBi("CS", "Chiếu sáng"));

        setEvent();
    }

    private void setEvent() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frmHome, homeFragmentActivity).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btnTrangChu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frmHome, homeFragmentActivity).commit();
                        return true;
                    case R.id.btnTaiKhoan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frmHome, taiKhoanFragmentActivity).commit();
                        return true;
                }

                return false;
            }
        });
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.btnViewTrangChu);
    }
}
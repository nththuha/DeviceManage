package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.devicemanagement.Adapter.AdapterLoaiThietBi;
import com.example.devicemanagement.Adapter.AdapterNhanVien;
import com.example.devicemanagement.Adapter.AdapterPhongHoc;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class PhongHocActivity extends AppCompatActivity {
    ListView lvPH;
    ImageButton imbBack, imbThemPH, imbSua, imbXoa;
    SearchView svPH;
    Button btnLuu, btnThoat;
    EditText txtMaPhong, txtLoaiPhong, txtTang;
    AdapterPhongHoc adapterPhongHoc;
    ArrayList<PhongHoc> DSPH;
    DBPhongHoc dbPhongHoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_hoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dbPhongHoc = new DBPhongHoc(this);
        DSPH = dbPhongHoc.layDSPhongHoc();
        adapterPhongHoc = new AdapterPhongHoc(this, R.layout.layout_phong_hoc, DSPH);
        lvPH.setAdapter(adapterPhongHoc);

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imbThemPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themPhongHoc(Gravity.CENTER, dbPhongHoc);
            }
        });
    }

    private void setControl() {
        lvPH = findViewById(R.id.lvPH);
        imbBack = findViewById(R.id.imbBack);
        imbThemPH = findViewById(R.id.imbThemPH);
        imbSua = findViewById(R.id.imbSua);
        imbXoa = findViewById(R.id.imbXoa);
        svPH = findViewById(R.id.svPH);
    }

    public void loadListView(DBPhongHoc dbPhongHoc){
        DSPH = dbPhongHoc.layDSPhongHoc();
        adapterPhongHoc = new AdapterPhongHoc(this, R.layout.layout_phong_hoc, DSPH);
        lvPH.setAdapter(adapterPhongHoc);
    }

    private void themPhongHoc(int gravity,DBPhongHoc dbPhongHoc){
        final Dialog dialog = new Dialog(PhongHocActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_them_sua_ph);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //click ra bên ngoài để tắt dialog
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(false);
        }

        btnLuu = dialog.findViewById(R.id.btnLuu);
        btnThoat = dialog.findViewById(R.id.btnThoat);
        txtMaPhong = dialog.findViewById(R.id.txtMaPhong);
        txtLoaiPhong = dialog.findViewById(R.id.txtLoaiPhong);
        txtTang = dialog.findViewById(R.id.txtTang);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maPhong = txtMaPhong.getText().toString();
                String loaiPhong = txtLoaiPhong.getText().toString();
                String tang = txtTang.getText().toString();
                if(maPhong.equals("")){
                    Toast.makeText(PhongHocActivity.this, "Mã phòng không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if(loaiPhong.equals("")){
                    Toast.makeText(PhongHocActivity.this, "Loại phòng không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }if(tang.equals("")){
                    Toast.makeText(PhongHocActivity.this, "Tầng không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                dbPhongHoc.themPhongHoc(new PhongHoc(maPhong, loaiPhong, tang));
                loadListView(dbPhongHoc);
                dialog.dismiss();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
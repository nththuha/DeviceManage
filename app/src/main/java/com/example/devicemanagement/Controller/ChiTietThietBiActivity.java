package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devicemanagement.Adapter.AdapterTenLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class ChiTietThietBiActivity extends AppCompatActivity {
    Spinner spTenloaiTB;
    EditText txtMaTB, txtTenTB, txtXuatxu, txtSoluong;
    Button btnLuuTB;
    DBThietBi dbThietBi;
    AdapterTenLoaiThietBi adapterTenLoaiThietBi;
    String maloaiTB;
    ImageView imbBack;
    TextView tvTieuDeTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thiet_bi);
        setControl();
        setEvent();
        loadDL();
    }
    private ArrayList<LoaiThietBi> getMaLoaiTB(){
        DBLoaiThietBi dbLoaiThietBi = new DBLoaiThietBi(this);
        return dbLoaiThietBi.layDanhSachLTB();
    }

    private void loadDL(){
        Intent intent = getIntent();
        ThietBi thietBi = (ThietBi) intent.getSerializableExtra("thietbi");
        txtMaTB.setText(thietBi.getMaThietBi());
        txtTenTB.setText(thietBi.getTenThietBi());
        txtXuatxu.setText(thietBi.getXuatXu());
        txtSoluong.setText(thietBi.getSoLuong());
        tvTieuDeTB.setText("CHI TIẾT THIẾT BỊ: "+thietBi.getTenThietBi());
        adapterTenLoaiThietBi = new AdapterTenLoaiThietBi(getApplicationContext(),R.layout.item_selected_spinnermaloaitb,getMaLoaiTB());
        spTenloaiTB.setAdapter(adapterTenLoaiThietBi);

        DBLoaiThietBi dbLoaiThietBi = new DBLoaiThietBi(this);
        ArrayList<LoaiThietBi> loaiThietBis = dbLoaiThietBi.layDanhSachLTB();
        for(int i =0;i<loaiThietBis.size();i++){
            if(loaiThietBis.get(i).getMaLoai().trim().equals(thietBi.getMaLoai().trim())){
                spTenloaiTB.setSelection(i);
            }
        }
    }

    private void setEvent() {
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spTenloaiTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maloaiTB = adapterTenLoaiThietBi.getItem(i).getMaLoai().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnLuuTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maTB = txtMaTB.getText().toString();
                String tenTB = txtTenTB.getText().toString();
                String xuatxu = txtXuatxu.getText().toString();
                String soluong = txtSoluong.getText().toString();

                if(maTB.equals("")){
                    Toast.makeText(ChiTietThietBiActivity.this, "Mã thiết bị không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tenTB.equals("")){
                    Toast.makeText(ChiTietThietBiActivity.this, "Tên thiết bị không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }if(xuatxu.equals("")){
                    Toast.makeText(ChiTietThietBiActivity.this, "Xuất xứ không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }if(soluong.equals("")){
                    Toast.makeText(ChiTietThietBiActivity.this, "Số lượg không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println("maTB"+maTB);
                System.out.println("tenTB"+tenTB);
                System.out.println("xuatxu"+xuatxu);
                System.out.println("soluong"+soluong);
                System.out.println("maloaitb"+maloaiTB);
                dbThietBi.suaThietBi(new ThietBi(maTB,tenTB, xuatxu, soluong,maloaiTB));
                Toast.makeText(ChiTietThietBiActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                tvTieuDeTB.setText("CHI TIẾT THIẾT BỊ: "+tenTB);
            }
        });

    }

    private void setControl() {
        dbThietBi = new DBThietBi(this);
        txtMaTB = findViewById(R.id.txtMaTB);
        txtTenTB = findViewById(R.id.txtTenTB);
        txtXuatxu = findViewById(R.id.txtXuatxu);
        txtSoluong = findViewById(R.id.txtSoluongTB);
        txtTenTB = findViewById(R.id.txtTenTB);
        btnLuuTB = findViewById(R.id.btnLuuTB);
        spTenloaiTB = findViewById(R.id.spTenloaiTB);
        imbBack = findViewById(R.id.imbBackCTTB);
        tvTieuDeTB = findViewById(R.id.tvTieuDeTB);
    }
}
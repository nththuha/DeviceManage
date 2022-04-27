package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.devicemanagement.Adapter.AdapterTenLoaiThietBi;
import com.example.devicemanagement.Adapter.AdapterThietBi;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class ThietBiActivity extends AppCompatActivity {
    ImageButton imbBack,imbThemTB;
    DBThietBi dbThietBi;
    ArrayList<ThietBi> thietBis;
    AdapterThietBi adapterThietBi;
    AdapterTenLoaiThietBi adapterTenLoaiThietBi;
    ArrayList<ThietBi> filter;
    ListView lvTb;
    Spinner snTB, spTenloaiTB;
    EditText txtMaTB, txtTenTB, txtXuatxu, txtSoluong;
    Button btnThoatTB, btnLuuTB;
    String maloaiTB;
    SearchView svTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi);
        setControl();
        setEvent();
    }
    private void setEvent() {
        loadListView(dbThietBi);
//        adapterMaLoaiThietBi = new AdapterMaLoaiThietBi(this,R.layout.item_selected_spinnermaloaitb,getMaLoaiTB());
//        snTB.setAdapter(adapterMaLoaiThietBi);

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imbThemTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themThietBi(Gravity.CENTER, dbThietBi);
            }
        });

        svTB.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getFilter(newText);
                return false;
            }
        });
    }

    private void themThietBi(int gravity, DBThietBi dbThietBi) {
        final Dialog dialog = new Dialog(ThietBiActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_them_thietbi);
        Window window = dialog.getWindow();

        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }
        txtMaTB = dialog.findViewById(R.id.txtMaTB);
        txtTenTB = dialog.findViewById(R.id.txtTenTB);
        txtXuatxu = dialog.findViewById(R.id.txtXuatxu);
        txtSoluong = dialog.findViewById(R.id.txtSoluongTB);
        txtTenTB = dialog.findViewById(R.id.txtTenTB);
        btnThoatTB = dialog.findViewById(R.id.btnThoatTB);
        btnLuuTB = dialog.findViewById(R.id.btnLuuTB);
        spTenloaiTB = dialog.findViewById(R.id.spTenloaiTB);
        adapterTenLoaiThietBi = new AdapterTenLoaiThietBi(getApplicationContext(),R.layout.item_selected_spinnermaloaitb,getMaLoaiTB());
        spTenloaiTB.setAdapter(adapterTenLoaiThietBi);
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
                    Toast.makeText(ThietBiActivity.this, "Mã thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if(tenTB.equals("")){
                    Toast.makeText(ThietBiActivity.this, "Tên thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }if(xuatxu.equals("")){
                    Toast.makeText(ThietBiActivity.this, "Xuất xứ không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }if(soluong.equals("")){
                    Toast.makeText(ThietBiActivity.this, "Số lượg không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                dbThietBi.themThietBi(new ThietBi(maTB, tenTB, xuatxu,soluong,maloaiTB));
                loadListView(dbThietBi);
                dialog.dismiss();
            }
        });
        btnThoatTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private ArrayList<LoaiThietBi> getMaLoaiTB(){
        DBLoaiThietBi dbLoaiThietBi = new DBLoaiThietBi(this);
        return dbLoaiThietBi.layDanhSachLTB();
    }

    private void getFilter(String s){
        filter = new ArrayList<>();
        for (ThietBi tb: thietBis) {
            if(tb.getTenThietBi().toLowerCase().contains(s)){
                filter.add(tb);
            }
        }
        adapterThietBi.setFilterList(filter);
        if(filter.isEmpty()){
            //Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không có dữ liệu để hiển thị!")
                    .setCancelable(true)
                    .show();
        }
    }

    public void loadListView(DBThietBi dbThietBi){
        thietBis = dbThietBi.layDSThietBi();
        adapterThietBi = new AdapterThietBi(this, R.layout.layout_thiet_bi, thietBis);
        lvTb.setAdapter(adapterThietBi);
    }

    private void setControl() {
        dbThietBi = new DBThietBi(this);
//        ThietBi tb = new ThietBi("TB1","Loa","Anh","5","DC");
//        dbThietBi.themThietBi(tb);
        lvTb = findViewById(R.id.lvTB);
        snTB = findViewById(R.id.snTB);
        imbBack = findViewById(R.id.imbBackTB);
        svTB = findViewById(R.id.svTB);
        imbThemTB = findViewById(R.id.imbThemTB);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView(dbThietBi);
    }
}
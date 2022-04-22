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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.devicemanagement.Adapter.AdapterLoaiPhong;
import com.example.devicemanagement.Adapter.AdapterLoaiThietBi;
import com.example.devicemanagement.Adapter.AdapterNhanVien;
import com.example.devicemanagement.Adapter.AdapterPhongHoc;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.Entity.LoaiPhong;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class PhongHocActivity extends AppCompatActivity {
    ListView lvPH;
    ImageButton imbBack, imbThemPH, imbSua, imbXoa;
    SearchView svPH;
    Button btnLuu, btnThoat;
    EditText txtMaPhong, txtLoaiPhong, txtTang;
    Spinner snPH;
    AdapterPhongHoc adapterPhongHoc;
    AdapterLoaiPhong adapterLoaiPhong;
    ArrayList<PhongHoc> DSPH;
    DBPhongHoc dbPhongHoc;
    ArrayList<PhongHoc> filter;

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

        adapterLoaiPhong = new AdapterLoaiPhong(this, R.layout.item_selected_spinner, getloaiPhong());
        snPH.setAdapter(adapterLoaiPhong);
        snPH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String loai = adapterLoaiPhong.getItem(i).getLoai();
                if(!loai.equals("Chọn loại phòng")){
                    if (loai.equals("Lý thuyết")) {
                        filter = new ArrayList<>();
                        for (PhongHoc ph : DSPH) {
                            if (ph.getLoaiPhong().equals("Lý thuyết")) {
                                filter.add(ph);
                            }
                        }
                        adapterPhongHoc.setFilterList(filter);
                    } else {
                        filter = new ArrayList<>();
                        for (PhongHoc ph : DSPH) {
                            if (ph.getLoaiPhong().equals("Thực hành")) {
                                filter.add(ph);
                            }
                        }
                        adapterPhongHoc.setFilterList(filter);
                    }
                }
                else{
                    loadListView(dbPhongHoc);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        svPH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getFilter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getFilter(s);
                return false;
            }
        });
    }

    private List getloaiPhong() {
        List<LoaiPhong> LP = new ArrayList<>();
        LP.add(new LoaiPhong("Chọn loại phòng"));
        LP.add(new LoaiPhong("Lý thuyết"));
        LP.add(new LoaiPhong("Thực hành"));
        return LP;
    }

    private void getFilter(String s){
        filter = new ArrayList<>();
        for (PhongHoc ph: DSPH) {
            if(ph.getMaPhong().toLowerCase().contains(s)){
                filter.add(ph);
            }
        }
        adapterPhongHoc.setFilterList(filter);
        if(filter.isEmpty()){
            Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl() {
        lvPH = findViewById(R.id.lvPH);
        imbBack = findViewById(R.id.imbBack);
        imbThemPH = findViewById(R.id.imbThemPH);
        imbSua = findViewById(R.id.imbSua);
        imbXoa = findViewById(R.id.imbXoa);
        svPH = findViewById(R.id.svPH);
        snPH = findViewById(R.id.snPH);
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
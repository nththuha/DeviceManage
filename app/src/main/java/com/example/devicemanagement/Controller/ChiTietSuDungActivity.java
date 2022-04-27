package com.example.devicemanagement.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devicemanagement.Adapter.AdapterChiTietSuDung;
import com.example.devicemanagement.Adapter.AdapterMaPhong;
import com.example.devicemanagement.Adapter.AdapterThietBi;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ChiTietSuDungActivity extends AppCompatActivity{
    ArrayList<ThietBi> thietBis;
    ArrayList<ThietBi> filter;
    AdapterChiTietSuDung adapterCTSD;
    AdapterMaPhong adapterMaPhong;
    DBThietBi dbThietBi;
    ListView lvCTSuDung;
    ImageButton imbBack;
    SearchView svCTSD;
    Spinner spMaPhong;
    String maPhong = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sudung);
        setControl();
        setEvent();
    }
    private void setEvent() {
        dbThietBi = new DBThietBi(this);
        loadListView(dbThietBi);
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvCTSuDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lvCTSuDung.setBackgroundResource(R.drawable.activity_onclick_item);
                dialogMuonTra(Gravity.CENTER,i);
            }
        });
        svCTSD.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    //search
    private void getFilter(String s) {
        filter = new ArrayList<>();
        for (ThietBi tb : thietBis){
            if(tb.getTenThietBi().toLowerCase().contains(s))
                filter.add(tb);
        }
        adapterCTSD.setFilterList(filter);
        if (filter.isEmpty()){
            //Toast.makeText(this, "Không có dữ liệu để hiển thị!", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không có dữ liệu để hiển thị!")
                    .setCancelable(true)
                    .show();
        }

    }

    public void loadListView(DBThietBi dbThietBi) {
        thietBis = dbThietBi.layDSThietBi();
        adapterCTSD = new AdapterChiTietSuDung(this,R.layout.activity_item_chitiet_sudung,thietBis);
        lvCTSuDung.setAdapter(adapterCTSD);
        adapterCTSD.notifyDataSetChanged();
    }

    private void setControl() {
        imbBack = findViewById(R.id.imbBack);
        lvCTSuDung = findViewById(R.id.lvCTSuDung);
        svCTSD = findViewById(R.id.svCTSD);
    }

    private void dialogMuonTra(int gravity, int i) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_muon_tra);

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

        Button btnMuonTB = dialog.findViewById(R.id.btnMuonTB);
        Button btnTraTB = dialog.findViewById(R.id.btnTraTB);
        ImageButton imbClose = dialog.findViewById(R.id.imbClose);

        btnMuonTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMuonThietBi(Gravity.CENTER,i);
            }
        });
        btnTraTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTraThietBi(Gravity.CENTER,i);
            }
        });
        imbClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void dialogMuonThietBi(int gravity, int i) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_muon_thietbi);

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
        TextView tvMaTB = dialog.findViewById(R.id.tvMaTB);
        Button btnMuon = dialog.findViewById(R.id.btnMuon);
        Button btnHuyMT = dialog.findViewById(R.id.btnHuyMT);
        spMaPhong = dialog.findViewById(R.id.spMaPhong);
        tvMaTB.setText(thietBis.get(i).getMaThietBi());
        adapterMaPhong = new AdapterMaPhong(this,R.layout.item_selected_spinner,getMaPhong());
        spMaPhong.setAdapter(adapterMaPhong);
        spMaPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maPhong = adapterMaPhong.getItem(i).getMaPhong().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnHuyMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private ArrayList<PhongHoc> getMaPhong() {
        DBPhongHoc dbPhongHoc = new DBPhongHoc(this);
        return dbPhongHoc.layDSPhongHoc();
    }

    private void dialogTraThietBi(int gravity, int i) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_muon_thietbi);

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

        TextView tvMaTB = dialog.findViewById(R.id.tvMaTB);
        TextView tvTitleMuonTB = dialog.findViewById(R.id.tvTitleMuonTB);
        Button btnTra = dialog.findViewById(R.id.btnMuon);
        spMaPhong = dialog.findViewById(R.id.spMaPhong);
        Button btnHuyMT = dialog.findViewById(R.id.btnHuyMT);
        tvTitleMuonTB.setText("Trả thiết bị theo phòng");
        tvMaTB.setText(thietBis.get(i).getMaThietBi());
        btnTra.setText("Trả");
        adapterMaPhong = new AdapterMaPhong(this,R.layout.item_selected_spinner,getMaPhong());
        spMaPhong.setAdapter(adapterMaPhong);
        spMaPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maPhong = adapterMaPhong.getItem(i).getMaPhong().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnHuyMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
package com.example.devicemanagement.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devicemanagement.Adapter.AdapterChiTietSuDung;
import com.example.devicemanagement.Adapter.AdapterMaPhong;
import com.example.devicemanagement.Adapter.AdapterMaThietBi;
import com.example.devicemanagement.Adapter.AdapterThietBi;
import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.ChiTietSD;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ChiTietSuDungActivity extends AppCompatActivity{
    Date date;
    long millis = System.currentTimeMillis();
    ArrayList<ChiTietSD> chiTietSDs;
    ArrayList<ChiTietSD> filter;
    ArrayList<ThietBi> thietBis;
    AdapterChiTietSuDung adapterCTSD;
    AdapterMaPhong adapterMaPhong;
    AdapterMaThietBi adapterMaThietBi;
    DBChiTietSD dbChiTietSD;
    DBThietBi dbThietBi;
    ListView lvCTSuDung;
    ImageButton imbBack;
    Button btnMuonCTTB, btnTraCTTB, btnMuon, btnTra, btnHuyMT;
    SearchView svCTSD;
    Spinner spMaPhong, spMaThietBi;
    String maPhong = "";
    String maThietBi = "";
    Integer slmuon = 0, tongsl = 0, sldu = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sudung);
        setControl();
        setEvent();
    }
    private void setEvent() {
        dbThietBi = new DBThietBi(this);
        dbChiTietSD = new DBChiTietSD(this);
        loadListView(dbChiTietSD);
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
                dialogChiTietMuon(Gravity.CENTER,i, dbThietBi);
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
        btnMuonCTTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMuonThietBi(Gravity.CENTER, dbChiTietSD, dbThietBi);
            }
        });
        btnTraCTTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialogTraThietBi(Gravity.CENTER, dbChiTietSD, dbThietBi);
            }
        });
    }
    //search
    private void getFilter(String s) {
        filter = new ArrayList<>();
        for (ChiTietSD ctsd : chiTietSDs){
            if(ctsd.getMaThietBi().toLowerCase().contains(s.toLowerCase())){
                filter.add(ctsd);
            }
            if(ctsd.getMaPhong().toLowerCase().contains(s.toLowerCase())){
                filter.add(ctsd);
            }
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
    public void loadListView(DBChiTietSD dbChiTietSD) {
        chiTietSDs = dbChiTietSD.layDSChiTietSD();
        adapterCTSD = new AdapterChiTietSuDung(this,R.layout.activity_item_chitiet_sudung,chiTietSDs);
        lvCTSuDung.setAdapter(adapterCTSD);
        adapterCTSD.notifyDataSetChanged();
    }
    private void setControl() {
        imbBack = findViewById(R.id.imbBack);
        lvCTSuDung = findViewById(R.id.lvCTSuDung);
        svCTSD = findViewById(R.id.svCTSD);
        btnMuonCTTB = findViewById(R.id.btnMuonCTTB);
        btnTraCTTB = findViewById(R.id.btnTraCTTB);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView(dbChiTietSD);
    }

    private ArrayList<PhongHoc> getMaPhong() {
        DBPhongHoc dbPhongHoc = new DBPhongHoc(this);
        return dbPhongHoc.layDSPhongHoc();
    }
    private ArrayList<ThietBi> getMaThietBi() {
        DBThietBi dbThietBi = new DBThietBi(this);
        return dbThietBi.layDSThietBi();
    }
    private void dialogChiTietMuon(int gravity, int i, DBThietBi dbThietBi) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_chitiet_sudung);

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
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(true);
        }
        TextView tvMaPCTmuon = dialog.findViewById(R.id.tvMaPCTmuon);
        TextView tvMaTBCTmuon = dialog.findViewById(R.id.tvMaTBCTmuon);
        TextView tvSoLuongCTmuon = dialog.findViewById(R.id.tvSoLuongCTmuon);
        TextView tvSoLuongCTconlai = dialog.findViewById(R.id.tvSoLuongCTconlai);
        TextView tvNgayCTmuon = dialog.findViewById(R.id.tvNgayCTmuon);

        tvMaPCTmuon.setText(chiTietSDs.get(i).getMaPhong());
        tvMaTBCTmuon.setText(chiTietSDs.get(i).getMaThietBi());
        tvSoLuongCTmuon.setText(chiTietSDs.get(i).getSoLuong());
        tvNgayCTmuon.setText(chiTietSDs.get(i).getNgaySuDung());

        Integer slmuon = Integer.parseInt(tvSoLuongCTmuon.getText().toString().trim());
        Integer sltong = Integer.parseInt(dbThietBi.laySLThietBi(chiTietSDs.get(i).getMaThietBi().toString().trim()));
        Integer sldu = sltong - slmuon;

        tvSoLuongCTconlai.setText(sldu.toString());
        dialog.show();

    }
    private void dialogMuonThietBi(int gravity, DBChiTietSD dbChiTietSD,DBThietBi dbThietBi) {
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
        //false = no; true = yes
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(false);
        }
        spMaPhong = dialog.findViewById(R.id.spMaPhong);
        spMaThietBi = dialog.findViewById(R.id.spMaThietBi);
        TextView tvNgay = dialog.findViewById(R.id.tvNgay);
        TextView tvSoLuongDu = dialog.findViewById(R.id.tvSoLuongDu);
        EditText txtSoLuongSD = dialog.findViewById(R.id.txtSoLuongSD);

        btnMuon = dialog.findViewById(R.id.btnMuon);
        btnHuyMT = dialog.findViewById(R.id.btnHuyMT);


        date = new Date(millis);
        tvNgay.setText(date.toString());
//        tongsl = dbThietBi.laySLThietBi(maThietBi);
        //----------------------------spinner------------------------------
        adapterMaPhong = new AdapterMaPhong(this,R.layout.spinner_maphong,getMaPhong());
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
        adapterMaThietBi = new AdapterMaThietBi(this,R.layout.spinner_mathietbi,getMaThietBi());
        spMaThietBi.setAdapter(adapterMaThietBi);
        spMaThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maThietBi = adapterMaThietBi.getItem(i).getMaThietBi().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //----------------------------end_spinner------------------------------
        btnMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String SoLuong = txtSoLuongSD.getText().toString().trim();

                /*if (TextUtils.isEmpty(SoLuong)){
                    txtSoLuongSD.requestFocus();
                    txtSoLuongSD.setError(view.getResources().getString(R.string.erorr_soLuongTrong));
                }
                if((Integer.parseInt(SoLuong) > tongsl)){
                    txtSoLuongSD.requestFocus();
                    txtSoLuongSD.setError(view.getResources().getString(R.string.erorr_soLuongMuon + tongsl));
                }*/


                slmuon = Integer.parseInt(SoLuong);
                sldu = tongsl - slmuon;

                thongBaoThanhCong(Gravity.CENTER);
                dbChiTietSD.themChiTietSD(new ChiTietSD(tvNgay.getText().toString(),SoLuong,maPhong,maThietBi));
                loadListView(dbChiTietSD);
                txtSoLuongSD.setText("");
                dialog.show();
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

    private void thongBaoThanhCong(int gravity) {
            //xử lý vị trí của dialog
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

            //click ra bên ngoài để tắt dialog
            if (Gravity.CENTER == gravity) {
                dialog.setCancelable(true);
            } else {
                dialog.setCancelable(true);
            }
            TextView tvThongBao = dialog.findViewById(R.id.tvThongBao);
            tvThongBao.setText("Mượn thiết bị thành công!");
            dialog.show();

    }
    /*private void dialogTraThietBi(int gravity, DBChiTietSD dbChiTietSD) {
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

    }*/
}
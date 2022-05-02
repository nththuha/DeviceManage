package com.example.devicemanagement.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.LinearLayout;
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
    Context context;
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
    DBPhongHoc dbPhongHoc;
    ChiTietSD chiTietSD;
    ThietBi thietBi;
    ListView lvCTSuDung;
    ImageButton imbBack;
    Button btnMuonCTTB, btnMuon, btnTra,btnHuyT, btnHuyM;
    EditText txtSoLuongTra;
    TextView tvSoLuongM;
    SearchView svCTSD;
    Spinner spMaPhong, spMaThietBi;
    String maPhong = "";
    String maThietBi = "";
    int tongsl = 0, sldu = 0, slmuon = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sudung);
        context = this;
        setControl();
        setEvent();
    }
    private void setEvent() {
        dbThietBi = new DBThietBi(this);
        dbChiTietSD = new DBChiTietSD(this);
        dbPhongHoc = new DBPhongHoc(this);
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
                dialogChiTietMuon(Gravity.CENTER,i, dbThietBi, dbPhongHoc);
            }
        });
        lvCTSuDung.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogTraThietBi(Gravity.CENTER,i,dbChiTietSD,dbThietBi);
                return true;
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
                dialogMuonThietBi(Gravity.CENTER,dbChiTietSD, dbThietBi);
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
            /*if(ctsd.getMaPhong().toLowerCase().contains(s.toLowerCase())){
                filter.add(ctsd);
            }*/
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
    private void dialogChiTietMuon(int gravity, int i, DBThietBi dbThietBi, DBPhongHoc dbPhongHoc) {
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
        TextView tvLoaiPCTmuon = dialog.findViewById(R.id.tvLoaiPCTmuon);
        TextView tvMaTBCTmuon = dialog.findViewById(R.id.tvMaTBCTmuon);
        TextView tvTenTBCTmuon = dialog.findViewById(R.id.tvTenTBCTmuon);
        TextView tvSoLuongCTmuon = dialog.findViewById(R.id.tvSoLuongCTmuon);
        TextView tvSoLuongCTconlai = dialog.findViewById(R.id.tvSoLuongCTconlai);
        TextView tvNgayCTmuon = dialog.findViewById(R.id.tvNgayCTmuon);

        tvMaPCTmuon.setText(chiTietSDs.get(i).getMaPhong());
        tvLoaiPCTmuon.setText(dbPhongHoc.layLoaiPhong(chiTietSDs.get(i).getMaPhong()));
        tvMaTBCTmuon.setText(chiTietSDs.get(i).getMaThietBi());
        tvTenTBCTmuon.setText(dbThietBi.layTenThietBi(chiTietSDs.get(i).getMaThietBi()));
        tvSoLuongCTmuon.setText(chiTietSDs.get(i).getSoLuong());
        tvNgayCTmuon.setText(chiTietSDs.get(i).getNgaySuDung());

        int tongsl = Integer.parseInt(dbThietBi.laySLThietBi(chiTietSDs.get(i).getMaThietBi()));
        int slmuon = Integer.parseInt(dbChiTietSD.layTongSLMuonMatb(chiTietSDs.get(i).getMaThietBi()));
        int sldu = tongsl - slmuon;

        tvSoLuongCTconlai.setText(String.valueOf(sldu));
        dialog.show();

    }
    private void dialogMuonThietBi(int gravity, DBChiTietSD dbChiTietSD,DBThietBi dbThietBi) {
        chiTietSDs = new ArrayList<>();
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
        spMaPhong = dialog.findViewById(R.id.spMaPhongM);
        spMaThietBi = dialog.findViewById(R.id.spMaThietBiM);
        LinearLayout llSoLuongMuon = dialog.findViewById(R.id.llSoLuongM);
        TextView tvNgay = dialog.findViewById(R.id.tvNgayM);
        TextView tvSoLuongDu = dialog.findViewById(R.id.tvSoLuongDu);
        EditText txtSoLuongSD = dialog.findViewById(R.id.txtSoLuongSD);

        btnMuon = dialog.findViewById(R.id.btnMuon);
        btnHuyM = dialog.findViewById(R.id.btnHuyM);


        date = new Date(millis);
        tvNgay.setText(date.toString());
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
        adapterMaThietBi = new AdapterMaThietBi(getApplication(),R.layout.spinner_mathietbi,getMaThietBi());
        spMaThietBi.setAdapter(adapterMaThietBi);
        spMaThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maThietBi = adapterMaThietBi.getItem(i).getMaThietBi();
                tongsl = Integer.parseInt(dbThietBi.laySLThietBi(maThietBi));
                slmuon = Integer.parseInt(dbChiTietSD.layTongSLMuonMatb(maThietBi));
                sldu = tongsl - slmuon;
                tvSoLuongDu.setText("/"+sldu);
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
                    txtSoLuongSD.setError(context.getResources().getString(R.string.erorr_soLuongTrong));
                }
                if((Integer.parseInt(SoLuong) > sldu)){
                    txtSoLuongSD.requestFocus();
                    txtSoLuongSD.setError(context.getResources().getString(R.string.erorr_soLuongMuon + sldu));
                }*/

                thongBaoThanhCong(Gravity.CENTER,"Mượn thiết bị thành công!");
                /*for (ChiTietSD chiTietSD: chiTietSDs) {
                    if((maPhong == chiTietSD.getMaPhong()) && (maThietBi == chiTietSD.getMaThietBi()) && (date.toString() == chiTietSD.getNgaySuDung())){
                        int tongslmuon = Integer.parseInt(chiTietSD.getSoLuong().trim()) + Integer.parseInt(SoLuong);
                        dbChiTietSD.suaChiTietSD(new ChiTietSD(maPhong,maThietBi,tvNgay.getText().toString(),String.valueOf(tongslmuon)));

                    }
                    else
                        dbChiTietSD.themChiTietSD(new ChiTietSD(maPhong,maThietBi,tvNgay.getText().toString(),SoLuong));
                        loadListView(dbChiTietSD);
                        txtSoLuongSD.setText("");
                        tvSoLuongDu.setText("/"+sldu);
                        dialog.show();
                }*/
                dbChiTietSD.themChiTietSD(new ChiTietSD(maPhong,maThietBi,tvNgay.getText().toString(),SoLuong));
                int slmoi = Integer.parseInt(dbThietBi.laySLThietBi(maThietBi)) - Integer.parseInt(dbChiTietSD.layTongSLMuonMatb(maThietBi));
                tvSoLuongDu.setText("/"+slmoi);
                txtSoLuongSD.setText("");
                loadListView(dbChiTietSD);
                dialog.show();

            }
        });
        btnHuyM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void dialogTraThietBi(int gravity,int i, DBChiTietSD dbChiTietSD, DBThietBi dbThietBi) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_tra_thietbi);

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

        LinearLayout llSoLuongTra = dialog.findViewById(R.id.llSoLuongT);
        TextView tvMaPhong = dialog.findViewById(R.id.tvMaPhongT);
        TextView tvMaThietBi = dialog.findViewById(R.id.tvMaThietBiT);
        TextView tvNgayMuon = dialog.findViewById(R.id.tvNgayMuon);
        txtSoLuongTra = dialog.findViewById(R.id.txtSoLuongTra);
        tvSoLuongM = dialog.findViewById(R.id.tvSoLuongM);
        btnTra = dialog.findViewById(R.id.btnTra);
        btnHuyT = dialog.findViewById(R.id.btnHuyT);

        tvMaPhong.setText(chiTietSDs.get(i).getMaPhong());
        tvMaThietBi.setText(chiTietSDs.get(i).getMaThietBi());
        tvNgayMuon.setText(chiTietSDs.get(i).getNgaySuDung());
        tvSoLuongM.setText("/" + chiTietSDs.get(i).getSoLuong());
        btnTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThiYN(Gravity.CENTER,i,dbChiTietSD,dbThietBi);
                dialog.dismiss();
            }
        });
        btnHuyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void thongBaoThanhCong(int gravity, String text) {
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
        tvThongBao.setText(text);
        dialog.show();

    }
    private void hienThiYN(int gravity,int i, DBChiTietSD dbChiTietSD, DBThietBi dbThietBi) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_logout);

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
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        Button btnY = dialog.findViewById(R.id.btnYes);
        Button btnN = dialog.findViewById(R.id.btnNo);
        tvTitle.setText("Bạn thật sự muốn trả "+ dbThietBi.layTenThietBi(chiTietSDs.get(i).getMaThietBi())+"?");

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sltra = Integer.parseInt(txtSoLuongTra.getText().toString().trim());
                int slmuon = Integer.parseInt(chiTietSDs.get(i).getSoLuong());
                slmuon = slmuon - sltra;
                if(slmuon == 0){
                    dbChiTietSD.xoaChiTietSD(chiTietSDs.get(i).getMaPhong(),chiTietSDs.get(i).getMaThietBi());
                }
                else{
                    dbChiTietSD.suaChiTietSD(new ChiTietSD(chiTietSDs.get(i).getMaPhong(),chiTietSDs.get(i).getMaThietBi(),chiTietSDs.get(i).getNgaySuDung(),String.valueOf(slmuon)));
                }
                thongBaoThanhCong(Gravity.CENTER,"Trả thiết bị thành công!");
                loadListView(dbChiTietSD);
                txtSoLuongTra.setText("");
                tvSoLuongM.setText("/" + slmuon);
                dialog.dismiss();
            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
package com.example.devicemanagement.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Controller.LoaiThietBiActivity;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class AdapterLoaiThietBi extends ArrayAdapter<LoaiThietBi> {
    Context context;
    int resource;
    ArrayList<LoaiThietBi> data;

    Button btnLuu, btnThoat, btnKhongXoa, btnDongYXoa;
    EditText txtMaLTB, txtTenLTB;
    TextView tvTieuDe, tvConfirmXoa;

    DBLoaiThietBi dbLoaiThietBi;

    public AdapterLoaiThietBi(@NonNull Context context, int resource, @NonNull ArrayList<LoaiThietBi> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvLoaiTB = convertView.findViewById(R.id.tvLoaiTB);
        TextView tvTenTB = convertView.findViewById(R.id.tvTenTB);
        ImageButton imbSua = convertView.findViewById(R.id.imbSua);
        ImageButton imbXoa = convertView.findViewById(R.id.imbXoa);

        LoaiThietBi loaiThietBi = data.get(position);
        tvLoaiTB.setText(loaiThietBi.getMaLoai());
        tvTenTB.setText(loaiThietBi.getTenLoai());

        imbSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaLTB(Gravity.CENTER, loaiThietBi.getMaLoai(), loaiThietBi.getTenLoai());
            }
        });

        imbXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaLTB(Gravity.CENTER, loaiThietBi.getMaLoai());
            }
        });

        return convertView;
    }

    private void suaLTB(int gravity, String maLoai, String tenLoai){
        dbLoaiThietBi = new DBLoaiThietBi(context);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_themltb);

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
        txtMaLTB = dialog.findViewById(R.id.txtMaLTB);
        txtTenLTB = dialog.findViewById(R.id.txtTenLTB);
        tvTieuDe = dialog.findViewById(R.id.tvTieuDe);

        tvTieuDe.setText("CHỈNH SỬA LOẠI THIẾT BỊ");

        txtMaLTB.setEnabled(false);
        txtMaLTB.setText(maLoai);
        txtTenLTB.setText(tenLoai);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maLTB = txtMaLTB.getText().toString();
                String tenLTB = txtTenLTB.getText().toString();
                if(maLTB.equals("")){
                    Toast.makeText(context, "Mã loại thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if(tenLTB.equals("")){
                    Toast.makeText(context, "Tên loại thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                dbLoaiThietBi.suaLTB(new LoaiThietBi(maLTB, tenLTB));
                dialog.dismiss();
                ((LoaiThietBiActivity)context).loadListView(dbLoaiThietBi);
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

    private void xoaLTB(int gravity, String maLoai){
        dbLoaiThietBi = new DBLoaiThietBi(context);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_xoaltb);

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

        btnKhongXoa = dialog.findViewById(R.id.btnKhongXoa);
        btnDongYXoa = dialog.findViewById(R.id.btnDongYXoa);
        tvConfirmXoa = dialog.findViewById(R.id.tvConfirmXoa);

        tvConfirmXoa.setText("Bạn có thật sự muốn xóa " + maLoai + "?");

        btnDongYXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbLoaiThietBi.xoaLTB(maLoai);
                dialog.dismiss();
                ((LoaiThietBiActivity)context).loadListView(dbLoaiThietBi);
            }
        });

        btnKhongXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

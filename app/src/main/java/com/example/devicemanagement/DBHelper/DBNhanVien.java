package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.NhanVien;

public class DBNhanVien extends SQLiteOpenHelper {
    public DBNhanVien(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE nhanvien (tendangnhap TEXT PRIMARY KEY, hoten TEXT, matkhau TEXT, mail TEXT, hinhanh TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS nhanvien");
        onCreate(db);
    }

    public void themNhanVien(NhanVien nhanVien){
        String sql = "Insert into nhanvien values (?,?,?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{nhanVien.getTenDangNhap(), nhanVien.getHoTen(), nhanVien.getMatKhau(), nhanVien.getMail(), nhanVien.getHinhAnh()});
        database.close();
    }
}

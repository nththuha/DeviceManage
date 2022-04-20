package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.NhanVien;

import java.util.ArrayList;

public class DBNhanVien extends SQLiteOpenHelper {
    public DBNhanVien(@Nullable Context context) {
        super(context, "DBNhanVien", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE nhanvien (tendangnhap text PRIMARY KEY, hoten TEXT, matkhau TEXT, mail TEXT, hinhanh TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS nhanvien");
//        onCreate(db);
    }

    public void themNhanVien(NhanVien nhanVien){
        String sql = "INSERT INTO nhanvien VALUES (?,?,?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{nhanVien.getTenDangNhap(), nhanVien.getHoTen(), nhanVien.getMatKhau(), nhanVien.getMail(), nhanVien.getHinhAnh()});
        database.close();
    }

    public ArrayList<NhanVien> layDSNhanVien(){
        ArrayList<NhanVien> data = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNhanVien(cursor.getString(0));
                nhanVien.setHoTen(cursor.getString(1));
                nhanVien.setMatKhau(cursor.getString(2));
                nhanVien.setMail(cursor.getString(3));
                nhanVien.setHinhAnh(cursor.getString(4));
                data.add(nhanVien);
            } while (cursor.moveToNext());
        }
        return data;
    }

    public NhanVien xetDangNhap(String tenDangNhap, String matKhau){
        String sql = "select * from nhanvien where tendangnhap = ? and matkhau = ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{tenDangNhap, matKhau});
        if(cursor.moveToFirst()){
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNhanVien(cursor.getString(0));
            nhanVien.setHoTen(cursor.getString(1));
            nhanVien.setMatKhau(cursor.getString(2));
            nhanVien.setMail(cursor.getString(3));
            nhanVien.setHinhAnh(cursor.getString(4));
            return nhanVien;
        }
        return null;
    }
}

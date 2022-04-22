package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.Entity.PhongHoc;

import java.util.ArrayList;

public class DBPhongHoc extends SQLiteOpenHelper {
    public DBPhongHoc(@Nullable Context context) {
        super(context, "DBPhongHoc", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE phonghoc (maphonghoc TEXT PRIMARY KEY, loaiphong TEXT, tang TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS phonghoc");
        onCreate(db);
    }

    public void themPhongHoc(PhongHoc phongHoc){
        String sql = "INSERT INTO phonghoc VALUES (?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{phongHoc.getMaPhong(), phongHoc.getLoaiPhong(), phongHoc.getTang()});
        database.close();
    }

    public void suaPhongHoc(PhongHoc phongHoc, String ma){
        String sql = "update phonghoc set maphonghoc=?, loaiphong=?, tang=? where maphonghoc=?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{phongHoc.getMaPhong(), phongHoc.getLoaiPhong(), phongHoc.getTang(), ma});
        database.close();
    }

    public void xoaPhongHoc(String maPhongHoc){
        String sql = "Delete from phonghoc where maphonghoc = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{maPhongHoc});
        database.close();
    }

    public ArrayList<PhongHoc> layDSPhongHoc(){
        ArrayList<PhongHoc> data = new ArrayList<>();
        String sql = "SELECT * FROM phonghoc";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                PhongHoc phongHoc = new PhongHoc();
                phongHoc.setMaPhong(cursor.getString(0));
                phongHoc.setLoaiPhong(cursor.getString(1));
                phongHoc.setTang(cursor.getString(2));
                data.add(phongHoc);
            } while (cursor.moveToNext());
        }
        return data;
    }
}

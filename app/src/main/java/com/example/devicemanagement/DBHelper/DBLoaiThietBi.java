package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.devicemanagement.Controller.LoaiThietBiActivity;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.NhanVien;

import java.util.ArrayList;

public class DBLoaiThietBi extends SQLiteOpenHelper {
    public DBLoaiThietBi(@Nullable Context context) {
        super(context, "DBLoaiThietBi", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE loaithietbi (maloai TEXT PRIMARY KEY, tenloai TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS loaithietbi");
//        onCreate(db);
    }

    public ArrayList<LoaiThietBi> layDanhSachLTB(){
        ArrayList<LoaiThietBi> data = new ArrayList<>();
        String sql = "SELECT * FROM loaithietbi";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                LoaiThietBi loaiThietBi = new LoaiThietBi();
                loaiThietBi.setMaLoai(cursor.getString(0));
                loaiThietBi.setTenLoai(cursor.getString(1));
                data.add(loaiThietBi);
            } while (cursor.moveToNext());
        }
        return data;
    }

    public void themLTB(LoaiThietBi loaiThietBi){
        String sql = "INSERT INTO loaithietbi VALUES (?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{loaiThietBi.getMaLoai(), loaiThietBi.getTenLoai()});
        database.close();
    }

    public void xoaLTB(String maLoai){
        String sql = "Delete from loaithietbi where maloai = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{maLoai});
        database.close();
    }

    public void suaLTB(LoaiThietBi loaiThietBi){
        String sql = "Update loaithietbi set tenloai = ? where maloai = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{loaiThietBi.getTenLoai(), loaiThietBi.getMaLoai()});
        database.close();
    }
}

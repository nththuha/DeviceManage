package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.ThietBi;

import java.util.ArrayList;

public class DBThietBi extends SQLiteOpenHelper {
    public DBThietBi(@Nullable Context context) {
        super(context, "DBThietBi", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE thietbi (matb TEXT PRIMARY KEY, tentb TEXT, xuatxu TEXT, soluong TEXT, " +
                "maloai TEXT, FOREIGN KEY(maloai) REFERENCES Loaithietbi(maloai) ON DELETE CASCADE ON UPDATE NO ACTION)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS thietbi");
        onCreate(db);
    }

    public ArrayList<ThietBi> layDSThietBi() {
        ArrayList<ThietBi> data = new ArrayList<>();
        String sql = "SELECT * FROM thietbi";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ThietBi thietBi = new ThietBi();
                thietBi.setMaThietBi(cursor.getString(0));
                thietBi.setTenThietBi(cursor.getString(1));
                thietBi.setXuatXu(cursor.getString(2));
                thietBi.setSoLuong(cursor.getString(3));
                thietBi.setMaLoai(cursor.getString(4));
                data.add(thietBi);
            } while (cursor.moveToNext());
        }
        return data;
    }

    public void themThietBi(ThietBi thietBi) {
        String sql = "INSERT INTO thietbi VALUES (?,?,?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{thietBi.getMaThietBi(), thietBi.getTenThietBi(), thietBi.getXuatXu(), thietBi.getSoLuong(), thietBi.getMaLoai()});
        database.close();
    }

    public void xoaThietBi(String matb) {
        String sql = "Delete from thietbi where matb = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{matb});
        database.close();
    }

    public void suaThietBi(ThietBi thietBi) {
        String sql = "update thietbi set tentb=?, xuatxu=?,soluong=?,maloai=? where matb=?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{thietBi.getTenThietBi(), thietBi.getXuatXu(), thietBi.getSoLuong(), thietBi.getMaLoai(), thietBi.getMaThietBi()});
        database.close();
    }
    public String laySLThietBi(String matb){
        String sql = "SELECT soluong FROM thietbi WHERE matb='" + matb+"'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst())
            return cursor.getString(0);
        return null;
    }

}

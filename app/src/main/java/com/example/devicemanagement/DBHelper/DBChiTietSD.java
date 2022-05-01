package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.devicemanagement.Entity.ChiTietSD;

import java.util.ArrayList;

public class DBChiTietSD extends SQLiteOpenHelper {
    public DBChiTietSD(@Nullable Context context) {
        super(context, "DBChiTietSD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE chitietsudung (ngaysudung TEXT, soluong TEXT, maphong TEXT, matb TEXT, PRIMARY KEY(maphong,matb)," +
                "FOREIGN KEY(maphong) REFERENCES Phonghoc(maphong) ON DELETE CASCADE ON UPDATE NO ACTION, " +
                "FOREIGN KEY(matb) REFERENCES Thietbi(matb) ON DELETE CASCADE ON UPDATE NO ACTION)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS chitietsudung");
        onCreate(db);
    }

    public ArrayList<ChiTietSD> layDSChiTietSD() {
        ArrayList<ChiTietSD> data = new ArrayList<>();
        String sql = "SELECT * FROM chitietsudung";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ChiTietSD chiTietSD = new ChiTietSD();
                chiTietSD.setMaPhong(cursor.getString(0));
                chiTietSD.setMaThietBi(cursor.getString(1));
                chiTietSD.setNgaySuDung(cursor.getString(2));
                chiTietSD.setSoLuong(cursor.getString(3));
                data.add(chiTietSD);
            } while (cursor.moveToNext());
        }
        return data;
    }
    public void themChiTietSD(ChiTietSD chiTietSD) {
        String sql = "INSERT INTO chitietsudung VALUES (?,?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{chiTietSD.getNgaySuDung(), chiTietSD.getSoLuong(), chiTietSD.getMaPhong(), chiTietSD.getMaThietBi()});
        database.close();
    }
    public void suaChiTietSD(ChiTietSD chiTietSD) {
        String sql = "update chitietsudung set ngaysudung=?, soluong=? where maphong=? and matb=?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{chiTietSD.getNgaySuDung(), chiTietSD.getSoLuong(), chiTietSD.getMaPhong(), chiTietSD.getMaThietBi()});
        database.close();
    }
    public void xoaCTSD(){
        String sql = "Delete from chitietsudung";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
        database.close();
    }
    public void xoaChiTietSD(String maphong,String matb) {
        String sql = "Delete from chitietthietbi where maphong=? and matb = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{maphong,matb});
        database.close();


    }
    public Integer laySLThietBi(String maphong, String matb){
        String sql = "SELECT soluong FROM chitietsudung WHERE maphong='" + maphong +"' and matb='" + matb+"'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null,null);
        if (cursor.moveToFirst())
            return Integer.parseInt(cursor.getString(0).toString().trim());
        return 0;
    }
}

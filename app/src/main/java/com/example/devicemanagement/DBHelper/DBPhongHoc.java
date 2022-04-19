package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBPhongHoc extends SQLiteOpenHelper {
    public DBPhongHoc(@Nullable Context context) {
        super(context, "DBPhongHoc", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE phonghoc (maphonghoc TEXT PRIMARY KEY, loaiphong TEXT, tang INTEGER, trangthai NUMERIC)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS phonghoc");
        onCreate(db);
    }
}

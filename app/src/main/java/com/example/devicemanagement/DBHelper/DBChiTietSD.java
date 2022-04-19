package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBChiTietSD extends SQLiteOpenHelper {
    public DBChiTietSD(@Nullable Context context) {
        super(context, "DBChiTietSD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE chitietsudung (ngaysudung NUMERIC, soluong INTEGER, maphong TEXT, matb TEXT, PRIMARY KEY(maphong,matb)," +
                "FOREIGN KEY(maphong) REFERENCES Phonghoc(maphong) ON DELETE CASCADE ON UPDATE NO ACTION, " +
                "FOREIGN KEY(matb) REFERENCES Thietbi(matb) ON DELETE CASCADE ON UPDATE NO ACTION)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS chitietsudung");
        onCreate(db);
    }
}

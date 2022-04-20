package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.util.Base64;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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

    public void xoaNhanVien(String tenDangNhap){
        String sql = "Delete from nhanvien where tendangnhap = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{tenDangNhap});
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NhanVien xetDangNhap(String tenDangNhap, String matKhau){
        try {
            matKhau = maHoa(matKhau);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String maHoa(String original) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String SECRET_KEY = "12345678";
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] byteEncrypted = cipher.doFinal(original.getBytes());
        String encrypted = null;
        encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
        return encrypted;
    }

    public NhanVien xetGuiMail(String tenDangNhap){
        String sql = "select * from nhanvien where tendangnhap = ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{tenDangNhap});
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

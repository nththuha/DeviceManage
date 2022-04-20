package com.example.devicemanagement.Entity;

import java.io.Serializable;
import java.util.Date;

public class ChiTietSD implements Serializable {
    String maPhong, maThietBi;
    Date ngaySuDung;
    int soLuong;

    public ChiTietSD(String maPhong, String maThietBi, Date ngaySuDung, int soLuong) {
        this.maPhong = maPhong;
        this.maThietBi = maThietBi;
        this.ngaySuDung = ngaySuDung;
        this.soLuong = soLuong;
    }

    public ChiTietSD() {
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }

    public Date getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(Date ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}

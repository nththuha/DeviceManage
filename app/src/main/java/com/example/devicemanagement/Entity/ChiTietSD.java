package com.example.devicemanagement.Entity;

import java.io.Serializable;
import java.util.Date;

public class ChiTietSD implements Serializable {
    String maPhong, maThietBi, ngaySuDung, soLuong;

    public ChiTietSD(String maPhong, String maThietBi, String ngaySuDung, String soLuong) {
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

    public String getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(String ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
}

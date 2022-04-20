package com.example.devicemanagement.Entity;

import java.io.Serializable;

public class PhongHoc implements Serializable {
    String maPhong, loaiPhong;
    int tang;

    public PhongHoc(String maPhong, String loaiPhong, int tang) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.tang = tang;
    }

    public PhongHoc() {
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }
}

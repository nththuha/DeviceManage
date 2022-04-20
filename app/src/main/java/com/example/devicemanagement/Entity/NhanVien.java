package com.example.devicemanagement.Entity;

import java.io.Serializable;

public class NhanVien implements Serializable {
    String tenDangNhap, hoTen, matKhau, mail, hinhAnh;

    public NhanVien(String tenDangNhap, String hoTen, String matKhau, String mail, String hinhAnh) {
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.mail = mail;
        this.hinhAnh = hinhAnh;
    }

    public NhanVien() {
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setMaNhanVien(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}

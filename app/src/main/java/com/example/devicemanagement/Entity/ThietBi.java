package com.example.devicemanagement.Entity;

public class ThietBi {
    String maThietBi, tenThietBi, xuatXu, maLoai;
    int soLuong;

    public ThietBi(String maThietBi, String tenThietBi, String xuatXu, String maLoai, int soLuong) {
        this.maThietBi = maThietBi;
        this.tenThietBi = tenThietBi;
        this.xuatXu = xuatXu;
        this.maLoai = maLoai;
        this.soLuong = soLuong;
    }

    public ThietBi() {
    }

    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}

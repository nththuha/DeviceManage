package com.example.devicemanagement.Entity;

import java.io.Serializable;

public class ThietBi implements Serializable {
    String maThietBi, tenThietBi, xuatXu, maLoai, soLuong;

    public ThietBi(String maThietBi, String tenThietBi, String xuatXu, String soLuong, String maLoai) {
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

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
}

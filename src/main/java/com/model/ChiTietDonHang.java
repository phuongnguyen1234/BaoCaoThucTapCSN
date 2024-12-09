package com.model;

public class ChiTietDonHang {
    private String maCaPhe;
    private String maDonHang;
    private String maNhanVien;
    private int soLuong;
    private String yeuCauDacBiet;
    private int tamTinh;

    public ChiTietDonHang(){}

    public ChiTietDonHang(String maCaPhe, String maDonHang, String maNhanVien, int soLuong, String yeuCauDacBiet, int tamTinh) {
        this.maCaPhe = maCaPhe;
        this.maDonHang = maDonHang;
        this.maNhanVien = maNhanVien;
        this.soLuong = soLuong;
        this.yeuCauDacBiet = yeuCauDacBiet;
        this.tamTinh = tamTinh;
    }

    public String getMaCaPhe() {
        return maCaPhe;
    }

    public void setMaCaPhe(String maCaPhe) {
        this.maCaPhe = maCaPhe;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getYeuCauDacBiet() {
        return yeuCauDacBiet;
    }

    public void setYeuCauDacBiet(String yeuCauDacBiet) {
        this.yeuCauDacBiet = yeuCauDacBiet;
    }

    public int getTamTinh() {
        return tamTinh;
    }

    public void setTamTinh(int tamTinh) {
        this.tamTinh = tamTinh;
    }
}

package com.model;

import java.time.LocalDateTime;

public class DonHang {
    private String maDonHang;
    private String maNhanVien;
    private LocalDateTime thoiGianDatHang;
    private int tongTien;

    public DonHang(){}

    public DonHang(String maDonHang, String maNhanVien, LocalDateTime thoiGianDatHang, int tongTien){
        this.maDonHang = maDonHang;
        this.maNhanVien = maNhanVien;
        this.thoiGianDatHang = thoiGianDatHang;
        this.tongTien = tongTien;
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

    public LocalDateTime getThoiGianDatHang() {
        return thoiGianDatHang;
    }

    public void setThoiGianDatHang(LocalDateTime thoiGianDatHang) {
        this.thoiGianDatHang = thoiGianDatHang;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}

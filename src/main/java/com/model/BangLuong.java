package com.model;

import java.time.YearMonth;

public class BangLuong {
    private String maBangLuong;
    private String maNhanVien;
    private YearMonth thang;
    private int soNgayCong;
    private int soNgayNghiCoCong;
    private int soNgaynghiKhongCong;
    private int soGioLamThem;
    private int thuongDoanhThu;
    private int luongThucNhan;
    private String ghiChu;
    private boolean duocPhepChinhSua;

    public BangLuong(){}

    public BangLuong(String maBangLuong, String maNhanVien, YearMonth thang, int soNgayCong, int soNgayNghiCoCong, int soNgaynghiKhongCong, int soGioLamThem, int thuongDoanhThu, int luongThucNhan, String ghiChu, boolean duocPhepChinhSua) {
        this.maBangLuong = maBangLuong;
        this.maNhanVien = maNhanVien;
        this.thang = thang;
        this.soNgayCong = soNgayCong;
        this.soNgayNghiCoCong = soNgayNghiCoCong;
        this.soNgaynghiKhongCong = soNgaynghiKhongCong;
        this.soGioLamThem = soGioLamThem;
        this.thuongDoanhThu = thuongDoanhThu;
        this.luongThucNhan = luongThucNhan;
        this.ghiChu = ghiChu;
        this.duocPhepChinhSua = duocPhepChinhSua;
    }

    public String getMaBangLuong() {
        return maBangLuong;
    }
    
    public void setMaBangLuong(String maBangLuong) {
        this.maBangLuong = maBangLuong;
    }
    
    public String getMaNhanVien() {
        return maNhanVien;
    }
    
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    
    public YearMonth getThang() {
        return thang;
    }
    
    public void setThang(YearMonth thang) {
        this.thang = thang;
    }
    
    public int getSoNgayCong() {
        return soNgayCong;
    }
    
    public void setSoNgayCong(int soNgayCong) {
        this.soNgayCong = soNgayCong;
    }
    
    public int getSoNgayNghiCoCong() {
        return soNgayNghiCoCong;
    }
    
    public void setSoNgayNghiCoCong(int soNgayNghiCoCong) {
        this.soNgayNghiCoCong = soNgayNghiCoCong;
    }
    
    public int getSoNgaynghiKhongCong() {
        return soNgaynghiKhongCong;
    }
    
    public void setSoNgaynghiKhongCong(int soNgaynghiKhongCong) {
        this.soNgaynghiKhongCong = soNgaynghiKhongCong;
    }
    
    public int getSoGioLamThem() {
        return soGioLamThem;
    }
    
    public void setSoGioLamThem(int soGioLamThem) {
        this.soGioLamThem = soGioLamThem;
    }
    
    public int getThuongDoanhThu() {
        return thuongDoanhThu;
    }
    
    public void setThuongDoanhThu(int thuongDoanhThu) {
        this.thuongDoanhThu = thuongDoanhThu;
    }
    
    public int getLuongThucNhan() {
        return luongThucNhan;
    }
    
    public void setLuongThucNhan(int luongThucNhan) {
        this.luongThucNhan = luongThucNhan;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public boolean getDuocPhepChinhSua() {
        return duocPhepChinhSua;
    }
    
    public void setDuocPhepChinhSua(boolean duocPhepChinhSua) {
        this.duocPhepChinhSua = duocPhepChinhSua;
    }
}

package com.dto;

public class CaPheDTO {
    private String maCaPhe;
    private String tenCaPhe;
    private byte[] anhMinhHoa;
    private int donGia;
    private String maDanhMuc;
    private String tenDanhMuc;
    private int soLuong;
    private String yeuCauDacBiet;
    private int tamTinh;

    public CaPheDTO(){}

    public CaPheDTO(String maCaPhe, String tenCaPhe, byte[] anhMinhHoa, int donGia, String maDanhMuc,
    String tenDanhMuc, int soLuong, String yeuCauDacBiet, int tamTinh) {
        this.maCaPhe = maCaPhe;
        this.tenCaPhe = tenCaPhe;
        this.anhMinhHoa = anhMinhHoa;
        this.donGia = donGia;
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
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

    public String getTenCaPhe() {
        return tenCaPhe;
    }

    public void setTenCaPhe(String tenCaPhe) {
        this.tenCaPhe = tenCaPhe;
    }

    public byte[] getAnhMinhHoa() {
        return anhMinhHoa;
    }

    public void setAnhMinhHoa(byte[] anhMinhHoa) {
        this.anhMinhHoa = anhMinhHoa;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
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

package com.model;

public class CaPhe {
    private String maCaPhe;
    private String tenCaPhe;
    private byte[] anhMinhHoa;
    private int donGia;
    private String maDanhMuc;

    public CaPhe(){}

    public CaPhe(String maCaPhe, String tenCaPhe, byte[] anhMinhHoa, int donGia, String maDanhMuc){
        this.maCaPhe = maCaPhe;
        this.tenCaPhe = tenCaPhe;
        this.anhMinhHoa = anhMinhHoa;
        this.donGia = donGia;
        this.maDanhMuc = maDanhMuc;
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
}

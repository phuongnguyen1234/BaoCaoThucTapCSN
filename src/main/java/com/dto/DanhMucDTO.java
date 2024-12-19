package com.dto;

import java.util.List;

import com.model.CaPhe;

public class DanhMucDTO {
    private String maDanhMuc;
    private String tenDanhMuc;
    private List<CaPhe> danhSachCaPhe;

    public DanhMucDTO(){}

    public DanhMucDTO(String maDanhMuc, String tenDanhMuc, List<CaPhe> danhSachCaPhe) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.danhSachCaPhe = danhSachCaPhe;
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

    public List<CaPhe> getDanhSachCaPhe() {
        return danhSachCaPhe;
    }

    public void setDanhSachCaPhe(List<CaPhe> danhSachCaPhe) {
        this.danhSachCaPhe = danhSachCaPhe;
    }
}

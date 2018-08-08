package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by dthtg on 11/23/2017.
 */

public class MonTheThao implements Serializable {
    private int id;
    private String TenMonTT;
    private String MoTa;
    private String HinhAnh;
    private int NangLuong;

    public MonTheThao(int id, String tenMonTT, String moTa, String hinhAnh, int nangLuong) {
        this.id = id;
        TenMonTT = tenMonTT;
        MoTa = moTa;
        HinhAnh = hinhAnh;
        NangLuong = nangLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMonTT() {
        return TenMonTT;
    }

    public void setTenMonTT(String tenMonTT) {
        TenMonTT = tenMonTT;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getNangLuong() {
        return NangLuong;
    }

    public void setNangLuong(int nangLuong) {
        NangLuong = nangLuong;
    }
}

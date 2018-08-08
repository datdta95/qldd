package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by dthtg on 11/20/2017.
 */

public class MonAn implements Serializable {
    private int id;
    private String TenMA;
    private String MoTa;
    private String HinhAnh;
    private int NangLuong;
    private int IDNhomMA;

    public MonAn(int id, String tenMA, String moTa, String hinhAnh, int nangLuong, int IDNhomMA) {
        this.id = id;
        TenMA = tenMA;
        MoTa = moTa;
        HinhAnh = hinhAnh;
        NangLuong = nangLuong;
        this.IDNhomMA = IDNhomMA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMA() {
        return TenMA;
    }

    public void setTenMA(String tenMA) {
        TenMA = tenMA;
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

    public int getIDNhomMA() {
        return IDNhomMA;
    }

    public void setIDNhomMA(int IDNhomMA) {
        this.IDNhomMA = IDNhomMA;
    }
}

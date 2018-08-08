package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by dthtg on 12/14/2017.
 */

public class LichsuChitietMonAn implements Serializable {
    private int idhd;
    private int idMA;
    private String TenMonAn;
    private int TrongLuong;
    private String Ngay;
    private float NLHT;
    private Integer idUser;

    public LichsuChitietMonAn(int idhd, int idMA, String tenMonAn, int trongLuong, String ngay, float NLHT, Integer idUser) {
        this.idhd = idhd;
        this.idMA = idMA;
        TenMonAn = tenMonAn;
        TrongLuong = trongLuong;
        Ngay = ngay;
        this.NLHT = NLHT;
        this.idUser=idUser;
    }

    public int getIdhd() {
        return idhd;
    }

    public void setIdhd(int idhd) {
        this.idhd = idhd;
    }

    public int getIdMA() {
        return idMA;
    }

    public void setIdMA(int idMA) {
        this.idMA = idMA;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }

    public int getTrongLuong() {
        return TrongLuong;
    }

    public void setTrongLuong(int trongLuong) {
        TrongLuong = trongLuong;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public float getNLHT() {
        return NLHT;
    }

    public void setNLHT(float NLHT) {
        this.NLHT = NLHT;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}

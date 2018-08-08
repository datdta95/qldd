package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by dthtg on 12/15/2017.
 */

public class LichsuChitietMonTheThao implements Serializable {
    private int idhd;
    private int idMTT;
    private String TenMonTT;
    private float ThoiGian;
    private String Ngay;
    private float NLTH;
    private Integer idUser;

    public LichsuChitietMonTheThao(int idhd, int idMTT, String tenMonTT, float thoiGian, String ngay, float NLTH, Integer idUser) {
        this.idhd = idhd;
        this.idMTT = idMTT;
        TenMonTT = tenMonTT;
        ThoiGian = thoiGian;
        Ngay = ngay;
        this.NLTH = NLTH;
        this.idUser=idUser;
    }

    public int getIdhd() {
        return idhd;
    }

    public void setIdhd(int idhd) {
        this.idhd = idhd;
    }

    public int getIdMTT() {
        return idMTT;
    }

    public void setIdMTT(int idMTT) {
        this.idMTT = idMTT;
    }

    public String getTenMonTT() {
        return TenMonTT;
    }

    public void setTenMonTT(String tenMonTT) {
        TenMonTT = tenMonTT;
    }

    public float getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(float thoiGian) {
        ThoiGian = thoiGian;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public float getNLTH() {
        return NLTH;
    }

    public void setNLTH(float NLTH) {
        this.NLTH = NLTH;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}

package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by TienDat on 4/22/2018.
 */

public class LichSu implements Serializable {
    private String ngay;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public LichSu(String ngay) {
        this.ngay = ngay;
    }
}

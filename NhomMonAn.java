package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by dthtg on 11/20/2017.
 */

public class NhomMonAn implements Serializable {
    private int id;
    private String TenNhomMA;

    public NhomMonAn(int id, String tenNhomMA) {
        this.id = id;
        TenNhomMA = tenNhomMA;
    }

    public int getId(int position) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNhomMA() {
        return TenNhomMA;
    }

    public void setTenNhomMA(String tenNhomMA) {
        TenNhomMA = tenNhomMA;
    }

}

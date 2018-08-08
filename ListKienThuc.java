package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by TienDat on 4/25/2018.
 */

public class ListKienThuc implements Serializable {
    private int id;
    private String tittle;
    private String link;

    public ListKienThuc(int id, String tittle, String link) {
        this.id = id;
        this.tittle = tittle;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

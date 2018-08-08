package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by TienDat on 4/10/2018.
 */

public class Account implements Serializable {

    private int id;
    private String userName;
    private String email;

    public Account() {
    }

    public Account(int id, String userName, String email) {
        this.id=id;
        this.userName = userName;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

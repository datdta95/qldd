package com.dvt.tiendat.quanlydinhduong;

import java.io.Serializable;

/**
 * Created by TienDat on 4/28/2018.
 */

public class User implements Serializable {
    private String password;

    public User(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

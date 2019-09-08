/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author Jason
 */
public class AppuserDTO implements Serializable{
    private final String id;
    private final String name;
    private final String phone;
    private final String address;
    private final String email;
    private final String password;
    private final String appGroup;

    public AppuserDTO(String id, String name, String phone, String address, String email, String password, String appGroup) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.appGroup = appGroup;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAppGroup() {
        return appGroup;
    }
    
    
}

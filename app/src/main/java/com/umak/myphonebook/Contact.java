package com.umak.myphonebook;

import java.io.Serializable;

/**
 * Created by Icorrelate on 10/17/2017.
 */

public class Contact implements Serializable {
    private String number;
    private String name;
    private long contactid;
    private String email;
    private String address;


    public Contact(long contactid, String name, String number, String email, String address) {
        this.number = number;
        this.name = name;
        this.contactid = contactid;
        this.email = email;
        this.address = address;
    }

    public Contact() {
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public long  getContactid() {
        return contactid;
    }

    public void setContactid(long contactid) {
        this.contactid = contactid;
    }

    public String getNumber() {
        return number;
    }

    public  void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Number: " + getNumber() + "Email: " + getEmail() + " Address: " + getAddress();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

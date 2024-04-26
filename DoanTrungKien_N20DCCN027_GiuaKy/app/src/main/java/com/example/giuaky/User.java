package com.example.giuaky;

public class User {
    private int id;
    private String ho;
    private String ten;
    private String gender;
    private String username;
    private String sdt;

    public User() {
    }

    public User(int id, String ho, String ten, String gender, String username, String sdt) {
        this.id = id;
        this.ho = ho;
        this.ten = ten;
        this.gender = gender;
        this.username = username;
        this.sdt = sdt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}

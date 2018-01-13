package com.example.jake.fantasy;

/**
 * Created by jake on 1/10/18.
 */

public class Players {
    String name;
    String country;
    int age,id,price,totScore;
    String Role;
    String Team;
    String url;



    public Players() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTotScore(int totScore) {
        this.totScore = totScore;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getTotScore() {
        return totScore;
    }

    public String getRole() {
        return Role;
    }

    public String getTeam() {
        return Team;
    }
}

package com.khl.bonvoath.kpc.entities;

public class Motivation {
    public Motivation(){}
    public Motivation(String letter, String date, String org, String meaning, String img){
        this.date = date;
        this.img = img;
        this.letter = letter;
        this.meaning = meaning;
        this.org = org;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getDate() {
        return date;
    }

    public String getImg() {
        return img;
    }

    public String getLetter() {
        return letter;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getOrg() {
        return org;
    }

    private String letter;
    private String date;
    private String org;
    private String meaning;
    private String img;
}

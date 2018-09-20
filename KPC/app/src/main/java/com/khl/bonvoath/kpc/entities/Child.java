package com.khl.bonvoath.kpc.entities;

public class Child {
    public Child(){ }
    public Child(String name, String sex, String dob, String job){
        this.dob = dob;
        this.job = job;
        this.name = name;
        this.sex = sex;
    }
    public String getDob() {
        return dob;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String name;
    private String sex;
    private String dob;
    private String job;
}

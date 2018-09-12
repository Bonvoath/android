package com.khl.bonvoath.kpc.entities;

public class Education {
    public String getFromDate(){
        return _from_date;
    }

    public String getToDate(){
        return _to_date;
    }

    public String getLevel() {
        return _level;
    }

    public String getOrganizatio() {
        return _organization;
    }

    public String getSkill(){
        return _skill;
    }

    public void setFromDate(String fromDate){
        _from_date = fromDate;
    }

    public void setToDate(String toDate){
        _to_date = toDate;
    }

    public void setLevel(String level){
        _level = level;
    }

    public void setOrganization(String organization){
        _organization = organization;
    }

    public void setSkill(String skill){
        _skill = skill;
    }

    private String _from_date;
    private String _to_date;
    private String _level;
    private String _organization;
    private String _skill;
}

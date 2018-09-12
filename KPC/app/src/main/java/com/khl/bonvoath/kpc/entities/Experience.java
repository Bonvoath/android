package com.khl.bonvoath.kpc.entities;

public class Experience {
    public void setFrom(String from){
        _from = from;
    }
    public void setTo(String to){
        _to = to;
    }
    public void setJob(String job){
        _job = job;
    }
    public void setOrg(String org){
        _org = org;
    }

    public String getFrom() {
        return _from;
    }

    public String getJob() {
        return _job;
    }

    public String getOrg() {
        return _org;
    }

    public String getTo() {
        return _to;
    }
    private String _from;
    private String _to;
    private String _job;
    private String _org;
}

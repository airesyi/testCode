package com.mvc.modules;

import java.util.List;

public class Student {
    private int stu_id;

    private String sname;

    private List<Project> proList;

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public List<Project> getProList() {
        return proList;
    }

    public void setProList(List<Project> proList) {
        this.proList = proList;
    }
}

package com.safronova.api.entity;

import java.io.Serializable;

public class Street implements Serializable {
    private int id;
    private String name;

    public Street(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Street(){
    }

    public int getid() {
        return id;
    }

    public void setid(int streetId) {
        this.id = streetId;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Street {");
        sb.append(" Id = ").append(id);
        sb.append(", Name = ").append(name);
        sb.append('}');
        return sb.toString();
    }
}

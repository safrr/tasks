package com.safronova.api.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Objects;

public class Street {
    @JSONField(name = "id")
    private long id;

    @JSONField(name = "name")
    private String name;

    public Street() {
    }

    public Street(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street that = (Street) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Street {");
        sb.append(" id = ").append(id);
        sb.append(", name = ").append(name);
        sb.append('}');
        return sb.toString();
    }
}

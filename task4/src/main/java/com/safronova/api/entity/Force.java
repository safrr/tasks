package com.safronova.api.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Objects;

public class Force {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Force(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Force() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Force force = (Force) o;
        return Objects.equals(id, force.id) &&
                Objects.equals(name, force.name);
    }

    @Override
    public int hashCode() {
        int result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Force {");
        sb.append(" id = ").append(id);
        sb.append(", name = ").append(name);
        sb.append('}');
        return sb.toString();
    }
}

package com.safronova.api.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.Objects;

public class OutcomeStatus {
    private long id;

    @JSONField(name = "category")
    private String category;

    @JSONField(name = "date", format = "yyyy-MM")
    private Date date;

    public OutcomeStatus() {
    }

    public OutcomeStatus(long id, String category, Date date) {
        this.id = id;
        this.category = category;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutcomeStatus that = (OutcomeStatus) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OutcomeStatus {");
        sb.append(" Date = ").append(date);
        sb.append(", Category = ").append(category);
        sb.append('}');
        return sb.toString();
    }
}

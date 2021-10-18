package com.safronova.api.entity;

import java.io.Serializable;
import java.util.Date;

public class OutcomeStatus implements Serializable {
    private String date;
    private String category;

    public OutcomeStatus(String date, String category) {
        this.date = date;
        this.category = category;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

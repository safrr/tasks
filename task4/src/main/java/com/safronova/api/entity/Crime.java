package com.safronova.api.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.Objects;

public class Crime {
    @JSONField(name = "id")
    private long id;

    @JSONField(name = "category")
    private String category;

    @JSONField(name = "persistent_id")
    private String persistentId;

    @JSONField(name = "month", format = "yyyy-MM")
    private Date month;

    @JSONField(name = "context")
    private String context;

    @JSONField(name = "location_type")
    private String locationType;

    @JSONField(name = "location_subtype")
    private String locationSubtype;

    @JSONField(name = "location")
    private Location location;

    @JSONField(name = "outcome_status")
    private OutcomeStatus outcomeStatus;


    public Crime() {
    }

    public Crime(long id, String category, String persistentId, Date month,
                 String context, String locationType, String locationSubtype,
                 Location location, OutcomeStatus outcomeStatus) {
        this.id = id;
        this.category = category;
        this.persistentId = persistentId;
        this.month = month;
        this.context = context;
        this.locationType = locationType;
        this.locationSubtype = locationSubtype;
        this.location = location;
        this.outcomeStatus = outcomeStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationSubtype() {
        return locationSubtype;
    }

    public void setLocationSubtype(String locationSubtype) {
        this.locationSubtype = locationSubtype;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public OutcomeStatus getOutcomeStatus() {
        return outcomeStatus;
    }

    public void setOutcomeStatus(OutcomeStatus outcomeStatus) {
        this.outcomeStatus = outcomeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crime crime = (Crime) o;

        return id == crime.id &&
                category.equals(crime.category) &&
                persistentId.equals(crime.persistentId) &&
                context.equals(crime.context) &&
                locationType.equals(crime.locationType) &&
                locationSubtype.equals(crime.locationSubtype) &&
                month.equals(crime.month) &&
                location.equals(crime.location) &&
                Objects.equals(location, crime.location) &&
                Objects.equals(outcomeStatus, crime.outcomeStatus);
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (locationSubtype != null ? locationSubtype.hashCode() : 0);
        result = 31 * result + (outcomeStatus != null ? outcomeStatus.hashCode() : 0);
        result = 31 * result + (persistentId != null ? persistentId.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (context != null ? context.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (locationType != null ? locationType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Crime {");
        sb.append(" Category = ").append(category);
        sb.append(" Id = ").append(id);
        sb.append(", LocationType = ").append(locationType);
        sb.append(", Location = ").append(location);
        sb.append(", Context = ").append(context);
        sb.append(", OutcomeStatus = ").append(outcomeStatus);
        sb.append(", PersistentId = ").append(persistentId);
        sb.append(", LocationSubtype = ").append(locationSubtype);
        sb.append(", Month = ").append(month);
        sb.append('}');
        return sb.toString();
    }
}

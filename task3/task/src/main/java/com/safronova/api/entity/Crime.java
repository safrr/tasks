package com.safronova.api.entity;

import java.io.Serializable;

public class Crime implements Serializable {
    private String location_subtype;
    private OutcomeStatus outcome_status;
    private String persistent_id;
    private String month;
    private String context;
    private Location location;
    private int id;
    private String category;
    private String location_type;

    public Crime(String locationSubtype, OutcomeStatus outcomeStatus, String persistentId, String month, String context, Location location, int id, String category, String locationType) {
        this.location_subtype = locationSubtype;
        this.outcome_status = outcomeStatus;
        this.persistent_id = persistentId;
        this.month = month;
        this.context = context;
        this.location = location;
        this.id = id;
        this.category = category;
        this.location_type = locationType;
    }
    public Crime(){
    }

    public String getLocationSubtype() {
        return location_subtype;
    }

    public void setLocationSubtype(String locationSubtype) {
        this.location_subtype = locationSubtype;
    }

    public OutcomeStatus getOutcomeStatus() {
        return outcome_status;
    }

    public void setOutcomeStatus(OutcomeStatus outcomeStatus) {
        this.outcome_status= outcomeStatus;
    }

    public String getPersistentId() {
        return persistent_id;
    }

    public void setPersistentId(String persistentId) {
        this.persistent_id = persistentId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocationType() {
        return location_type;
    }

    public void setLocationType(String locationType) {
        this.location_type = locationType;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Crime {");
        sb.append(" Category = ").append(category);
        sb.append(" Id = ").append(id);
        sb.append(", LocationType = ").append(location_type);
        sb.append(", Location = ").append( location);
        sb.append(", Context = ").append(context);
        sb.append(", OutcomeStatus = ").append(outcome_status);
        sb.append(", PersistantId = ").append(persistent_id);
        sb.append(", LocationSubtype = ").append(location_subtype);
        sb.append(", Month = ").append(month);
        sb.append('}');
        return sb.toString();
    }
}

package com.safronova.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class StopAndSearch {
    @JSONField(name = "type")
    String type;

    @JSONField(name = "involved_person")
    boolean involvedPerson;

    @JSONField(name = "datetime", format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    Date dateTime;

    @JSONField(name = "operation")
    boolean operation;

    @JSONField(name = "operation_name")
    String operationName;

    @JSONField(name = "location")
    Location location;

    @JSONField(name = "gender")
    String gender;

    @JSONField(name = "age_range")
    String ageRange;

    @JSONField(name = "self_defined_ethnicity")
    String selfDefinedEthnicity;

    @JSONField(name = "officer_defined_ethnicity")
    String officerDefinedEthnicity;

    @JSONField(name = "legislation")
    String legislation;

    @JSONField(name = "object_of_search")
    String objectOfSearch;

    @JSONField(name = "outcome")
    String outcome;

    @JSONField(name = "outcome_linked_to_object_of_search")
    String outcomeLinkedToObjectOfSearch;

    @JSONField(name = "removal_of_more_than_outer_clothing")
    boolean removalOfMoreThanOuterClothing;

    public StopAndSearch(String ageRange, String selfDefinedEthnicity,
                         String outcomeLinkedToObjectOfSearch, Date dateTime,
                         boolean removalOfMoreThanOuterClothing, boolean operation,
                         String officerDefinedEthnicity, String objectOfSearch,
                         boolean involvedPerson, String gender, String legislation,
                         Location location, String outcome, String type, String operationName) {
        this.ageRange = ageRange;
        this.selfDefinedEthnicity = selfDefinedEthnicity;
        this.outcomeLinkedToObjectOfSearch = outcomeLinkedToObjectOfSearch;
        this.dateTime = dateTime;
        this.removalOfMoreThanOuterClothing = removalOfMoreThanOuterClothing;
        this.operation = operation;
        this.officerDefinedEthnicity = officerDefinedEthnicity;
        this.objectOfSearch = objectOfSearch;
        this.involvedPerson = involvedPerson;
        this.gender = gender;
        this.legislation = legislation;
        this.location = location;
        this.outcome = outcome;
        this.type = type;
        this.operationName = operationName;
    }

    public StopAndSearch() {
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInvolvedPerson() {
        return involvedPerson;
    }

    public void setInvolvedPerson(boolean involvedPerson) {
        this.involvedPerson = involvedPerson;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDatetime(Date datetime) {
        this.dateTime = datetime;
    }

    public boolean isOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getSelfDefinedEthnicity() {
        return selfDefinedEthnicity;
    }

    public void setSelfDefinedEthnicity(String selfDefinedEthnicity) {
        this.selfDefinedEthnicity = selfDefinedEthnicity;
    }

    public String getOfficerDefinedEthnicity() {
        return officerDefinedEthnicity;
    }

    public void setOfficerDefinedEthnicity(String officerDefinedEthnicity) {
        this.officerDefinedEthnicity = officerDefinedEthnicity;
    }

    public String getLegislation() {
        return legislation;
    }

    public void setLegislation(String legislation) {
        this.legislation = legislation;
    }

    public String getObjectOfSearch() {
        return objectOfSearch;
    }

    public void setObjectOfSearch(String objectOfSearch) {
        this.objectOfSearch = objectOfSearch;
    }

    public String isOutcome() {
        return outcome;
    }

    public String getOutcomeLinkedToObjectOfSearch() {
        return outcomeLinkedToObjectOfSearch;
    }

    public void setOutcomeLinkedToObjectOfSearch(String outcomeLinkedToObjectOfSearch) {
        this.outcomeLinkedToObjectOfSearch = outcomeLinkedToObjectOfSearch;
    }

    public boolean isRemovalOfMoreThanOuterClothing() {
        return removalOfMoreThanOuterClothing;
    }

    public void setRemovalOfMoreThanOuterClothing(boolean removalOfMoreThanOuterClothing) {
        this.removalOfMoreThanOuterClothing = removalOfMoreThanOuterClothing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopAndSearch that = (StopAndSearch) o;
        return involvedPerson == that.involvedPerson &&
                operation == that.operation &&
                removalOfMoreThanOuterClothing == that.removalOfMoreThanOuterClothing &&
                Objects.equals(type, that.type) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(operationName, that.operationName) &&
                Objects.equals(location, that.location) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(ageRange, that.ageRange) &&
                Objects.equals(selfDefinedEthnicity, that.selfDefinedEthnicity) &&
                Objects.equals(officerDefinedEthnicity, that.officerDefinedEthnicity) &&
                Objects.equals(legislation, that.legislation) &&
                Objects.equals(objectOfSearch, that.objectOfSearch) &&
                Objects.equals(outcome, that.outcome) &&
                Objects.equals(outcomeLinkedToObjectOfSearch, that.outcomeLinkedToObjectOfSearch);
    }

    @Override
    public int hashCode() {
        int result = ageRange != null ? ageRange.hashCode() : 0;
        result = 31 * result + (selfDefinedEthnicity != null ? selfDefinedEthnicity.hashCode() : 0);
        result = 31 * result + (outcomeLinkedToObjectOfSearch != null ? outcomeLinkedToObjectOfSearch.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (removalOfMoreThanOuterClothing ? 1 : 0);
        result = 31 * result + (operation ? 1 : 0);
        result = 31 * result + (officerDefinedEthnicity != null ? officerDefinedEthnicity.hashCode() : 0);
        result = 31 * result + (objectOfSearch != null ? objectOfSearch.hashCode() : 0);
        result = 31 * result + (involvedPerson ? 1 : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (legislation != null ? legislation.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (outcome != null ? outcome.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (operationName != null ? operationName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StopAndSearch { ");
        sb.append(" AgeRange = ").append(ageRange);
        sb.append(" SelfDefinedEthnicity = ").append(selfDefinedEthnicity);
        sb.append(", OutcomeLinkedToObjectOfSearch = ").append(outcomeLinkedToObjectOfSearch);
        sb.append(", DateTime = ").append(dateTime);
        sb.append(", RemovalOfMoreThanOuterClothing = ").append(removalOfMoreThanOuterClothing);
        sb.append(", Operation = ").append(operation);
        sb.append(", OfficerDefinedEthnicity = ").append(officerDefinedEthnicity);
        sb.append(", ObjectOfSearch = ").append(objectOfSearch);
        sb.append(", InvolvedPerson = ").append(involvedPerson);
        sb.append(", Gender = ").append(gender);
        sb.append(", Legislation = ").append(legislation);
        sb.append(", Location = ").append(location);
        sb.append(", Outcome = ").append(outcome);
        sb.append(", Type = ").append(type);
        sb.append(", OperationName = ").append(operationName);
        sb.append('}');
        return sb.toString();
    }
}

package com.findchildren.avi.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Avi on 24.09.2017.
 */

public class Request implements Parcelable {
    @Expose
    Long id;
    @Expose
    String name;
    @Expose
    String dateOfBirth;
    @Expose
    String gender;
    @Expose
    String childDescription;
    @Expose
    String region;
    @Expose
    String situationDescription;
    @Expose
    String timeOfLoss;
    @Expose
    String timeOfRequest;
    @Expose
    String status;

    public Request() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSituationDescription() {
        return situationDescription;
    }

    public void setSituationDescription(String situationDescription) {
        this.situationDescription = situationDescription;
    }

    public String getTimeOfLoss() {
        return timeOfLoss;
    }

    public void setTimeOfLoss(String timeOfLoss) {
        this.timeOfLoss = timeOfLoss;
    }

    public String getTimeOfRequest() {
        return timeOfRequest;
    }

    public void setTimeOfRequest(String timeOfRequest) {
        this.timeOfRequest = timeOfRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Request(Parcel in) {
        name = in.readString();
        dateOfBirth = in.readString();
        gender = in.readString();
        childDescription = in.readString();
        region = in.readString();
        situationDescription = in.readString();
        timeOfLoss = in.readString();
        timeOfRequest = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(dateOfBirth);
        parcel.writeString(gender);
        parcel.writeString(childDescription);
        parcel.writeString(region);
        parcel.writeString(situationDescription);
        parcel.writeString(timeOfLoss);
        parcel.writeString(timeOfRequest);
        parcel.writeString(status);
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
}
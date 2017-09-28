package com.findchildren.avi.test.models;

/**
 * Created by Avi on 28.09.2017.
 */

public class Comment {
    long id;
    String senderName;
    String text;
    String timeOfCreate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeOfCreate() {
        return timeOfCreate;
    }

    public void setTimeOfCreate(String timeOfUpdate) {
        this.timeOfCreate = timeOfUpdate;
    }


}

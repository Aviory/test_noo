package com.findchildren.avi.test.models;

/**
 * Created by avi on 28.09.17.
 */

public class RequestComment extends Comment {
    long senderId;
    long requestId;

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}

package com.ctis487.newsfeed.entity;

public class UserSource {
    private String userId;
    private String sourceId;

    public UserSource(String userId, String sourceId) {
        this.userId = userId;
        this.sourceId = sourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}

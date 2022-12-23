package com.ctis487.newsfeed.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class User implements Parcelable {
    private String uuid;
    private String username;
    private String email;
    private String password;

    public User(String uuid, String username, String password, String email) {
        this.uuid = uuid;
        this.username = username;
        this.email =  email;
        this.password = password;
    }

    public User(String username, String password, String email) {
        this.uuid = UUID.randomUUID().toString();
        this.username = username;
        this.email =  email;
        this.password = password;
    }

    protected User(Parcel in) {
        uuid = in.readString();
        username = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return uuid;
    }

    public void setId(String id) {
        this.uuid = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.password);
    }
}
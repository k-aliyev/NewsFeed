package com.ctis487.newsfeed.service;


import android.content.ContentValues;
import android.database.Cursor;

import com.ctis487.newsfeed.entity.User;
import com.ctis487.newsfeed.util.DatabaseHelper;
import java.util.ArrayList;
import java.util.UUID;

public class UserTable {
    public static final String TABLE_NAME = "user";
    public static final String FIELD_ID = "uuid";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PASSWORD = "password";
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ( " + FIELD_ID + " text, " + FIELD_USERNAME + " text, " + FIELD_EMAIL + " text, " + FIELD_PASSWORD + " text );";
    public static final String DROP_TABLE_SQL = "DROP TABLE if exists " + TABLE_NAME;

    public static ArrayList<User> findUser(DatabaseHelper db, String key) {
        String where = FIELD_USERNAME + " like '%" + key + "%'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME, null, where);
        ArrayList <User> data = new ArrayList < > ();
        User med = null;
        while (cursor.moveToNext()) {
            String uuid = cursor.getString(0);
            String username = cursor.getString(1);
            String email = cursor.getString(2);
            String password = cursor.getString(3);
            med = new User(uuid, username, password);
            data.add(med);
        }
        return data;
    }


    public static boolean isUsernameAvailable(DatabaseHelper db, String username){
        String where = FIELD_USERNAME + " like '%" + username + "%'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME, null, where);
        if(cursor.getCount() > 0){
            return false;
        }
        return true;
    }

    public static boolean isEmailAvailable(DatabaseHelper db, String email){
        String where = FIELD_EMAIL + " like '%" + email + "%'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME, null, where);
        if(cursor.getCount() > 0){
            return false;
        }
        return true;
    }

    public static User checkPassword(DatabaseHelper db, String username ,String password){
        String where = FIELD_USERNAME + " like '%" + username + "%'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME, null, where);
        if(cursor.getCount() > 0) {
            User data = null;
            cursor.moveToFirst();

            String uuid = cursor.getString(0);
            String usernameDb = cursor.getString(1);
            String passwordDb = cursor.getString(2);

            data = new User(uuid, usernameDb, passwordDb);
            if (password.equals(passwordDb)) {
                return data;
            }
        }
        return null;
    }

    public static boolean insertUser(DatabaseHelper db, String username, String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_ID, UUID.randomUUID().toString());
        contentValues.put(FIELD_USERNAME, username);
        contentValues.put(FIELD_EMAIL, email);
        contentValues.put(FIELD_PASSWORD, password);
        boolean res = db.insert(TABLE_NAME, contentValues);
        return res;
    }
    public static boolean insertUser(DatabaseHelper db, User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_ID, user.getId());
        contentValues.put(FIELD_USERNAME, user.getUsername());
        contentValues.put(FIELD_EMAIL, user.getEmail());
        contentValues.put(FIELD_PASSWORD, user.getPassword());
        boolean res = db.insert(TABLE_NAME, contentValues);
        return res;
    }
    public static boolean updateUser(DatabaseHelper db, String uuid, String username, String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_ID, uuid);
        contentValues.put(FIELD_USERNAME, username);
        contentValues.put(FIELD_EMAIL, email);
        contentValues.put(FIELD_PASSWORD, password);
        String where = FIELD_ID + " = " + uuid;
        boolean res = db.update(TABLE_NAME, contentValues, where);
        return res;
    }
    public static boolean updateUser(DatabaseHelper db, User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_USERNAME, user.getUsername());
        contentValues.put(FIELD_EMAIL, user.getEmail());
        contentValues.put(FIELD_PASSWORD, user.getPassword());
        String where = FIELD_ID + " = " + user.getId();
        boolean res = db.update(TABLE_NAME, contentValues, where);
        return res;
    }
    public static boolean deleteUser(DatabaseHelper db, String uuid) {
        String where = FIELD_ID + " = " + uuid;
        boolean res = db.delete(TABLE_NAME, where);
        return res;
    }
}
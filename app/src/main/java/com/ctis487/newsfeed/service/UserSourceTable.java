package com.ctis487.newsfeed.service;

import android.content.ContentValues;
import android.database.Cursor;

import com.ctis487.newsfeed.entity.Source;
import com.ctis487.newsfeed.entity.User;
import com.ctis487.newsfeed.entity.UserSource;
import com.ctis487.newsfeed.util.DatabaseHelper;
import java.util.ArrayList;
import java.util.UUID;

public class UserSourceTable {
    public static final String TABLE_NAME = "user_source";
    public static final String FIELD_USER_ID = "user_id";
    public static final String FIELD_SOURCE_ID = "source_id";
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ( " + FIELD_USER_ID + " text, " + FIELD_SOURCE_ID + " text );";
    public static final String DROP_TABLE_SQL = "DROP TABLE if exists " + TABLE_NAME;


    public static boolean checkIfExists(DatabaseHelper db, String userId, String sourceId){
        String where = FIELD_USER_ID + " like '%" + userId + "%'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME, null, where);
        while (cursor.moveToNext()) {
            String tmpSourceId = cursor.getString(1);
            if(tmpSourceId.equals(sourceId))
                return true;
        }
        return false;
    }

    public static ArrayList<String> getFavoritesId(DatabaseHelper db, String userId){
        String where = FIELD_USER_ID + " like '%" + userId + "%'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME, null, where);
        ArrayList <String> data = new ArrayList < > ();
        User med = null;
        while (cursor.moveToNext()) {
            String sourceId = cursor.getString(1);
            data.add(sourceId);
        }
        return data;
    }

    public static boolean insertUser(DatabaseHelper db, String userId, String sourceId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_USER_ID, userId);
        contentValues.put(FIELD_SOURCE_ID, sourceId);
        boolean res = db.insert(TABLE_NAME, contentValues);
        return res;
    }
    public static boolean insertUserSource(DatabaseHelper db, UserSource userSource) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_USER_ID, userSource.getUserId());
        contentValues.put(FIELD_SOURCE_ID, userSource.getSourceId());
        boolean res = db.insert(TABLE_NAME, contentValues);
        return res;
    }
    public static boolean deleteUser(DatabaseHelper db, String userId, String sourceId) {
        String where = FIELD_USER_ID + " = " + userId + " and " + FIELD_SOURCE_ID + " = " + sourceId;
        boolean res = db.delete(TABLE_NAME, where);
        return res;
    }
}
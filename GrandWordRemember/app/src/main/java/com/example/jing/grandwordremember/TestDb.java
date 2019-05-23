package com.example.jing.grandwordremember;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TestDb {
    private DBOpenHandler dbOpenHandler;
    public TestDb(Context context){
        this.dbOpenHandler = new DBOpenHandler(context, "dbTestWord.db3", null, 1);
    }

    public Uri insert(Uri uri, ContentValues cv){
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.insert("words", null, cv);
        db.close();
        return uri;
    }

    public int update(Uri uri, ContentValues cv, String where, String[] whereArgs){
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int ret = db.update("words", cv, where, whereArgs);
        db.close();
        return ret;
    }

    public int delete(Uri uri, String where, String[] whereArgs){
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int ret = db.delete("words", where, whereArgs);
        db.close();
        return ret;
    }

    public Cursor query(Uri uri, String[] projection, String where, String[] whereArgs, String sortOrder){
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        Cursor cursor = db.query("words", projection, where, whereArgs, null, null, sortOrder, null);
//        db.close();
        return cursor;
    }
}


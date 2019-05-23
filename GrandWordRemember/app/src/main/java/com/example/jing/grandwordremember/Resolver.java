package com.example.jing.grandwordremember;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class Resolver {
    ContentResolver resolver;
    ContentValues cv=new ContentValues();
    Uri uri = Uri.parse("content://com.example.jing.app.contentProvider/");

    public Resolver(ContentResolver resolver, Uri uri){
        this.resolver = resolver;
        this.uri = uri;
    }

    public List<WordRec> query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        List<WordRec> words = new ArrayList<>();
        while(c!=null && c.moveToNext()){
            WordRec word = new WordRec(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3));
            words.add(word);
//            Log.d("测试","num:"+c.getInt(0)+" word:"+c.getString(1)+" explanation:"+c.getString(2)+" level:"+c.getInt(3));
        }
        return words;
    }


    public void insert(ContentValues cv ){
        Uri newUri = resolver.insert(uri,cv);  // null--错误
    }
    public void update() {
        cv.clear();
        cv.put("num","170002");
        cv.put("name","李四");
//        int count = resolver.update(uri, cv, "_id=?", new String[]{"2"});// 影响的记录数
    }
    public void delete() {
//        int count = resolver.delete(uri, "_id=?", new String[]{"5"}); // 影响的记录数
    }
}

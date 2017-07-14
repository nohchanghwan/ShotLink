package com.test.sy.shotlinkone;

/**
 * Created by jaemoon on 2016-11-21.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLManager extends SQLiteOpenHelper {

    public SQLManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE screenshot1 ( url TEXT, filepath TEXT);");
        db.execSQL("CREATE TABLE screenshot ( url TEXT, tag TEXT, filepath TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS screenshot");
        onCreate(db);
    }

    public void Insert(String url,String tag, String filepath) {
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("INSERT INTO screenshot1 VALUES ('"+ url +"' + '"+ filepath+"' );");
        db.execSQL("INSERT INTO screenshot VALUES ( '" + url + "', '" + tag + "', '" + filepath + "');");
    }

    public void Drop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE screenshot;");
    }


    public void Update(String url,String filepath,String oldtag)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("url",url);
        values.put("filepath",filepath);
        db.update("screenshot",values,"title=?",new String[]{oldtag});
    }

    public void Delete(String filepath) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM screenshot WHERE filepath='" + filepath + "';");
    }


    public ArrayList<content> SelectAll() {
        ArrayList<content> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM screenshot", null);
        while(cursor.moveToNext()) {
            String url = cursor.getString(0);
            String filepath = cursor.getString(2);
            content c = new content(url,filepath);
            list.add(c);
        }

        return list;
    }

    public class content {
        public String url;
        public String filepath;

        public content(String url,String filepath) {
            this.url = url;
            this.filepath=filepath;
        }
    }
}

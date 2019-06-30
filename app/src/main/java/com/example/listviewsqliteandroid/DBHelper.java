package com.example.listviewsqliteandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    //SA kreye database la
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyTodo.db";

    //Sa striktire database la
    public static final String TABLE_NAME = "todo";
    public static final String COLUMN_ID ="_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NOTE = "note";

    //Sql queries database la li detekte li
    private final static String TYPE = " TEXT ";
    private final static String SQL_CREATE = "CREATE TABLE "+TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_DATE + TYPE + ", " + COLUMN_NOTE + TYPE +");";

    private final static String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase db;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
        Log.e("DATABASE: ", "CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE);
            onCreate(db);
    }


    public Cursor getAllNotes(){
        db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_DATE, COLUMN_NOTE};
        String orderBy = COLUMN_ID + " DESC";

        Cursor cursor = db.query(TABLE_NAME, columns,null,null,null,null,orderBy);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insertNote(String date, String note){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_NOTE, note);
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    public void deleteNote(Long id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_ID + " = " + id, null);
        db.close();
    }

}

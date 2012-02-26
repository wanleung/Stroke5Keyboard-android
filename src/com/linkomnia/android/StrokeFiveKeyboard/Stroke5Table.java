package com.linkomnia.android.StrokeFiveKeyboard;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Stroke5Table {
    public static final String TABLE_NAME = "stroke5";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CHAR = "char";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_USAGE = "usage";

    protected static final String TABLE_CREATE = "create table " + TABLE_NAME + "( " 
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_CHAR + " VARCHAR(5) not null, "
            + COLUMN_CODE + " VARCHAR(5) not null, "
            + COLUMN_USAGE +  " integer DEFAULT 0 " 
            + " );";
    protected static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
    
    private boolean isWritable;
    
    private SQLiteDatabase database;
    private Stroke5SQLiteHelper dbHelper;
    private String[] searchColumns = { Stroke5Table.COLUMN_CHAR };
    
    public Stroke5Table(Context context, boolean isWritable) {
        dbHelper = new Stroke5SQLiteHelper(context);
        this.isWritable = isWritable;
    }

    public void open() throws SQLException {
        if (isWritable) {
            database = dbHelper.getWritableDatabase();
        } else {
            database = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        dbHelper.close();
    }

    public void createRecord(String ch, String code) {
        if (!isWritable) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(Stroke5Table.COLUMN_CHAR, ch);
        values.put(Stroke5Table.COLUMN_CODE, code);
        database.insert(Stroke5Table.TABLE_NAME, null,
                values);
        // To show how to query
    }

    public ArrayList<String> searchRecord(String input) {
        ArrayList<String> result = new ArrayList<String>();
        String[] args =  {input};
        String order = Stroke5Table.COLUMN_USAGE+", "+ Stroke5Table.COLUMN_CHAR +" DESC";
        Cursor cursor = database.query(Stroke5Table.TABLE_NAME,
                searchColumns, Stroke5Table.COLUMN_CODE + " = ? ", args, null, null, order );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String ch = cursor.getString(cursor.getColumnIndex(Stroke5Table.COLUMN_CHAR)) ;
            result.add(ch);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return result;
    }
    
    

}   


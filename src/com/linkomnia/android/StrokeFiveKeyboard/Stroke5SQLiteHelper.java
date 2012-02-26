package com.linkomnia.android.StrokeFiveKeyboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Stroke5SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "stoke5.db";
    private static final int DATABASE_VERSION = 1;
    
    private Context ctx;

    // Database creation sql statement

    public Stroke5SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("WANLEUNG", "Create DB");
        try {
            database.execSQL(Stroke5Table.TABLE_CREATE);
            FileImporter fileimp = new FileImporter(ctx, database);
            fileimp.importFileFromResourceId(R.raw.stroke5);
        } catch (Exception e) {
            Log.d("WANLEUNG", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Stroke5SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL(Stroke5Table.TABLE_DROP);
        this.onCreate(db);
    }
}

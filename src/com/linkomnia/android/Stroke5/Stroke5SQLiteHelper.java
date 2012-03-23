/*
    Stroke5 Chinese Input Method for Android
    Copyright (C) 2012 LinkOmnia Ltd.  

    Author: Wan Leung Wong (wanleung@linkomnia.com)

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.linkomnia.android.Stroke5;

import com.linkomnia.android.Stroke5.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
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
            FileImporter fileimp = new FileImporter(ctx, database, R.raw.stroke5);
            new ImportFilesTask().execute(fileimp);
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
    
    private class ImportFilesTask extends AsyncTask<FileImporter, Void, Long> {
        protected Long doInBackground(FileImporter... fims) {
            int count = fims.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize ++;
                fims[i].importFileFromResourceId();
            }
            return totalSize;
        }

    }

}



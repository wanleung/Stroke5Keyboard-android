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

import java.io.BufferedReader;
import java.io.File;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FileImporter {

    private Context ctx;
    private boolean isLibrary = false;
    private SQLiteDatabase database;

    public FileImporter(Context context, SQLiteDatabase db) {
        this.ctx = context;
        this.database = db;
    }

    public void importFileFromResourceId(int resourceId) {
        try {
            InputStream inputStream = ctx.getResources().openRawResource(
                    resourceId);
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF8");
            BufferedReader reader = new BufferedReader(isr);

            String line = reader.readLine();

            database.beginTransaction();
            while (line != null) {
                if (! line.substring(0, 3).equals("###")) {
                    if (isLibrary) {
                        String[] split = line.split(" ");
                        
                        //Log.d("WANLEUNG", "####" + split[0] + "####" + split[1] + "####");
                        ContentValues values = new ContentValues();
                        values.put(Stroke5Table.COLUMN_CHAR, split[1]);
                        values.put(Stroke5Table.COLUMN_CODE, split[0]);
                        database.insert(Stroke5Table.TABLE_NAME, null,
                                values);
                    }
                    if (line.equals("%chardef begin")) {
                        isLibrary = true;
                    }
                    if (line.equals("%chardef end")) {
                        isLibrary = false;
                    }
                }
                line = reader.readLine();
            }
            database.setTransactionSuccessful();
            database.endTransaction();
            reader.close();
            isr.close();
            inputStream.close();
        } catch (Exception e) {

        }
    }
}

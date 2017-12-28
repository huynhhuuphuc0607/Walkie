package com.phuchuynh.projects.walkie;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HuynhHuu on 24-Dec-17.
 */

class DBHelper extends SQLiteOpenHelper {

    private Context mContext;
    static final String DATABASE_NAME = "Walkie";
    private static final int DATABASE_VERSION = 2;

    private static final String TROPHY_DATABASE_TABLE = "Trophies";

    //Trophies
    private static final String FIELD_TROPHY_NUMBER = "trophy_number";
    private static final String FIELD_TROPHY_OBJECTIVE = "objective";
    private static final String FIELD_TROPHY_DESCRIPTION = "description";
    private static final String FIELD_TROPHY_SCHOOL = "school";
    private static final String FIELD_TROPHY_LOCATION_TITLE = "location_title";
    private static final String FIELD_TROPHY_LATITUDE = "latitude";
    private static final String FIELD_TROPHY_LONGITUDE = "longitude";
    private static final String FIELD_TROPHY_DONE = "done";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TROPHY_DATABASE_TABLE + "("
                + FIELD_TROPHY_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_TROPHY_OBJECTIVE +" TEXT, "
                + FIELD_TROPHY_DESCRIPTION +" TEXT, "
                + FIELD_TROPHY_SCHOOL +" TEXT, "
                + FIELD_TROPHY_LOCATION_TITLE+ " TEXT, "
                + FIELD_TROPHY_LATITUDE + " FLOAT, "
                + FIELD_TROPHY_LONGITUDE + " FLOAT, "
                + FIELD_TROPHY_DONE +" INTEGER"
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TROPHY_DATABASE_TABLE);
        onCreate(db);
    }

    public void addTrophy(long id, String objective, String description, String school, String locationTitle,
                          double latitude, double longtitude, boolean done)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_TROPHY_NUMBER, id);
        values.put(FIELD_TROPHY_OBJECTIVE, objective);
        values.put(FIELD_TROPHY_DESCRIPTION, description);
        values.put(FIELD_TROPHY_SCHOOL, school);
        values.put(FIELD_TROPHY_LOCATION_TITLE, locationTitle);
        values.put(FIELD_TROPHY_LATITUDE, latitude);
        values.put(FIELD_TROPHY_LONGITUDE, longtitude);
        values.put(FIELD_TROPHY_DONE, done);
        db.insert(TROPHY_DATABASE_TABLE, null,values);

        db.close();
    }

    public List<Trophy> getAllTrophies()
    {
        List<Trophy> trophies = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TROPHY_DATABASE_TABLE, new String[]{FIELD_TROPHY_NUMBER, FIELD_TROPHY_OBJECTIVE, FIELD_TROPHY_DESCRIPTION,
        FIELD_TROPHY_SCHOOL, FIELD_TROPHY_LOCATION_TITLE, FIELD_TROPHY_LATITUDE, FIELD_TROPHY_LONGITUDE, FIELD_TROPHY_DONE},
                null,null,null,null,null);

        if (cursor.moveToFirst()) {
            do {
                Trophy trophy = new Trophy(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getDouble(6),
                        (cursor.getInt(7) ==1));
                trophies.add(trophy);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return trophies;
    }
    public void deleteAllTrophies()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TROPHY_DATABASE_TABLE, null,null);
        db.close();
    }
    //--------------------------Trophies--STARTS--HERE-------------------------------
    public boolean importTrophiesFromCSV(String csvFileName) {
        AssetManager manager = mContext.getAssets();
        InputStream inStream;
        try {
            inStream = manager.open(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] fields = line.split("(?<!\\\\),");
                if (fields.length != 8) {
                    Log.d("Walkie", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                long id = Long.parseLong(fields[0].trim());
                String objective = fields[1].trim();
                String description = fields[2].trim();
                String school = fields[3].trim();
                String locationTitle = fields[4].trim();
                double latitude = Double.parseDouble(fields[5].trim());
                double longitude = Double.parseDouble(fields[6].trim());
                boolean done = Boolean.parseBoolean(fields[7].trim());
                addTrophy(id,objective,description,school,locationTitle,latitude,longitude,done);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //-----------------------------Trophies--ENDs--HERE------------------------------
}

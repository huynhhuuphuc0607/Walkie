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
    private static final int DATABASE_VERSION = 3;

    private static final String TROPHY_DATABASE_TABLE = "Trophies";
    private static final String SCHOOL_DATABASE_TABLE = "Schools";

    //Trophies
    private static final String FIELD_TROPHY_NUMBER = "trophy_number";
    private static final String FIELD_TROPHY_OBJECTIVE = "objective";
    private static final String FIELD_TROPHY_DESCRIPTION = "description";
    private static final String FIELD_TROPHY_SCHOOL = "school";
    private static final String FIELD_TROPHY_LOCATION_TITLE = "location_title";
    private static final String FIELD_TROPHY_LATITUDE = "latitude";
    private static final String FIELD_TROPHY_LONGITUDE = "longitude";
    private static final String FIELD_TROPHY_DONE = "done";


    //Schools
    private static final String FIELD_SCHOOL_NUMBER = "school_number";
    private static final String FIELD_SCHOOL_NAME = "objective";
    private static final String FIELD_SCHOOL_DESCRIPTION = "description";
    private static final String FIELD_SCHOOL_STATE= "state";
    private static final String FIELD_SCHOOL_TYPE = "type";
    private static final String FIELD_SCHOOL_LATITUDE = "latitude";
    private static final String FIELD_SCHOOL_LONGITUDE = "longitude";
    private static final String FIELD_SCHOOL_IMAGE_NAME = "image_name";

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

        db.execSQL("CREATE TABLE " + SCHOOL_DATABASE_TABLE + "("
                + FIELD_SCHOOL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_SCHOOL_NAME +" TEXT, "
                + FIELD_SCHOOL_DESCRIPTION +" TEXT, "
                + FIELD_SCHOOL_STATE +" TEXT, "
                + FIELD_SCHOOL_TYPE +" INTEGER, "
                + FIELD_SCHOOL_LATITUDE+ " FLOAT, "
                + FIELD_SCHOOL_LONGITUDE + " FLOAT, "
                + FIELD_SCHOOL_IMAGE_NAME + " TEXT"
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TROPHY_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ SCHOOL_DATABASE_TABLE);
        onCreate(db);
    }


    //--------------------------Trophies--STARTS--HERE-------------------------------
    public void addTrophy(long id, String objective, String description, String school, String locationTitle,
                          double latitude, double longitude, boolean done)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_TROPHY_NUMBER, id);
        values.put(FIELD_TROPHY_OBJECTIVE, objective);
        values.put(FIELD_TROPHY_DESCRIPTION, description);
        values.put(FIELD_TROPHY_SCHOOL, school);
        values.put(FIELD_TROPHY_LOCATION_TITLE, locationTitle);
        values.put(FIELD_TROPHY_LATITUDE, latitude);
        values.put(FIELD_TROPHY_LONGITUDE, longitude);
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


    //-----------------------------Schools--STARTS--HERE------------------------------

    public void addSchool(long id, String name, String description, String state, int type, double latitude,
                          double longitude, String imageName)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_SCHOOL_NUMBER, id);
        values.put(FIELD_SCHOOL_NAME, name);
        values.put(FIELD_SCHOOL_DESCRIPTION, description);
        values.put(FIELD_SCHOOL_STATE, state);
        values.put(FIELD_SCHOOL_TYPE, type);
        values.put(FIELD_SCHOOL_LATITUDE, latitude);
        values.put(FIELD_SCHOOL_LONGITUDE, longitude);
        values.put(FIELD_SCHOOL_IMAGE_NAME, imageName);

        db.insert(SCHOOL_DATABASE_TABLE, null,values);

        db.close();
    }

    public List<School> getAllSchools()
    {
        List<School> schools = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(SCHOOL_DATABASE_TABLE, new String[]{FIELD_SCHOOL_NUMBER, FIELD_SCHOOL_NAME, FIELD_SCHOOL_DESCRIPTION,
                        FIELD_SCHOOL_STATE, FIELD_SCHOOL_TYPE, FIELD_SCHOOL_LATITUDE, FIELD_SCHOOL_LONGITUDE, FIELD_SCHOOL_IMAGE_NAME},
                null,null,null,null,null);

        if (cursor.moveToFirst()) {
            do {
                School school = new School(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getInt(4), cursor.getDouble(5), cursor.getDouble(6),
                        cursor.getString(7));
                schools.add(school);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return schools;
    }

    public ArrayList<School> getSchools(int type)
    {
        ArrayList<School> schools = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(SCHOOL_DATABASE_TABLE, new String[]{FIELD_SCHOOL_NUMBER, FIELD_SCHOOL_NAME, FIELD_SCHOOL_DESCRIPTION,
                        FIELD_SCHOOL_STATE, FIELD_SCHOOL_TYPE, FIELD_SCHOOL_LATITUDE, FIELD_SCHOOL_LONGITUDE, FIELD_SCHOOL_IMAGE_NAME},
                FIELD_SCHOOL_TYPE + " = ?",new String[]{type +""},null,null,null);

        if (cursor.moveToFirst()) {
            do {
                School school = new School(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getInt(4), cursor.getDouble(5), cursor.getDouble(6),
                        cursor.getString(7));
                schools.add(school);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return schools;
    }

    public void deleteAllSchools()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SCHOOL_DATABASE_TABLE, null,null);
        db.close();
    }
    public boolean importSchoolsFromCSV(String csvFileName) {
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
                String name = fields[1].trim();
                String description = fields[2].trim();
                String state = fields[3].trim();
                int type = Integer.parseInt(fields[4].trim());
                double latitude = Double.parseDouble(fields[5].trim());
                double longitude = Double.parseDouble(fields[6].trim());
                String imageName = fields[7].trim();
                addSchool(id,name,description,state,type,latitude,longitude,imageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //-----------------------------Schools--ENDS--HERE------------------------------
}

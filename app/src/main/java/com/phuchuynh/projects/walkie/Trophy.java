package com.phuchuynh.projects.walkie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HuynhHuu on 24-Dec-17.
 */

public class Trophy implements Parcelable{
    private long id;
    private String objective;
    private String description;
    private String school;
    private String locationTitle;
    private double latitude;
    private double longitude;
    private boolean done;

    public Trophy(long id, String objective, String description, String school, String locationTitle, double latitude, double longitude, boolean done) {
        this.id = id;
        this.objective = objective;
        this.description = description;
        this.school = school;
        this.locationTitle = locationTitle;
        this.latitude = latitude;
        this.longitude = longitude;
        this.done = done;
    }

    protected Trophy(Parcel in) {
        id = in.readLong();
        objective = in.readString();
        description = in.readString();
        school = in.readString();
        locationTitle = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        done = in.readInt() == 1;
    }

    public static final Creator<Trophy> CREATOR = new Creator<Trophy>() {
        @Override
        public Trophy createFromParcel(Parcel in) {
            return new Trophy(in);
        }

        @Override
        public Trophy[] newArray(int size) {
            return new Trophy[size];
        }
    };

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(objective);
        parcel.writeString(description);
        parcel.writeString(school);
        parcel.writeString(locationTitle);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeInt(done ? 1 : 0);
    }
}

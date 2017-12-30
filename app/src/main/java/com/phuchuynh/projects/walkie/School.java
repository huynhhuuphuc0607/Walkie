package com.phuchuynh.projects.walkie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HuynhHuu on 30-Dec-17.
 */

public class School implements Parcelable {
    private long id;
    private String name;
    private String description;
    private String state;
    private int type;
    private double latitude;
    private double longitude;
    private String imageName;

    public School(long id, String name, String description, String state, int type, double latitude, double longitude, String imageName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageName = imageName;
    }

    protected School(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        state = in.readString();
        type = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        imageName = in.readString();
    }

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel in) {
            return new School(in);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(state);
        parcel.writeInt(type);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(imageName);
    }
}

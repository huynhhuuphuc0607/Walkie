package com.phuchuynh.projects.walkie;

/**
 * Created by HuynhHuu on 24-Dec-17.
 */

public class Trophy {
    private long id;
    private String objective;
    private String description;
    private String school;
    private String locationTitle;
    private double latitude;
    private double longtitude;
    private boolean done;

    public Trophy(long id, String objective, String description, String school, String locationTitle, double latitude, double longtitude, boolean done) {
        this.id = id;
        this.objective = objective;
        this.description = description;
        this.school = school;
        this.locationTitle = locationTitle;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.done = done;
    }

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

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
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
}

package com.escuelaing.persistence.models;

public class Ride {
    private String id;
    private String origin;
    private String end;
    private String driver;
    private String user;
    private String status;

    public Ride() {}

    public Ride(String origin, String end, String driver, String user) {
        this.origin = origin;
        this.end = end;
        this.driver = driver;
        this.user = user;
        this.status = "pending";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

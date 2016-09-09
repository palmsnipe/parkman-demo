package com.palmsnipe.parkmandemo.models;

import com.palmsnipe.parkmandemo.helpers.JsonHelper;

import org.json.JSONObject;

/**
 * Created by cyril on 08/09/16.
 */
public class Bounds {
    private double north;
    private double south;
    private double west;
    private double east;

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public static Bounds fromJsonObject(JSONObject json) {
        if (json == null) return null;

        Bounds bounds = new Bounds();

        bounds.setNorth(JsonHelper.getDouble(json, "north"));
        bounds.setSouth(JsonHelper.getDouble(json, "south"));
        bounds.setWest(JsonHelper.getDouble(json, "west"));
        bounds.setEast(JsonHelper.getDouble(json, "east"));

        return bounds;
    }
}

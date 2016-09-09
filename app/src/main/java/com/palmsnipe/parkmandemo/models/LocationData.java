package com.palmsnipe.parkmandemo.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyril on 08/09/16.
 */
public class LocationData {
    private Bounds bounds;
    private List<Zone> zones;

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public static LocationData fromJson(JSONObject json) {
        if (json == null) return null;

        LocationData data = new LocationData();

        try {
            if (json.has("bounds"))
                data.setBounds(Bounds.fromJsonObject(json.getJSONObject("bounds")));
            if (json.has("zones")) {
                JSONArray zones = json.getJSONArray("zones");
                List<Zone> list = new ArrayList<>();

                for (int i = 0; i < zones.length(); i++) {
                    Zone zone = Zone.fromJsonObject(zones.getJSONObject(i));

                    if (zone != null)
                        list.add(zone);
                }

                data.setZones(list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}

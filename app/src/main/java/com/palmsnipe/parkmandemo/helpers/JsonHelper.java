package com.palmsnipe.parkmandemo.helpers;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyril on 08/09/16.
 */
public class JsonHelper {

    public static String getString(JSONObject object, String key) {
        try {
            if (object.has(key))
                return object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static int getInt(JSONObject object, String key) {
        try {
            if (object.has(key))
                return object.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static long getLong(JSONObject object, String key) {
        try {
            if (object.has(key))
                return object.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static double getDouble(JSONObject object, String key) {
        try {
            if (object.has(key))
                return object.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0.0;
    }


    public static List<LatLng> getPolygon(String polygon) {
        List<LatLng> list = new ArrayList<>();

        if (!polygon.isEmpty()) {
            String[] tab = polygon.split(",");
            for (String position : tab) {
                String point[] = position.trim().split(" ");
                list.add(new LatLng(Double.parseDouble(point[0]), Double.parseDouble(point[1])));
            }
        }

        return list;
    }
}

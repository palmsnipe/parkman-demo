package com.palmsnipe.parkmandemo.helpers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cyril on 08/09/16.
 */
public class DataHelper {

    public static JSONObject loadJsonObjectFromAsset(Context context, String path) {
        JSONObject json = null;
        try {
            InputStream is = context.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }

        return json;
    }
}

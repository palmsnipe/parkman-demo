package com.palmsnipe.parkmandemo.models;

import com.google.android.gms.maps.model.Polygon;

/**
 * Created by cyril on 09/09/16.
 */
public class PolygonZone {
    private Polygon polygon;
    private Zone zone;

    public PolygonZone(Polygon polygon, Zone zone) {
        setPolygon(polygon);
        setZone(zone);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}

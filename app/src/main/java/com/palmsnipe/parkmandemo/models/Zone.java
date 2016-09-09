package com.palmsnipe.parkmandemo.models;

import android.graphics.Color;

import com.palmsnipe.parkmandemo.helpers.JsonHelper;

import org.json.JSONObject;

/**
 * Created by cyril on 08/09/16.
 */
public class Zone {
    private long id;
    private String polygon;
    private String name;
    private int paymentIsAllowed;
    private double maxDuration;
    private double servicePrice;
    private int depth;
    private int draw;
    private int stickerRequired;
    private String currency;
    private String contactEmail;
    private String point;
    private String country;
    private int providerId;
    private String providerName;

    public Zone() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPolygon() {
        return polygon;
    }

    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPaymentIsAllowed() {
        return paymentIsAllowed;
    }

    public void setPaymentIsAllowed(int paymentIsAllowed) {
        this.paymentIsAllowed = paymentIsAllowed;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStickerRequired() {
        return stickerRequired;
    }

    public void setStickerRequired(int stickerRequired) {
        this.stickerRequired = stickerRequired;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public static Zone fromJsonObject(JSONObject json) {
        if (json == null) return null;

        Zone zone = new Zone();

        zone.setId(JsonHelper.getLong(json, "id"));
        zone.setPolygon(JsonHelper.getString(json, "polygon"));
        zone.setName(JsonHelper.getString(json, "name"));
        zone.setPaymentIsAllowed(JsonHelper.getInt(json, "payment_is_allowed"));
        zone.setMaxDuration(JsonHelper.getDouble(json, "max_duration"));
        zone.setServicePrice(JsonHelper.getDouble(json, "service_price"));
        zone.setDepth(JsonHelper.getInt(json, "depth"));
        zone.setDraw(JsonHelper.getInt(json, "draw"));
        zone.setStickerRequired(JsonHelper.getInt(json, "sticker_required"));
        zone.setCurrency(JsonHelper.getString(json, "currency"));
        zone.setContactEmail(JsonHelper.getString(json, "contact_email"));
        zone.setPoint(JsonHelper.getString(json, "point"));
        zone.setCountry(JsonHelper.getString(json, "country"));
        zone.setProviderId(JsonHelper.getInt(json, "provider_id"));
        zone.setProviderName(JsonHelper.getString(json, "provider_name"));

        return zone;
    }

    public int getZoneColor() {
        return getPaymentIsAllowed() == 1 ? Color.parseColor("#4AE371") : Color.parseColor("#FF8A8A");
    }

}

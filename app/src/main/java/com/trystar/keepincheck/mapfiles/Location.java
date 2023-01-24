package com.trystar.keepincheck.mapfiles;

import com.google.android.gms.maps.model.LatLng;

public class Location {

    String name;
    LatLng latLng;

    public Location(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

}

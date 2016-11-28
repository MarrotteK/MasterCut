package com.example.rune.mastercut;

import java.util.UUID;

/**
 * original file created by loune from loune.net on 26/12/2015,
 * Full tutorial can be found at https://loune.net/2016/04/building-an-android-phone-connected-door-bell-with-light-blue-beans-ibeacon/
 */

public class BeaconInfo {
    private String name;
    private String address;
    private short manufacturerId;
    private short beaconCode;
    private UUID beaconUUID;
    private short major;
    private short minor;
    private int referenceRssi;
    private int rssi;
    private double distance;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public short getManufacturerId() {
        return manufacturerId;
    }

    public short getBeaconCode() {
        return beaconCode;
    }

    public UUID getBeaconUUID() {
        return beaconUUID;
    }

    public short getMajor() {
        return major;
    }

    public short getMinor() {
        return minor;
    }

    public int getReferenceRssi() {
        return referenceRssi;
    }

    public int getRssi() {
        return rssi;
    }

    public double getDistance() {
        return distance;
    }

    public BeaconInfo(String name, String address, short manufacturerId, short beaconCode, UUID beaconUUID, short major, short minor, int referenceRssi, int rssi, double distance) {
        this.name = name;
        this.address = address;
        this.manufacturerId = manufacturerId;
        this.beaconCode = beaconCode;
        this.beaconUUID = beaconUUID;
        this.major = major;
        this.minor = minor;
        this.referenceRssi = referenceRssi;
        this.rssi = rssi;
        this.distance = distance;
    }
}
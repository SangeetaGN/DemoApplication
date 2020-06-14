package com.example.demoapplication.model;

import com.google.gson.annotations.SerializedName;

public class ParkingCar {

    @SerializedName("slotNo")
    private Integer slotNo;
    @SerializedName("regNo")
    private String regNo;
    @SerializedName("color")
    private String color;

    @SerializedName("isAlloted")
    private boolean isAlloted;

    public ParkingCar() {
    }

    public ParkingCar(int slotNo, String regNo, String color, boolean isAlloted) {
        this.slotNo = slotNo;
        this.regNo = regNo;
        this.color = color;
        this.isAlloted = isAlloted;
    }

    public boolean getIsAlloted() {
        return isAlloted;
    }

    public void setIsAlloted(boolean alloted) {
        isAlloted = alloted;
    }

    public Integer getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(Integer slotNo) {
        this.slotNo = slotNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
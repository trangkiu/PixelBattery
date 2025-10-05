package org.example.pixelbattery.Service;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;

import java.util.List;

public class BatteryService {
    private  String batteryName = "";
    private  double batteryLifePercent = 0;
    private  boolean isCharging = false;
    private  double timeRemain = 0;
    private  boolean isPowerOnLine = false;
    private static final BatteryService batteryService = new BatteryService();

    public  String getBatteryName() {
        return this.batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    public double getBatteryLifePercent() {
        return this.batteryLifePercent;
    }

    public void setBatteryLifePercent(double batteryLifePercent) {
        this.batteryLifePercent = batteryLifePercent;
    }

    public boolean isIsCharging() {
        return this.isCharging;
    }

    public void setIsCharging(boolean isCharging) {
        this.isCharging = isCharging;
    }

    public double getTimeRemain() {
        return this.timeRemain;
    }

    public void setTimeRemain(double timeRemain) {
        this.timeRemain = timeRemain;
    }

    public boolean isIsPowerOnLine() {
        return this.isPowerOnLine;
    }

    public void setIsPowerOnLine(boolean isPowerOnLine) {
        this.isPowerOnLine = isPowerOnLine;
    }

    public static BatteryService getInstance() {
        return batteryService;
    }


    public void updateBatteryInfo(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        List<PowerSource> powerSources = hal.getPowerSources();

        for (PowerSource ps : powerSources) {
            this.batteryName = ps.getName();
            this.isCharging = ps.isCharging();
            this.timeRemain = ps.getTimeRemainingInstant();
            this.isPowerOnLine = ps.isPowerOnLine();

            if (ps.getMaxCapacity() > 0) {
                this.batteryLifePercent  = Math.round((double) ps.getCurrentCapacity() / ps.getMaxCapacity() * 100);
            }
        }
    }

    @Override
    public String toString() {
        return "BatteryService{" +
                "batteryName='" + batteryName + '\'' +
                ", batteryLifePercent=" + batteryLifePercent +
                ", isCharging=" + isCharging +
                ", timeRemain=" + timeRemain +
                ", isPowerOnLine=" + isPowerOnLine +
                '}';
    }
}

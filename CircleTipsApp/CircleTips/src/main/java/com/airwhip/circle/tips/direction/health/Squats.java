package com.airwhip.circle.tips.direction.health;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

/**
 * Created by Whiplash on 10.03.14.
 */
public class Squats {

    private int count;

    private float minZ;
    private float maxZ;
    private Float firstZ;

    private long lastTime;

    public int getCount() {
        return count;
    }

    public void start(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            firstZ = event.values[2];
            maxZ = firstZ;
            minZ = firstZ;
            lastTime = System.currentTimeMillis();
        } else {
            Log.e("ERROR_APP", "Squats: wrong sensor (" + event.sensor.getType() + ")");
        }
    }

    public void update(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if (firstZ != null) {
                maxZ = Math.max(maxZ, event.values[2]);
                minZ = Math.min(minZ, event.values[2]);
                if (curTime - lastTime > 1000L && Math.abs(event.values[2] - firstZ) < 1f && maxZ - minZ > 10f) {
                    count++;
                    maxZ = firstZ;
                    minZ = firstZ;
                    lastTime = curTime;
                }
            }
        } else {
            Log.e("ERROR_APP", "Squats: wrong sensor (" + event.sensor.getType() + ")");
        }
    }
}

package com.github.isabsent.exoview.orientation;

public interface OnOrientationChangedListener {
    int SENSOR_UNKNOWN = -1;
    int SENSOR_PORTRAIT = SENSOR_UNKNOWN + 1;
    int SENSOR_LANDSCAPE = SENSOR_PORTRAIT + 1;

//    @IntDef({SENSOR_UNKNOWN, SENSOR_PORTRAIT, SENSOR_LANDSCAPE})
//    @Retention(RetentionPolicy.SOURCE)
//    @interface SensorOrientationType {
//
//    }


    void onChanged(int orientation);
}

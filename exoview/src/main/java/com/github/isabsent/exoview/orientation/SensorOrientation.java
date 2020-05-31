package com.github.isabsent.exoview.orientation;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.OrientationEventListener;

import static com.github.isabsent.exoview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE;
import static com.github.isabsent.exoview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT;
import static com.github.isabsent.exoview.orientation.OnOrientationChangedListener.SENSOR_UNKNOWN;

public class SensorOrientation {

    private int oldScreenOrientation = SENSOR_UNKNOWN;

    private final Context context;
    private final OrientationEventListener screenOrientationEventListener;

    public SensorOrientation(Context context, final OnOrientationChangedListener onOrientationChangedListener) {
        this.context = context;
        screenOrientationEventListener = new OrientationEventListener(context) {
            @Override
            public void onOrientationChanged(int orientation) {

                if (onOrientationChangedListener == null || !isScreenOpenRotate()) {
                    return;
                }

                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    onOrientationChangedListener.onChanged(SENSOR_UNKNOWN);
                    return;
                }

                if (orientation > 350 || orientation < 10) { //0
                    orientation = 0;
                } else if (orientation > 80 && orientation < 100) { //90
                    orientation = 90;
                } else if (orientation > 170 && orientation < 190) { //180
                    orientation = 180;
                } else if (orientation > 260 && orientation < 280) { //270
                    orientation = 270;
                } else {
                    return;
                }

                if (oldScreenOrientation == orientation) {
                    onOrientationChangedListener.onChanged(SENSOR_UNKNOWN);
                    return;
                }

                oldScreenOrientation = orientation;

                if (orientation == 0 || orientation == 180) {
                    onOrientationChangedListener.onChanged(SENSOR_PORTRAIT);
                } else {
                    onOrientationChangedListener.onChanged(SENSOR_LANDSCAPE);
                }
            }
        };
    }

    private boolean isScreenOpenRotate() {

        int gravity = 0;
        try {
            gravity = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(getClass().getSimpleName(), e.getMessage() + "");
        }
        return 1 == gravity;
    }

    public void enable() {
        screenOrientationEventListener.enable();
    }

    public void disable() {
        screenOrientationEventListener.disable();
    }
}

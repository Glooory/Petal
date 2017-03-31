package com.glooory.petal.app.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Glooory on 17/3/30.
 */

public class UIUtils {

    private UIUtils() {
    }

    public static int getScreenWidth(Context context) {
        return getPoint(context).x;
    }

    public static Point getPoint(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }
}

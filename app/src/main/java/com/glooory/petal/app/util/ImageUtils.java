package com.glooory.petal.app.util;

import android.text.TextUtils;

/**
 * Created by Glooory on 17/2/22.
 */

public class ImageUtils {

    private ImageUtils(){
    }

    /**
     * 检查是否为 Gif 格式的图片
     * @param type
     * @return
     */
    public static boolean isGif(String type) {
        if (type == null || TextUtils.isEmpty(type)) {
            return false;
        }
        if (type.contains("gif") || type.contains("GIF") || type.contains("Gif")) {
            return true;
        }
        return false;
    }

    public static float getAspectRatio(int width, int height) {
        float ratio = width / (float) height;
        if (ratio <= 0.7) {
            return 0.7F;
        }
        return ratio;
    }
}

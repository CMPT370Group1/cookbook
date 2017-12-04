package com.feasymax.cookbook.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Olya on 2017-11-19.
 * Converts image to byte array accepted by database and back
 */

public class DbBitmapUtility {

    /**
     * Convert from bitmap to byte array
     * @param bitmap
     * @return
     */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     * Convert from byte array to bitmap
     * @param image
     * @return
     */
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}

package com.feasymax.cookbook.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.FileNotFoundException;

/**
 * Created by Olya on 2017-11-14.
 * The class manages image decoding and resizing.
 */

public class Graphics {

    /**
     * Resize a bitmap image
     * @param image
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            float ratioImg = (float) image.getWidth() / (float) image.getHeight();
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioImg) {
                finalWidth = (int) ((float)maxHeight * ratioImg);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioImg);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    /**
     * Calculates a sample size value that is a power of two based on a target width and height
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Get the bitmap constrained by specified dimensions from resource
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    /**
     * Decode selected image in the specified size
     * @param selectedImage uri of the image
     * @param maxWidth
     * @param maxHeight
     * @return the bitmap decoded
     * @throws FileNotFoundException
     */
    public static Bitmap decodeUri(FragmentActivity activity, Uri selectedImage, int maxWidth, int maxHeight) throws FileNotFoundException {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                activity.getContentResolver().openInputStream(selectedImage), null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(
                activity.getContentResolver().openInputStream(selectedImage), null, options);
    }

    /**
     * Convert dp to pixels
     * @param res
     * @param dp
     * @return
     */
    public static int dpToPx(Resources res, int dp) {
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}

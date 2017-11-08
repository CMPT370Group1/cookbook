package com.feasymax.cookbook.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasymax.cookbook.R;

import java.io.FileNotFoundException;

/**
 * Created by Olya on 21/09/2017.
 */

public class RecipeAddFragment extends Fragment {

    int MY_READ_PERMISSION_REQUEST_CODE =1 ;
    int PICK_IMAGE_REQUEST = 2;
    public static final int PICK_IMAGE = 1;

    View view;
    ImageView recipeImage;
    TextView recipeImageText;

    public RecipeAddFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_res_add, container, false);
        recipeImage = view.findViewById(R.id.recipeImage);
        recipeImageText = view.findViewById(R.id.myImageViewText);

        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        return view ;
    }

    private void pickImage()
    {

        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            Log.println(Log.INFO, "pickImage", "permission already granted");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                Log.println(Log.INFO, "pickImage", "need permission");
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_READ_PERMISSION_REQUEST_CODE);
                Log.println(Log.INFO, "pickImage", "after requestPermissions");
            }
            else
            {
                Log.println(Log.INFO, "pickImage", "old android version");
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] grantResults) {
        Log.println(Log.INFO, "onRequestPermRes", "in onRequestPermissionsResult");
        if (requestCode == MY_READ_PERMISSION_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.println(Log.INFO, "onRequestPermRes", "PERMISSION GRANTED");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
            else {
                Log.println(Log.INFO, "onRequestPermRes", "PERMISSION DENIED");
            }
        }
        else {
            Log.println(Log.INFO, "onRequestPermRes", "wrong request code");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null)
        {
            try
            {
                Uri selectedImage = data.getData();
                Bitmap bitmapImage = decodeUri(selectedImage, 500, 500);
                recipeImage.setImageBitmap(bitmapImage);
                recipeImageText.setVisibility(View.INVISIBLE);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage, int maxWidth, int maxHeight) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < maxWidth || height_tmp / 2 < maxHeight) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }
/*
    private static Bitmap resize(int imgWidth, int imgHeight, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            float ratioImg = (float) imgWidth / (float) imgHeight;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
*/
}

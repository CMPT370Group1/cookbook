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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.feasymax.cookbook.R;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Olya on 21/09/2017.
 * The fragment corresponds to fragment_res_add (ADD tab in My Recipes activity)
 * It has a form to add a new user recipe manually.
 */

public class RecipeAddFragment extends Fragment {

    /*
     * Format to display a fraction
     */
    final DecimalFormat DF = new DecimalFormat("#.############");

    /*
     * Codes to request permission to read storage and pick image from storage
     */
    int MY_READ_PERMISSION_REQUEST_CODE = 1 ;
    int PICK_IMAGE_REQUEST = 2;

    /*
     * Recipe attributes
     */
    private ImageView recipeImage;
    private TextView recipeImageText;
    private Spinner recipeCategory;
    private TableLayout recipeIngredientTable;
    private TableLayout recipeTagTable;
    private Map<Integer, View> recipeIngredients;
    private int ingredientIndex;
    private Map<Integer, View> recipeTags;
    private int tagIndex;
    private ImageButton addIngredient;
    private ImageButton addTag;

    /**
     * Required empty public constructor
     */
    public RecipeAddFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_add, container, false);

        // set up all the variables for components
        recipeImage = view.findViewById(R.id.recipeImage);
        recipeImageText = view.findViewById(R.id.myImageViewText);
        recipeCategory = view.findViewById(R.id.recipeCategory);
        addIngredient = view.findViewById(R.id.buttonAddIngredient);
        addTag = view.findViewById(R.id.buttonAddTag);
        recipeIngredientTable = view.findViewById(R.id.recipeIngredientsAdd);
        recipeTagTable = view.findViewById(R.id.recipeTagTable);

        recipeIngredients = new HashMap<>();
        ingredientIndex = 0;
        recipeTags = new HashMap<>();
        tagIndex = 0;

        // pick recipe image from SD card
        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewIngredient();
            }
        });

        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTag();
            }
        });

        // correctly display recipe categories in the dropdown spinner
        ArrayAdapter adapterCategory = ArrayAdapter.createFromResource(this.getContext(),
                R.array.categories, R.layout.spinner_item_left);
        adapterCategory.setDropDownViewResource(R.layout.spinner_dropdown_item);
        recipeCategory.setAdapter(adapterCategory);

        return view ;
    }

    /**
     * Check for permission to read storage and read the image (requesting permission if needed)
     */
    private void pickImage()
    {
        // if permission to access storage already granted
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            // pick image from SD card
            Log.println(Log.INFO, "pickImage", "permission already granted");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
        // no permission was granted
        else
        {
            // if permissions need to be asked at runtime, ask it
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

    /**
     * Check if permission to read storage was granted and initiate the intent to read the image
     * @param requestCode code of request, should be MY_READ_PERMISSION_REQUEST_CODE
     * @param permissions the app's permissions
     * @param grantResults permissions' flags indication if they were granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] grantResults) {
        Log.println(Log.INFO, "onRequestPermRes", "in onRequestPermissionsResult");
        // if permission granted was accessing storage, pick the image
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

    /**
     * Decode image from uri after permission to read storage was granted and set the appropriate
     *  imageview's src to it
     * @param requestCode code of request, should be PICK_IMAGE_REQUEST
     * @param resultCode code of the request's result indicating if the request was satisfied
     * @param data the uri of the image
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // image was picked alright
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null
                && data.getData() != null)
        {
            // try to decode the image (in the specified size) and set the imageview src to it
            try
            {
                Uri selectedImage = data.getData();
                Bitmap bitmapImage = decodeUri(selectedImage, 500, 500);
                recipeImage.setImageBitmap(bitmapImage);
                recipeImageText.setVisibility(View.INVISIBLE);
                recipeImage.setImageTintMode(null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Decode selected image in the specified size
     * @param selectedImage uri of the image
     * @param maxWidth
     * @param maxHeight
     * @return the bitmap decoded
     * @throws FileNotFoundException
     */
    private Bitmap decodeUri(Uri selectedImage, int maxWidth, int maxHeight) throws FileNotFoundException {
        // just read the image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o);

        // find the new image size and the number the image should be scaled by
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

        // decode the bitmap scaling it
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }

    private void addNewIngredient() {
        View tr = getActivity().getLayoutInflater().inflate(R.layout.ingredient_add_layout, null, false);

        Spinner unit = tr.findViewById(R.id.ingredientUnit);
        // correctly display recipe categories in the dropdown spinner
        ArrayAdapter adapterUnit = ArrayAdapter.createFromResource(this.getContext(),
                R.array.mass_volume_units, R.layout.spinner_item_left);
        adapterUnit.setDropDownViewResource(R.layout.spinner_dropdown_item);
        unit.setAdapter(adapterUnit);

        // Add row to TableLayout.
        recipeIngredientTable.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        recipeIngredients.put(ingredientIndex, tr);

        ImageButton deleteIngredient = tr.findViewById(R.id.buttonDeleteIngredient);
        deleteIngredient.setTag(ingredientIndex);
        deleteIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View row = recipeIngredients.get((int)v.getTag());
                row.setVisibility(View.GONE);
                recipeIngredients.remove(row);
            }
        });

        ingredientIndex++;

    }

    private void addNewTag() {
        View tr = getActivity().getLayoutInflater().inflate(R.layout.tag_add_layout, null, false);

        // Add row to TableLayout.
        recipeTagTable.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        recipeTags.put(tagIndex, tr);

        ImageButton deleteIngredient = tr.findViewById(R.id.buttonDeleteTag);
        deleteIngredient.setTag(tagIndex);
        deleteIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View row = recipeTags.get((int)v.getTag());
                row.setVisibility(View.GONE);
                recipeTags.remove(row);
            }
        });

        tagIndex++;

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

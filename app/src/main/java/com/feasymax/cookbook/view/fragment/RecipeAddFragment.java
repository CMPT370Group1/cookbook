package com.feasymax.cookbook.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Ingredient;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.RecipesActivity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Olya on 21/09/2017.
 * The fragment corresponds to fragment_res_add (ADD tab in My Recipes activity)
 * It has a form to add a new user recipe manually.
 */

public class RecipeAddFragment extends Fragment {

    public static final String FRAGMENT_ID = "RecipeAddFragment";

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
    protected EditText recipeTitle;
    protected Spinner recipeCategory;
    protected TableLayout recipeIngredientTable;
    protected Map<Integer, View> recipeIngredients;
    protected List<Ingredient> ingredientList;
    protected int ingredientIndex;
    protected EditText recipeDirections;
    protected EditText recipeDurationHour;
    protected EditText recipeDurationMin;
    protected TableLayout recipeTagTable;
    protected Map<Integer, View> recipeTags;
    protected List<String> tagList;
    protected int tagIndex;
    protected ImageView recipeImage;
    protected Bitmap recipeImageBitmap;
    protected Bitmap recipeIconBitmap;
    protected TextView recipeImageText;

    protected ImageButton ibtnAddIngredient;
    protected ImageButton ibtnAddTag;
    protected Button btnAddRecipe;

    protected Recipe editedRecipe = null;

    /**
     * Required empty public constructor
     */
    public RecipeAddFragment() {}

    //TODO: add the button to remove image
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_add, container, false);

        setHasOptionsMenu(true);

        // set up all the variables for components
        recipeTitle = view.findViewById(R.id.recipeTitleAdd);
        recipeCategory = view.findViewById(R.id.recipeCategoryAdd);
        recipeIngredientTable = view.findViewById(R.id.recipeIngredientsAdd);
        recipeDirections = view.findViewById(R.id.recipeDirectionsAdd);
        recipeDurationHour = view.findViewById(R.id.recipeDurationHour);
        recipeDurationMin = view.findViewById(R.id.recipeDurationMin);
        recipeTagTable = view.findViewById(R.id.recipeTagTableAdd);
        recipeImage = view.findViewById(R.id.recipeImageAdd);
        recipeImageText = view.findViewById(R.id.myImageViewTextAdd);

        ibtnAddIngredient = view.findViewById(R.id.buttonAddIngredient);
        ibtnAddTag = view.findViewById(R.id.buttonAddTag);
        btnAddRecipe = view.findViewById(R.id.buttonAddRecipe);

        recipeIngredients = new HashMap<>();
        ingredientList = new LinkedList<>();
        ingredientIndex = 0;
        recipeTags = new HashMap<>();
        tagList = new LinkedList<>();
        tagIndex = 0;

        recipeCategory.setSelection(12);

        // pick recipe image from SD card
        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        recipeImage.setImageDrawable(getResources().getDrawable(R.drawable.bread, null));
        recipeImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        ibtnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewIngredient(null);
            }
        });

        ibtnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTag(null);
            }
        });

        // correctly display recipe categories in the dropdown spinner
        ArrayAdapter adapterCategory = ArrayAdapter.createFromResource(this.getContext(),
                R.array.categories, R.layout.spinner_item_left);
        adapterCategory.setDropDownViewResource(R.layout.spinner_dropdown_item);
        recipeCategory.setAdapter(adapterCategory);

        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe(true, -1, -1);
            }
        });

        return view ;
    }

    /**
     * Check for permission to read storage and read the image (requesting permission if needed)
     */
    protected void pickImage()
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
                recipeImageBitmap = Graphics.decodeUri(getActivity(), selectedImage, 500, 500);
                recipeImage.setImageBitmap(recipeImageBitmap);
                recipeImageText.setVisibility(View.INVISIBLE);
                recipeImage.clearColorFilter();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * Add a row for a new ingredient in layout and set its field to ingr's attributes if not null
     * @param ingr
     */
    protected void addNewIngredient(Ingredient ingr) {
        View tr = getActivity().getLayoutInflater().inflate(R.layout.ingredient_add_layout, null, false);

        EditText name = tr.findViewById(R.id.ingredientName);

        Spinner unit = tr.findViewById(R.id.ingredientUnit);
        // correctly display recipe categories in the dropdown spinner
        ArrayAdapter adapterUnit = ArrayAdapter.createFromResource(this.getContext(),
                R.array.all_units, R.layout.spinner_item_left);
        adapterUnit.setDropDownViewResource(R.layout.spinner_dropdown_item);
        unit.setAdapter(adapterUnit);

        EditText quantity = tr.findViewById(R.id.ingredientQuantity);

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
                recipeIngredients.remove((int)v.getTag());
            }
        });

        ingredientIndex++;

        if (ingr != null) {
            name.setText(ingr.getName());
            unit.setSelection(ingr.getUnit());
            quantity.setText(String.valueOf(ingr.getQuantity()));
        }

    }

    /**
     * Add a row for a new tag in layout and set its text to tag if not null
     * @param tag
     */
    protected void addNewTag(String tag) {
        View tr = getActivity().getLayoutInflater().inflate(R.layout.tag_add_layout, null, false);

        EditText tagName = tr.findViewById(R.id.tagName);

        // Add row to TableLayout.
        recipeTagTable.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        recipeTags.put(tagIndex, tr);
        Log.println(Log.INFO, "recipeTags adding", recipeTags.toString());

        ImageButton deleteTag = tr.findViewById(R.id.buttonDeleteTag);
        deleteTag.setTag(tagIndex);
        deleteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View row = recipeTags.get((int)v.getTag());
                row.setVisibility(View.GONE);
                recipeTags.remove((int)v.getTag());
            }
        });

        tagIndex++;

        if (tag != null) {
            tagName.setText(tag);
        }
    }

    /**
     * Add new recipe
     * @param isNewRecipe is it a new user recipe or saved from Discover collection
     * @param recipeId
     */
    protected void addRecipe(boolean isNewRecipe, int recipeId, int category) {
        if (recipeTitle.getText().length() != 0) {
            Recipe recipe = new Recipe();
            if (!isNewRecipe) {
                recipe.setId(recipeId);
            }
            recipe.setTitle(recipeTitle.getText().toString().trim());
            recipe.setCategory(recipeCategory.getSelectedItemPosition());
            for (int ingr: recipeIngredients.keySet()) {
                View tr = recipeIngredients.get(ingr);
                EditText name = tr.findViewById(R.id.ingredientName);
                if (name.getText().length() != 0) {
                    Spinner unit = tr.findViewById(R.id.ingredientUnit);
                    EditText quantity = tr.findViewById(R.id.ingredientQuantity);
                    Double ingrQuantity = 0.0;
                    if (quantity.getText().length() != 0) {
                        ingrQuantity = Double.valueOf(quantity.getText().toString());
                    }
                    Ingredient ingredient = new Ingredient(name.getText().toString(),
                            ingrQuantity, unit.getSelectedItemPosition());
                    ingredientList.add(ingredient);
                    Log.println(Log.INFO, "ingredientList", ingredientList.toString());
                }
            }
            recipe.setIngredients(ingredientList);
            recipe.setDirections(recipeDirections.getText().toString().trim());
            if (recipeDurationHour.getText().length() != 0) {
                recipe.setDuration(Integer.parseInt(recipeDurationHour.getText().toString())*60);
            }
            if (recipeDurationMin.getText().length() != 0) {
                recipe.setDuration(recipe.getDuration() +
                        Integer.parseInt(recipeDurationMin.getText().toString()));
            }
            for (int tag: recipeTags.keySet()) {
                View tr = recipeTags.get(tag);
                EditText name = tr.findViewById(R.id.tagName);
                if (name.getText().length() != 0) {
                    String tagName = name.getText().toString();
                    tagList.add(tagName);
                    
                    Log.println(Log.INFO, "tagList", tagList.toString());
                }
            }

            recipe.setTags(tagList);

            if (recipeImageBitmap == null) {
                recipeImageBitmap = Graphics.decodeSampledBitmapFromResource(getResources(), R.drawable.no_image420, 420, 420);
            }
            recipe.setImage(recipeImageBitmap);

            Log.println(Log.INFO, "addRecipe", recipe.toString());

            boolean isOwner = true;
            if (!isNewRecipe) {
                isOwner = (editedRecipe != null) ? editedRecipe.isOwner() : false;
            }

            if (Application.addNewRecipe(isNewRecipe, recipe, isOwner, recipeIconBitmap, category) != -1) {
                Log.println(Log.INFO, "addRecipe", Application.getUserCollection().getCurRecipe().toString());
                Log.println(Log.INFO, "addRecipe", Application.getUserCollection().
                        getRecipes().toString());

                emptyFragment();
                enterRecipeViewFragment();
                Toast.makeText(getContext(), "The recipe has been added",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Log.println(Log.INFO, "addRecipe", "couldn't add recipe");
            }
        }

    }

    /**
     * Empty all fragment's views
     */
    protected void emptyFragment() {
        recipeTitle.setText("");
        recipeCategory.setSelection(12);
        recipeIngredientTable.removeAllViews();
        recipeIngredients.clear();
        recipeDirections.setText("");
        recipeDurationHour.setText("");
        recipeDurationMin.setText("");
        recipeTagTable.removeAllViews();
        recipeTags.clear();
        recipeImageBitmap = null;
        recipeIconBitmap = null;
        recipeImage.setImageDrawable(getResources().getDrawable(R.drawable.bread, null));
        recipeImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        recipeImageText.setVisibility(View.VISIBLE);

        recipeImage.requestFocus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Log.println(Log.INFO, "MENU","action_info has clicked");
                return true;
            default:
                Log.println(Log.INFO, "MENU","error");
                break;
        }

        return false;
    }

    /**
     * Go to fragment that displays recipe
     */
    protected void enterRecipeViewFragment() {
        recipeImage.setImageBitmap(null);
        recipeImage.destroyDrawingCache();
        ((RecipesActivity)getActivity()).navigateFragment(0, FRAGMENT_ID);
    }
}

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
import android.support.v4.app.FragmentTransaction;
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

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Ingredient;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.util.Graphics;

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

public class RecipeEditFragment extends Fragment {

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
    private EditText recipeTitle;
    private Spinner recipeCategory;
    private TableLayout recipeIngredientTable;
    private Map<Integer, View> recipeIngredients;
    private List<Ingredient> ingredientList;
    private int ingredientIndex;
    private EditText recipeDirections;
    private EditText recipeDurationHour;
    private EditText recipeDurationMin;
    private TableLayout recipeTagTable;
    private Map<Integer, View> recipeTags;
    private List<String> tagList;
    private int tagIndex;
    private ImageView recipeImage;
    private Bitmap recipeImageBitmap;
    private TextView recipeImageText;

    private ImageButton addIngredient;
    private ImageButton addTag;
    private Button editRecipe;

    private Recipe editedRecipe;

    /**
     * Required empty public constructor
     */
    public RecipeEditFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_edit, container, false);

        setHasOptionsMenu(true);

        editedRecipe = Application.getUserCurrentRecipe();

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

        addIngredient = view.findViewById(R.id.buttonAddIngredient);
        addTag = view.findViewById(R.id.buttonAddTag);
        editRecipe = view.findViewById(R.id.buttonEditRecipe);

        recipeIngredients = new HashMap<>();
        ingredientList = new LinkedList<>();
        ingredientIndex = 0;
        recipeTags = new HashMap<>();
        tagList = new LinkedList<>();
        tagIndex = 0;

        recipeTitle.setText(editedRecipe.getTitle());
        recipeCategory.setSelection(editedRecipe.getCategory());
        recipeDirections.setText(editedRecipe.getDirections());
        recipeDurationHour.setText(String.valueOf(editedRecipe.getDuration() / 60));
        recipeDurationMin.setText(String.valueOf(editedRecipe.getDuration() % 60));

        //
        recipeImageBitmap = editedRecipe.getImage();
        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        recipeImage.setImageBitmap(recipeImageBitmap);


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

        editRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRecipe();
            }
        });

        return view ;
    }

    /**
     * Check for permission to read storage and read the image (requesting permission if needed)
     */
    private void pickImage()
    {
        // if permission to access storage already granted
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
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


    private void addNewIngredient() {
        View tr = getActivity().getLayoutInflater().inflate(R.layout.ingredient_add_layout, null, false);

        EditText name = tr.findViewById(R.id.ingredientName);

        Spinner unit = tr.findViewById(R.id.ingredientUnit);
        // correctly display recipe categories in the dropdown spinner
        ArrayAdapter adapterUnit = ArrayAdapter.createFromResource(this.getContext(),
                R.array.mass_volume_units, R.layout.spinner_item_left);
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
                recipeIngredients.remove(row);
            }
        });

        ingredientIndex++;

    }

    private void addNewTag() {
        View tr = getActivity().getLayoutInflater().inflate(R.layout.tag_add_layout, null, false);

        EditText tagName = tr.findViewById(R.id.tagName);

        // Add row to TableLayout.
        recipeTagTable.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        recipeTags.put(tagIndex, tr);

        ImageButton deleteTag = tr.findViewById(R.id.buttonDeleteTag);
        deleteTag.setTag(tagIndex);
        deleteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View row = recipeTags.get((int)v.getTag());
                row.setVisibility(View.GONE);
                recipeTags.remove(row);
            }
        });

        tagIndex++;

    }

    /**
     *
     */
    private void editRecipe() {
        if (recipeTitle.getText().length() != 0) {
            editedRecipe.setTitle(recipeTitle.getText().toString());
            editedRecipe.setCategory(recipeCategory.getSelectedItemPosition());
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
                }
            }
            editedRecipe.setIngredients(ingredientList);
            editedRecipe.setDirections(recipeDirections.getText().toString());
            if (recipeDurationHour.getText().length() != 0) {
                editedRecipe.setDuration(Integer.parseInt(recipeDurationHour.getText().toString())*60);
            }
            if (recipeDurationMin.getText().length() != 0) {
                editedRecipe.setDuration(editedRecipe.getDuration() +
                        Integer.parseInt(recipeDurationMin.getText().toString()));
            }
            for (int tag: recipeTags.keySet()) {
                View tr = recipeTags.get(tag);
                EditText name = tr.findViewById(R.id.tagName);
                if (name.getText().length() != 0) {
                    String tagName = name.getText().toString();
                    tagList.add(tagName);
                    
                    Log.println(Log.INFO, "ADD INGRED", name.getText().toString());
                }
            }

            editedRecipe.setTags(tagList);
            if (recipeImageBitmap == null) {
                recipeImageBitmap = Graphics.decodeSampledBitmapFromResource(getResources(), R.drawable.no_image420, 420, 420);
                recipeImageBitmap = Graphics.resize(recipeImageBitmap, 420, 420);
            }
            editedRecipe.setImage(recipeImageBitmap);

            Application.editRecipe(editedRecipe);

            Log.println(Log.INFO, "addRecipe", Application.getUserCurrentRecipe().toString());
            Log.println(Log.INFO, "addRecipe", Application.getUserCollectionRecipes().toString());

            emptyFragment();
            enterRecipeViewFragment();
            // TODO: maybe go to recipe view fragment displaying current fragment
        }

    }

    private void emptyFragment() {
        recipeTitle.setText("");
        recipeCategory.setSelection(12);
        recipeIngredientTable.removeAllViews();
        recipeIngredients.clear();
        ingredientList.clear();
        recipeDirections.setText("");
        recipeDurationHour.setText("");
        recipeDurationMin.setText("");
        recipeTagTable.removeAllViews();
        recipeTags.clear();
        tagList.clear();
        recipeImageBitmap = null;
        recipeImage.setImageDrawable(getResources().getDrawable(R.drawable.bread, null));
        recipeImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        recipeImageText.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

    protected void enterRecipeViewFragment() {
        RecipeViewFragment a2Fragment = new RecipeViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }
}

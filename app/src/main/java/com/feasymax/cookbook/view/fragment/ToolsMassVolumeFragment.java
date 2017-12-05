package com.feasymax.cookbook.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.entity.ConversionFactor;
import com.feasymax.cookbook.model.util.MassVolumeUnitConverter;
import com.feasymax.cookbook.model.util.UnitConverters;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class ToolsMassVolumeFragment extends Fragment {

    public static final String FRAGMENT_ID = "ToolsMassVolumeFragment";

    private EditText massVolumeNum1;
    private EditText massVolumeNum2;
    private EditText massVolumeNum2Editable;
    private Spinner massVolumeUnit1;
    private Spinner massVolumeUnit2;
    private Spinner massVolumeIngredient;
    private EditText massVolumeNewIngredient;
    private Button btnMassVolumeAdd;

    final DecimalFormat DF = new DecimalFormat("#.############");

    private List<ConversionFactor> list = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    public ToolsMassVolumeFragment() {
        list.add(new ConversionFactor("Add New", 1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tool_mass_volume, container, false);

        massVolumeNum1 = view.findViewById(R.id.massVolumeNum1);
        massVolumeNum2 = view.findViewById(R.id.massVolumeNum2);
        massVolumeNum2Editable = view.findViewById(R.id.massVolumeNum2Editable);
        massVolumeUnit1 = view.findViewById(R.id.massVolumeList1);
        massVolumeUnit2 = view.findViewById(R.id.massVolumeList2);
        massVolumeIngredient = view.findViewById(R.id.massVolumeIngredient);
        massVolumeNewIngredient = view.findViewById(R.id.massVolumeNewIngredient);
        btnMassVolumeAdd = view.findViewById(R.id.buttonAddConversion);
        setupConversion(massVolumeNum1,massVolumeNum2,true);
        setupConversion(massVolumeNum2Editable,massVolumeNum1,false);
        List<ConversionFactor> cfs;
        try {
            cfs = MassVolumeUnitConverter.makeListFromFile(view.getContext());
        } catch (IOException e) {
            Log.println(Log.ERROR, "getConversionFactors", "ERROR: unable to get conversionFactors from file" + e.toString());
            return null;
        }

        for (int i = 0; i < cfs.size(); i++) {
            list.add(cfs.get(i));
            names.add(cfs.get(i).name);
        }
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        massVolumeIngredient.setAdapter(adp1);

        massVolumeNewIngredient.setEnabled(false);
        btnMassVolumeAdd.setEnabled(false);

        btnMassVolumeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNewConversionEntered()) {
                    // hide keyboard
                    massVolumeNewIngredient.clearFocus();
                    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    // add the new ingredient to the list
                    // use massVolumeNum2Editable instead of massVolumeNum2


                    double massConverted = UnitConverters.MassToMass(UnitConverters.ParseFraction(massVolumeNum1.getText().toString()), massVolumeUnit1.getSelectedItemPosition(), 1);
                    double volumeConverted = UnitConverters.VolumeToVolume(UnitConverters.ParseFraction(massVolumeNum2.getText().toString()), massVolumeUnit2.getSelectedItemPosition(), 5);
                    try {
                        MassVolumeUnitConverter.AddConversionFactor(massVolumeNewIngredient.toString(), massConverted / volumeConverted, view.getContext());
                    } catch (IOException e) {
                        Log.println(Log.ERROR, "addConversionFactor", "ERROR: failed to add new conversionFactor " + e.toString());
                    }
                    // convert
                    //check whether unitA is a mass or a volume
                    double result = MassVolumeUnitConverter.MassToVolume(UnitConverters.ParseFraction(massVolumeNum1.getText().toString()), massVolumeUnit1.getSelectedItemPosition(), massVolumeUnit2.getSelectedItemPosition(), 0, "Mass");
                    list.add(new ConversionFactor(massVolumeNewIngredient.getText().toString(), result));
                    // set selection on the new ingredient
                    massVolumeIngredient.setSelection(list.size() - 1);
                    massVolumeNum1.setText("0");

                    massVolumeNum2.setText(DF.format(result));
                }
            }
        });

        massVolumeIngredient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // user adds new ingredient
                massVolumeNum2.setVisibility(View.INVISIBLE);
                massVolumeNum2Editable.setVisibility(View.VISIBLE);
                massVolumeNum1.setText("0");
                massVolumeNum2Editable.setText("0");
                massVolumeNewIngredient.setEnabled(true);
                btnMassVolumeAdd.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return view;
    }

    private void setupConversion(final EditText self, final EditText other, final boolean massFirst) {
        TextWatcher convertListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable.toString() != "0") && (editable.length() != 0)) {
                    ConvertNumber(self.getText().toString(), other, massFirst);
                }
            }
        };

        AdapterView.OnItemSelectedListener unitListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ConvertNumber(self.getText().toString(), other, massFirst);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(1);
            }
        };
        self.addTextChangedListener(convertListener);
        massVolumeUnit1.setOnItemSelectedListener(unitListener);
        massVolumeUnit2.setOnItemSelectedListener(unitListener);
    }

    private void ConvertNumber(String inputNumber, EditText other, boolean massFirst) {
        double num1 = UnitConverters.ParseFraction(inputNumber);
        int unit1, unit2;
        String unitType;
        if (massFirst) {
            unit1 = massVolumeUnit1.getSelectedItemPosition();
            unit2 = massVolumeUnit2.getSelectedItemPosition();
            unitType = "Mass";
        } else {
            unit1 = massVolumeUnit2.getSelectedItemPosition();
            unit2 = massVolumeUnit1.getSelectedItemPosition();
            unitType = "Volume";
        }
        Log.println(Log.INFO, "ConvertNumber", num1 + " " + unit1 + " " + unit2);
        double factor = list.get(names.indexOf(massVolumeIngredient.getSelectedItem())).factor;
        other.setText(DF.format(MassVolumeUnitConverter.MassToVolume(num1, unit1, unit2, factor, unitType)));
    }


    private boolean isNewConversionEntered() {
        Log.println(Log.INFO, "isNewConversionEntered", massVolumeNewIngredient.getText().toString()
                + ", " + massVolumeNum1.getText().toString() + ", " + massVolumeNum2Editable.getText().toString());
        return ((massVolumeNewIngredient.getText().toString().trim().length() != 0) &&
                (!massVolumeNum1.getText().toString().equals("0")) &&
                (!massVolumeNum2Editable.getText().toString().equals("0")));
    }
}
package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.util.UnitConverters;

import java.text.DecimalFormat;

/**
 * Created by Olya on 2017-09-21.
 */

public class ToolsMassFragment extends Fragment {

    public static final String FRAGMENT_ID = "ToolsMassFragment";

    private EditText massNum1;
    private EditText massNum2;
    private Spinner massUnit1;
    private Spinner massUnit2;

    final DecimalFormat DF = new DecimalFormat("#.############");

    /**
     * Required empty public constructor
     */
    public ToolsMassFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tool_mass, container, false);

        massNum1 = view.findViewById(R.id.massNum1);
        massNum2 = view.findViewById(R.id.massNum2);
        massUnit1 = view.findViewById(R.id.massList1);
        massUnit2 = view.findViewById(R.id.massList2);

        massNum1.clearFocus();

        setupConversion();

        return view ;
    }

    private void setupConversion() {
        TextWatcher convertListener = new TextWatcher()  {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable.toString() != "0") && (editable.length() != 0)) {
                    ConvertNumber();
                }
            }
        };

        AdapterView.OnItemSelectedListener unitListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ConvertNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(1);
            }
        };
        massNum1.addTextChangedListener(convertListener);
        massUnit1.setOnItemSelectedListener(unitListener);
        massUnit2.setOnItemSelectedListener(unitListener);
    }

    private void ConvertNumber() {
        double num1 = UnitConverters.ParseFraction(massNum1.getText().toString());
        int unit1 = massUnit1.getSelectedItemPosition();
        int unit2 = massUnit2.getSelectedItemPosition();
        Log.println(Log.INFO, "ConvertNumber", num1 + " " + unit1 + " " + unit2);
        massNum2.setText(DF.format(UnitConverters.MassToMass(num1, unit1, unit2)));
    }
}
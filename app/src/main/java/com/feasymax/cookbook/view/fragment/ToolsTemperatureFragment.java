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

public class ToolsTemperatureFragment extends Fragment {

    public static final String FRAGMENT_ID = "ToolsTemperatureFragment";

    private EditText tempNum1;
    private EditText tempNum2;
    private Spinner tempUnit1;
    private Spinner tempUnit2;

    final DecimalFormat DF = new DecimalFormat("#.############");

    /**
     * Required empty public constructor
     */
    public ToolsTemperatureFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tool_temperature, container, false);

        tempNum1 = view.findViewById(R.id.temperatureNum1);
        tempNum2 = view.findViewById(R.id.temperatureNum2);
        tempUnit1 = view.findViewById(R.id.temperatureList1);
        tempUnit2 = view.findViewById(R.id.temperatureList2);

        tempNum1.clearFocus();
        tempUnit2.setSelection(1);

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
                if (editable.length() != 0) {
                    ConvertNumber();
                }
            }
        };

        AdapterView.OnItemSelectedListener unitListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getId() == R.id.temperatureList1) {
                    tempUnit2.setSelection(1 - i);
                }
                else {
                    tempUnit1.setSelection(1 - i);
                }
                ConvertNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(1);
            }
        };
        tempNum1.addTextChangedListener(convertListener);
        tempUnit1.setOnItemSelectedListener(unitListener);
        tempUnit2.setOnItemSelectedListener(unitListener);
    }

    private void ConvertNumber() {
        double num1 = UnitConverters.ParseFraction(tempNum1.getText().toString());
        int unit1 = tempUnit1.getSelectedItemPosition();
        int unit2 = tempUnit2.getSelectedItemPosition();
        Log.println(Log.INFO, "ConvertNumber", num1 + " " + unit1 + " " + unit2);
        tempNum2.setText(DF.format(UnitConverters.TempToTemp(num1, unit1)));
    }
}
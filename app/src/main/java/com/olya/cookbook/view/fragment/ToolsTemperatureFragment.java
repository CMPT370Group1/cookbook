package com.olya.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.olya.cookbook.R;

/**
 * Created by Olya on 2017-09-21.
 */

public class ToolsTemperatureFragment extends Fragment {

    private EditText tempNum1;
    private EditText tempNum2;
    private Spinner tempUnit1;
    private Spinner tempUnit2;

    public ToolsTemperatureFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tool_temperature, container, false);

        tempNum1 = view.findViewById(R.id.temperatureNum1);
        tempNum2 = view.findViewById(R.id.temperatureNum2);
        tempUnit1 = view.findViewById(R.id.temperatureList1);
        tempUnit2 = view.findViewById(R.id.temperatureList2);

        // TODO: create TextWatcher for Num1 and AdapterView.OnItemSelectedListener Unit1 and Unit2
        // and then do massNum1.addTextChangedListener(that TextWatcher);
        // massUnit1.setOnItemSelectedListener(that listener);
        // massUnit2.setOnItemSelectedListener(that listener);

        return view ;
    }
}

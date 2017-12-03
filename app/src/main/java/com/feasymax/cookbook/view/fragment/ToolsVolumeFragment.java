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

public class ToolsVolumeFragment extends Fragment {

    public static final String FRAGMENT_ID = "ToolsVolumeFragment";

    private EditText volumeNum1;
    private EditText volumeNum2;
    private Spinner volumeUnit1;
    private Spinner volumeUnit2;

    final DecimalFormat DF = new DecimalFormat("#.############");

    /**
     * Required empty public constructor
     */
    public ToolsVolumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tool_volume, container, false);

        volumeNum1 = view.findViewById(R.id.volumeNum1);
        volumeNum2 = view.findViewById(R.id.volumeNum2);
        volumeUnit1 = view.findViewById(R.id.volumeList1);
        volumeUnit2 = view.findViewById(R.id.volumeList2);

        view.clearFocus();

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
        volumeNum1.addTextChangedListener(convertListener);
        volumeUnit1.setOnItemSelectedListener(unitListener);
        volumeUnit2.setOnItemSelectedListener(unitListener);
    }

    private void ConvertNumber() {
        double num1 = UnitConverters.ParseFraction(volumeNum1.getText().toString());
        int unit1 = volumeUnit1.getSelectedItemPosition();
        int unit2 = volumeUnit2.getSelectedItemPosition();
        Log.println(Log.INFO, "ConvertNumber", num1 + " " + unit1 + " " + unit2);
        volumeNum2.setText(DF.format(UnitConverters.VolumeToVolume(num1, unit1, unit2)));
    }
}
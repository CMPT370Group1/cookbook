package com.olya.cookbook.view.fragment;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.olya.cookbook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class ToolsMassVolumeFragment extends Fragment {

    private EditText massVolumeNum1;
    private EditText massVolumeNum2;
    private EditText massVolumeNum2Editable;
    private Spinner massVolumeUnit1;
    private Spinner massVolumeUnit2;
    private Spinner massVolumeIngredient;
    private EditText massVolumeNewIngredient;
    private Button btnMassVolumeAdd;

    final List<String> list = new ArrayList<>();

    public ToolsMassVolumeFragment() {
        // Required empty public constructor
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

        list.add("Add New");
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        massVolumeIngredient.setAdapter(adp1);

        massVolumeNewIngredient.setEnabled(false);
        btnMassVolumeAdd.setEnabled(false);

        btnMassVolumeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide keyboard
                massVolumeNewIngredient.clearFocus();
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                // add the new ingredient to the list
                list.add(massVolumeNewIngredient.getText().toString());
                // convert

                // set selection on the new ingredient
                massVolumeIngredient.setSelection(list.size() - 1);
            }
        });

        massVolumeIngredient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (massVolumeIngredient.getItemAtPosition(i).equals("Add New")) {
                    massVolumeNum2.setVisibility(View.INVISIBLE);
                    massVolumeNum2Editable.setVisibility(View.VISIBLE);
                    massVolumeNum1.setText("0");
                    massVolumeNum2Editable.setText("0");
                    massVolumeNewIngredient.setEnabled(true);
                    btnMassVolumeAdd.setEnabled(true);
                }
                else {
                    massVolumeNum2.setVisibility(View.VISIBLE);
                    massVolumeNum2Editable.setVisibility(View.INVISIBLE);
                    massVolumeNewIngredient.setText("");
                    massVolumeNewIngredient.setEnabled(false);
                    btnMassVolumeAdd.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view ;
    }
}

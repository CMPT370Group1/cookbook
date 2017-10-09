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

public class ToolsMassFragment extends Fragment {

    private EditText massNum1;
    private EditText massNum2;
    private Spinner massUnit1;
    private Spinner massUnit2;

    public ToolsMassFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tool_mass, container, false);

        massNum1 = view.findViewById(R.id.massNum1);
        massNum2 = view.findViewById(R.id.massNum2);
        massUnit1 = view.findViewById(R.id.massList1);
        massUnit2 = view.findViewById(R.id.massList1);

        return view ;
    }
}

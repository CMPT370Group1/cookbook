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

public class ToolsVolumeFragment extends Fragment {

    private EditText volumeNum1;
    private EditText volumeNum2;
    private Spinner volumeUnit1;
    private Spinner volumeUnit2;

    public ToolsVolumeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tool_volume, container, false);

        volumeNum1 = view.findViewById(R.id.volumeNum1);
        volumeNum2 = view.findViewById(R.id.volumeNum2);
        volumeUnit1 = view.findViewById(R.id.volumeList1);
        volumeUnit2 = view.findViewById(R.id.volumeList2);

        return view ;
    }
}

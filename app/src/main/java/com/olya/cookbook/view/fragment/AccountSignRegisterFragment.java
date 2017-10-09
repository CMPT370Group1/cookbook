package com.olya.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.olya.cookbook.R;

/**
 * Created by Olya on 2017-09-21.
 */

public class AccountSignRegisterFragment extends Fragment{

    public AccountSignRegisterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_acc_sign_register, container, false);

        return view ;
    }
}

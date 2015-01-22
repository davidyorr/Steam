package com.mangoshine.steam.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mangoshine.steam.MainActivity;
import com.mangoshine.steam.R;

public class AbstractBaseFragment extends Fragment {
    public static final String ARG_PLACE_NUMER = "place_number";

    protected int mLayout;
    protected Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(mLayout, container, false);
        int i = getArguments().getInt(ARG_PLACE_NUMER);
        String place = getResources().getStringArray(R.array.places_array)[i];

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((ActionBarActivity)getActivity()).setSupportActionBar(mToolbar);

        ((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity)getActivity()).sync();

        getActivity().setTitle(place);
        return rootView;
    }

    protected void setTitle(String title) {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(title);
    }
}

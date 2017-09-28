package com.example.mt.menitest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mladen.todorovic on 9/27/2017.
 */

public class tabDostavljeno  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_dostavljeno, container, false);
        return rootView;
    }
}

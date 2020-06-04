/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.soshoplus.timeline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class timelineFragment extends Fragment {
    
    public timelineFragment () {
        // Required empty public constructor
    }
    
    /*TODO NULL exception zipo AVOID AVOID*/
    
    /*initializing a view and inflate it */
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }
}

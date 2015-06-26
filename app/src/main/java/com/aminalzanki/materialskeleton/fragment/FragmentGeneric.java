package com.aminalzanki.materialskeleton.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aminalzanki.materialskeleton.R;

/**
 * Created by nikmuhammad on 6/26/15.
 */
public class FragmentGeneric extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_tab_generic, container, false);

        return view;
    }
}

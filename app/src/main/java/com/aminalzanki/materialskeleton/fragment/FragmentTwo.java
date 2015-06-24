package com.aminalzanki.materialskeleton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aminalzanki.materialskeleton.R;

/**
 * Created by nikmuhammad on 6/23/15.
 */
public class FragmentTwo extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_two, container, false);

        return view;
    }
}

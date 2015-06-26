package com.aminalzanki.materialskeleton.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aminalzanki.materialskeleton.R;
import com.aminalzanki.materialskeleton.activity.tabs.SwipeTabIconActivity;
import com.aminalzanki.materialskeleton.activity.tabs.SwipeTabTextActivity;
import com.aminalzanki.materialskeleton.activity.tabs.TabIconActivity;
import com.aminalzanki.materialskeleton.activity.tabs.TabTextActivity;

/**
 * Created by nikmuhammad on 6/23/15.
 */
public class FragmentOne extends Fragment implements View.OnClickListener
{

    private Intent mIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_one, container, false);

        Button mTabText = (Button) view.findViewById(R.id.tab_with_text);
        Button mSwipeTabText = (Button) view.findViewById(R.id.swipe_tab_with_text);
        Button mTabIcon = (Button) view.findViewById(R.id.tab_with_icon);
        Button mSwipeTabIcon = (Button) view.findViewById(R.id.swipe_tab_with_icon);

        mTabText.setOnClickListener(this);
        mSwipeTabText.setOnClickListener(this);
        mTabIcon.setOnClickListener(this);
        mSwipeTabIcon.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        final Activity activity = this.getActivity();
        if (activity == null)
        {
            return;
        }

        int id = v.getId();
        if (id == R.id.tab_with_text)
        {
            this.mIntent = new Intent(activity, TabTextActivity.class);
        }
        else if (id == R.id.swipe_tab_with_text)
        {
            this.mIntent = new Intent(activity, SwipeTabTextActivity.class);
        }
        else if (id == R.id.tab_with_icon)
        {
            this.mIntent = new Intent(activity, TabIconActivity.class);
        }
        else if (id == R.id.swipe_tab_with_icon)
        {
            this.mIntent = new Intent(activity, SwipeTabIconActivity.class);
        }

        // Start activity
        startActivity(mIntent);
    }
}

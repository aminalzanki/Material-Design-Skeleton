package com.aminalzanki.materialskeleton.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nikmuhammad on 6/21/15.
 */
public class BaseActivity extends AppCompatActivity
{
    private FragmentManager mFragmentManager;
    private ActionBar       mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public FragmentManager getSafeFragmentManager()
    {
        if (this.mFragmentManager == null)
        {
            this.mFragmentManager = this.getSupportFragmentManager();
        }

        return this.mFragmentManager;
    }

    public ActionBar getSafeActionBar()
    {
        if (this.mActionBar == null)
        {
            this.mActionBar = this.getSupportActionBar();
        }

        return this.mActionBar;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}

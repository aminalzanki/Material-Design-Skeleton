package com.aminalzanki.materialskeleton.activity;

import java.util.ArrayList;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aminalzanki.materialskeleton.R;
import com.aminalzanki.materialskeleton.adapter.DrawerAdapter;
import com.aminalzanki.materialskeleton.fragment.FragmentOne;
import com.aminalzanki.materialskeleton.ui.Items;

public class MainActivity extends BaseActivity
{
    private String[]              mDrawerTitles;
    private TypedArray            mDrawerIcons;
    private ArrayList<Items>      mDrawerItems;
    private DrawerLayout          mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView              mDrawerList;
    private CharSequence          mDrawerTitle;
    private CharSequence          mTitle;
    private int                   mCurrentPosition;

    private Fragment              mFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
        }

        this.mDrawerTitles = getResources().getStringArray(R.array.drawer_titles);
        this.mDrawerIcons = getResources().obtainTypedArray(R.array.drawer_icons);
        this.mDrawerItems = new ArrayList<>();
        this.mDrawerList = (ListView) findViewById(R.id.drawer_left);

        for (int i = 0; i < this.mDrawerTitles.length; i++)
        {
            this.mDrawerItems.add(new Items(this.mDrawerTitles[i], this.mDrawerIcons.getResourceId(i, -(i + 1))));
        }

        this.mTitle = this.mDrawerTitle = getTitle();
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout, toolbar, R.string.drawer_opened,
                        R.string.drawer_closed)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                MainActivity.this.getSafeActionBar().setTitle(MainActivity.this.mTitle);
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                MainActivity.this.getSafeActionBar().setTitle(MainActivity.this.mDrawerTitle);
            }
        };

        // Set drawer toggle as drawer listener
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);

        LayoutInflater inflater = this.getLayoutInflater();
        final ViewGroup header = (ViewGroup) inflater.inflate(R.layout.drawer_header, this.mDrawerList, false);

        this.mDrawerList.addHeaderView(header, null, true);

        // Set width of drawer
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) this.mDrawerList.getLayoutParams();
        lp.width = calculateDrawerWidth();
        this.mDrawerList.setLayoutParams(lp);

        // Set adapter for the listview
        this.mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(), this.mDrawerItems));

        // Set the list click listener
        this.mDrawerList.setOnItemClickListener(new DrawerItemClickListenr());

        this.getSafeActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSafeActionBar().setHomeButtonEnabled(true);

        this.preSelectItem(1);
    }

    private int calculateDrawerWidth()
    {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        Display display = getWindowManager().getDefaultDisplay();
        int width;
        if (Build.VERSION.SDK_INT >= 13)
        {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        }
        else
        {
            width = display.getWidth(); // deprecated
        }

        return width - actionBarHeight;
    }

    private void preSelectItem(int position)
    {
        if (this.mFragment == null)
        {
            this.selectItem(position, false);
            this.mCurrentPosition = position;
        }
    }

    /**
     * Swipes fragments in the main container
     */
    private void selectItem(int position, boolean forceRefresh)
    {
        if (isFinishing())
        {
            return;
        }

        // Close drawer
        this.mDrawerLayout.closeDrawer(this.mDrawerList);

        // Prevent reload same fragment
        if (!forceRefresh && position == this.mCurrentPosition)
        {
            return;
        }

        switch (position)
        {
            case 0:
                break;
            case 1:
                this.mFragment = new FragmentOne();
                break;
            case 2:
                break;
            default:
                this.mFragment = new Fragment();
                break;
        }

        if (this.mFragment != null)
        {
            // Insert the fragment by replacing the existing fragment
            FragmentManager fragmentManager = this.getSafeFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, this.mFragment).commitAllowingStateLoss();
        }

        // Highlight the selected item, update the title, and close the drawer
        this.mDrawerList.setItemChecked(position, true);
        if (position != 0)
        {
            setTitle(this.mDrawerTitles[position - 1]);
        }
    }

    private class DrawerItemClickListenr implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id)
        {
            MainActivity.this.selectItem(position, false);
            MainActivity.this.mCurrentPosition = position;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        this.mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title)
    {
        this.mTitle = title;
        this.getSafeActionBar().setTitle(this.mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

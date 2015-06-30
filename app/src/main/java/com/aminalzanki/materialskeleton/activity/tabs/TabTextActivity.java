package com.aminalzanki.materialskeleton.activity.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aminalzanki.materialskeleton.R;
import com.aminalzanki.materialskeleton.activity.BaseActivity;
import com.aminalzanki.materialskeleton.fragment.FragmentGeneric;
import com.aminalzanki.materialskeleton.lib.tabs.MaterialTab;
import com.aminalzanki.materialskeleton.lib.tabs.MaterialTabHost;
import com.aminalzanki.materialskeleton.lib.tabs.MaterialTabListener;

/**
 * Created by nikmuhammad on 6/26/15.
 */
public class TabTextActivity extends BaseActivity implements MaterialTabListener
{
    private ViewPager        mPager;
    private ViewPagerAdapter mAdapter;
    private MaterialTabHost  mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabtext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.getSafeActionBar().setDisplayHomeAsUpEnabled(true);

        this.mTabHost = (MaterialTabHost) findViewById(R.id.tab_host);
        this.mPager = (ViewPager) findViewById(R.id.pager);
        this.mAdapter = new ViewPagerAdapter(this.getSafeFragmentManager());

        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                // Don't care
            }

            @Override
            public void onPageSelected(int position)
            {
                // Change the fragment once user swipe the viewpager
                TabTextActivity.this.mTabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                // Don't care
            }
        });

        // Add all tabs from pagerAdapter data
        for (int i = 0; i < this.mAdapter.getCount(); i++)
        {
            this.mTabHost.addTab(this.mTabHost.newTab().setTitle(this.mAdapter.getPageTitle(i)).setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(MaterialTab tab)
    {
        this.mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab)
    {
        // Don't care
    }

    @Override
    public void onTabUnselected(MaterialTab tab)
    {
        // Don't care
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter
    {

        public ViewPagerAdapter(final FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return new FragmentGeneric();
        }

        @Override
        public int getCount()
        {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return getString(R.string.custom_tab_title) + " " + position;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }
}

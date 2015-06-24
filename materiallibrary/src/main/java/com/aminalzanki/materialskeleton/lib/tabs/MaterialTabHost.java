package com.aminalzanki.materialskeleton.lib.tabs;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aminalzanki.materialskeleton.lib.R;

/**
 * A Toolbar that contains multiple tabs
 *
 * Created by nikmuhammad on 6/24/15.
 */
public class MaterialTabHost extends RelativeLayout implements View.OnClickListener
{
    private List<MaterialTab>    mTabs;
    private HorizontalScrollView mScrollView;
    private LinearLayout         mLayout;
    private ImageButton          mLeft;
    private ImageButton          mRight;

    private int                  primaryColor;
    private int                  accentColor;
    private int                  titleColor;
    private int                  iconColor;
    private float                density;

    private boolean              hasIcon;
    private boolean              isTablet;
    private boolean              isScrollable;

    private static int           tabSelected;

    public MaterialTabHost(Context context)
    {
        this(context, null);
    }

    public MaterialTabHost(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public MaterialTabHost(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        this.mScrollView = new HorizontalScrollView(context);
        this.mScrollView.setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);
        this.mScrollView.setHorizontalFadingEdgeEnabled(false);
        this.mLayout = new LinearLayout(context);
        this.mScrollView.addView(this.mLayout);

        // Get attributes
        if (attrs != null)
        {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialTabHost, 0, 0);

            try
            {
                this.hasIcon = a.getBoolean(R.styleable.MaterialTabHost_hasIcons, false);
                this.primaryColor = a.getColor(R.styleable.MaterialTabHost_primaryColor, Color.parseColor("#009688"));
                this.accentColor = a.getColor(R.styleable.MaterialTabHost_accentColor, Color.parseColor("#00b0ff"));
                this.titleColor = a.getColor(R.styleable.MaterialTabHost_titleColor, Color.WHITE);
                this.iconColor = a.getColor(R.styleable.MaterialTabHost_iconColor, Color.WHITE);
            }
            finally
            {
                a.recycle();
            }
        }
        else
        {
            this.hasIcon = false;
        }

        this.isInEditMode();

        this.isScrollable = false;
        this.isTablet = this.getResources().getBoolean(R.bool.isTablet);
        this.density = this.getResources().getDisplayMetrics().density;

        tabSelected = 0;

        // Initiate tabs list
        this.mTabs = new LinkedList<MaterialTab>();
        super.setBackgroundColor(this.primaryColor);
    }

    public void addTab(final MaterialTab tab)
    {
        // Add properties to tab
        tab.setAccentColor(this.accentColor);
        tab.setPrimaryColor(this.primaryColor);
        tab.setTitleColor(this.titleColor);
        tab.setIconColor(this.iconColor);
        tab.setPosition(this.mTabs.size());

        // Insert new tab in the list
        this.mTabs.add(tab);

        if (this.mTabs.size() == 4 && !this.hasIcon)
        {
            this.isScrollable = true;
        }

        if (this.mTabs.size() == 6 && this.hasIcon)
        {
            this.isScrollable = true;
        }
    }

    public MaterialTab newTab()
    {
        return new MaterialTab(this.getContext(), this.hasIcon);
    }

    public void setSelectedNavigationItem(final int position)
    {
        if (position < 0 || position > this.mTabs.size())
        {
            throw new RuntimeException("Index overflow");
        }
        else
        {
            for (int i = 0; i < this.mTabs.size(); i++)
            {
                MaterialTab tab = this.mTabs.get(i);

                if (i == position)
                {
                    tab.enableTab();
                }
                else
                {
                    this.mTabs.get(i).disableTab();
                }
            }

            if (this.isScrollable)
            {
                this.scrollTo(position);
            }

            tabSelected = position;
        }
    }

    private void scrollTo(final int position)
    {
        int totalWidth = 0;
        for (int i = 0; i < position; i++)
        {
            int width = this.mTabs.get(i).getView().getWidth();
            if (width == 0)
            {
                if (!this.isTablet)
                {
                    width = (int) (this.mTabs.get(i).getTabMinWidth() + (24 * this.density));
                }
                else
                {
                    width = (int) (this.mTabs.get(i).getTabMinWidth() + (48 * this.density));
                }

            }

            totalWidth += width;
        }
        this.mScrollView.smoothScrollTo(totalWidth, 0);
    }

    @Override
    public void removeAllViews()
    {
        for (int i = 0; i < this.mTabs.size(); i++)
        {
            this.mTabs.remove(i);
        }

        this.mLayout.removeAllViews();
        super.removeAllViews();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        if (this.getWidth() != 0 && this.mTabs.size() != 0)
        {
            this.notifyDataChanged();
        }
    }

    public void notifyDataChanged()
    {
        super.removeAllViews();
        this.mLayout.removeAllViews();

        if (!this.isScrollable)
        {
            // not scrollable tabs
            int tabWidth = this.getWidth() / this.mTabs.size();

            // set params for resizing tabs width
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tabWidth,
                            HorizontalScrollView.LayoutParams.MATCH_PARENT);
            for (MaterialTab t : this.mTabs)
            {
                this.mLayout.addView(t.getView(), params);
            }

        }
        else
        {
            // scrollable tab

            if (!isTablet)
            {
                for (int i = 0; i < this.mTabs.size(); i++)
                {
                    LinearLayout.LayoutParams params;
                    MaterialTab tab = this.mTabs.get(i);

                    int tabWidth = (int) (tab.getTabMinWidth() + (24 * density)); // 12dp + text/icon width + 12dp

                    if (i == 0)
                    {
                        // first tab
                        View view = new View(this.mLayout.getContext());
                        view.setMinimumWidth((int) (60 * density));
                        this.mLayout.addView(view);
                    }

                    params = new LinearLayout.LayoutParams(tabWidth, HorizontalScrollView.LayoutParams.MATCH_PARENT);
                    this.mLayout.addView(tab.getView(), params);

                    if (i == this.mTabs.size() - 1)
                    {
                        // last tab
                        View view = new View(this.mLayout.getContext());
                        view.setMinimumWidth((int) (60 * density));
                        this.mLayout.addView(view);
                    }
                }
            }
            else
            {
                // is a tablet
                for (int i = 0; i < this.mTabs.size(); i++)
                {
                    LinearLayout.LayoutParams params;
                    MaterialTab tab = this.mTabs.get(i);

                    int tabWidth = (int) (tab.getTabMinWidth() + (48 * density)); // 24dp + text/icon width + 24dp

                    params = new LinearLayout.LayoutParams(tabWidth, HorizontalScrollView.LayoutParams.MATCH_PARENT);
                    this.mLayout.addView(tab.getView(), params);
                }
            }
        }

        if (this.isTablet && this.isScrollable)
        {
            // if device is a tablet and have scrollable tabs add right and mLeft arrows
            Resources res = getResources();

            this.mLeft = new ImageButton(this.getContext());
            this.mLeft.setId(R.id.left);
            this.mLeft.setImageDrawable(res.getDrawable(R.drawable.left_arrow));
            this.mLeft.setBackgroundColor(Color.TRANSPARENT);
            this.mLeft.setOnClickListener(this);

            // set 56 dp width and 48 dp height
            RelativeLayout.LayoutParams paramsLeft = new LayoutParams((int) (56 * density), (int) (48 * density));
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.addView(mLeft, paramsLeft);

            this.mRight = new ImageButton(this.getContext());
            this.mRight.setId(R.id.right);
            this.mRight.setImageDrawable(res.getDrawable(R.drawable.right_arrow));
            this.mRight.setBackgroundColor(Color.TRANSPARENT);
            this.mRight.setOnClickListener(this);

            RelativeLayout.LayoutParams paramsRight = new LayoutParams((int) (56 * density), (int) (48 * density));
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.addView(this.mRight, paramsRight);

            RelativeLayout.LayoutParams paramsScroll = new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT);
            paramsScroll.addRule(RelativeLayout.LEFT_OF, R.id.right);
            paramsScroll.addRule(RelativeLayout.RIGHT_OF, R.id.left);
            this.addView(this.mScrollView, paramsScroll);
        }
        else
        {
            // if is not a tablet add only scrollable content
            RelativeLayout.LayoutParams paramsScroll = new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT);
            this.addView(this.mScrollView, paramsScroll);
        }

        this.setSelectedNavigationItem(tabSelected);
    }

    public MaterialTab getCurrentTab()
    {
        for (MaterialTab tab : this.mTabs)
        {
            if (tab.isSelected())
            {
                return tab;
            }
        }

        return null;
    }

    @Override
    public void onClick(View v)
    {
        // on tablet left/right button clicked
        int currentPosition = this.getCurrentTab().getPosition();

        if (v.getId() == R.id.right && currentPosition < this.mTabs.size() - 1)
        {
            currentPosition++;

            // set next tab selected
            this.setSelectedNavigationItem(currentPosition);

            // change fragment
            this.mTabs.get(currentPosition).getTabListener().onTabSelected(this.mTabs.get(currentPosition));
            return;
        }

        if (v.getId() == R.id.left && currentPosition > 0)
        {
            currentPosition--;

            // set previous tab selected
            this.setSelectedNavigationItem(currentPosition);
            // change fragment
            this.mTabs.get(currentPosition).getTabListener().onTabSelected(this.mTabs.get(currentPosition));
            return;
        }
    }
}

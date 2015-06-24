package com.aminalzanki.materialskeleton.lib.tabs;

import java.util.Locale;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aminalzanki.materialskeleton.lib.R;
import com.aminalzanki.materialskeleton.lib.util.RevealColorView;

/**
 * Material design tab
 * Created by nikmuhammad on 6/24/15.
 */
public class MaterialTab implements View.OnTouchListener
{
    private static final int    REVEAL_DURATION = 400;
    private static final int    HIDE_DURATION   = 500;
    private View                mView;
    private ImageView           mIcon;
    private TextView            mTitle;
    private RevealColorView     mBackground;
    private ImageView           mIndicator;

    private Resources           mRes;
    private MaterialTabListener mListener;
    private Drawable            mIconDrawable;

    private int                 titleColor;
    private int                 iconColor;
    private int                 primaryColor;
    private int                 accentColor;

    private boolean             isActive;
    private boolean             hasIcon;
    private int                 position;
    private float               density;
    private Point               mLastTouchedPoint;

    public MaterialTab(final Context context, final boolean hasIcon)
    {
        this.hasIcon = hasIcon;
        this.mRes = context.getResources();
        this.density = this.mRes.getDisplayMetrics().density;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            if (!hasIcon)
            {
                this.mView = LayoutInflater.from(context).inflate(R.layout.tab, null);
                this.mTitle = (TextView) this.mView.findViewById(R.id.title);
            }
            else
            {
                this.mView = LayoutInflater.from(context).inflate(R.layout.tab_icon, null);
                this.mIcon = (ImageView) this.mView.findViewById(R.id.icon);
            }

            this.mIndicator = (ImageView) this.mView.findViewById(R.id.indicator);
        }
        else
        {
            if (!hasIcon)
            {
                this.mView = LayoutInflater.from(context).inflate(R.layout.material_tab, null);
                this.mTitle = (TextView) this.mView.findViewById(R.id.title);
            }
            else
            {
                this.mView = LayoutInflater.from(context).inflate(R.layout.material_tab_icon, null);
                this.mIcon = (ImageView) this.mView.findViewById(R.id.icon);
            }

            this.mBackground = (RevealColorView) this.mView.findViewById(R.id.reveal);
            this.mIndicator = (ImageView) this.mView.findViewById(R.id.indicator);
        }

        // Set touch listener
        this.mView.setOnTouchListener(this);

        this.isActive = false;
        this.titleColor = Color.WHITE;
        this.iconColor = Color.WHITE;
    }

    private boolean isDeviceSupportRipple()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public void setAccentColor(final int color)
    {
        this.accentColor = color;
        this.titleColor = color;
        this.iconColor = color;
    }

    public void setPrimaryColor(final int color)
    {
        this.primaryColor = color;

        if (isDeviceSupportRipple())
        {
            this.mBackground.setBackgroundColor(color);
        }
        else
        {
            this.mView.setBackgroundColor(color);
        }
    }

    public void setTitleColor(final int color)
    {
        this.titleColor = color;
        if (this.mTitle != null)
        {
            this.mTitle.setTextColor(color);
        }
    }

    public void setIconColor(final int color)
    {
        this.iconColor = color;
        if (this.mIcon != null)
        {
            this.mIcon.setColorFilter(color);
        }
    }

    public MaterialTab setTitle(final CharSequence title)
    {
        if (this.hasIcon)
        {
            throw new RuntimeException("You have icons, use icons instead");
        }

        this.mTitle.setText(title.toString().toUpperCase(Locale.US));
        return this;
    }

    public MaterialTab setIcon(final Drawable icon)
    {
        if (!hasIcon)
        {
            throw new RuntimeException("You have no icon to set, use text instead");
        }

        this.mIconDrawable = icon;

        this.mIcon.setImageDrawable(icon);
        this.setIconColor(this.iconColor);
        return this;
    }

    public void disableTab()
    {
        // Set 60% alpha to text color
        if (this.mTitle != null)
        {
            this.mTitle.setTextColor(Color.argb(0x99, Color.red(this.titleColor), Color.green(this.titleColor),
                            Color.blue(this.titleColor)));
        }

        // Set 60% alpha to icon color
        if (this.mIcon != null)
        {
            this.setIconAlpha(0x99);
        }

        // Set indicator to transparent
        this.mIndicator.setBackgroundColor(this.mRes.getColor(android.R.color.transparent));

        this.isActive = false;

        if (this.mListener != null)
        {
            this.mListener.onTabUnselected(this);
        }
    }

    public void enableTab()
    {
        if (this.mTitle != null)
        {
            this.mTitle.setText(this.titleColor);
        }

        if (this.mIcon != null)
        {
            this.setIconAlpha(0xFF);
        }

        // Set accent color to indicator view
        this.mIndicator.setBackgroundColor(this.accentColor);

        this.isActive = true;
    }

    public boolean isSelected()
    {
        return this.isActive;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        this.mLastTouchedPoint = new Point();
        this.mLastTouchedPoint.x = (int) event.getX();
        this.mLastTouchedPoint.y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (!this.isDeviceSupportRipple())
            {
                this.mView.setBackgroundColor(Color.argb(0x80, Color.red(this.accentColor),
                                Color.green(this.accentColor), Color.blue(this.accentColor)));
            }

            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_CANCEL)
        {
            if (!this.isDeviceSupportRipple())
            {
                this.mView.setBackgroundColor(this.primaryColor);
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if (!this.isDeviceSupportRipple())
            {
                this.mView.setBackgroundColor(this.primaryColor);
            }
            else
            {
                this.mBackground.reveal(
                                this.mLastTouchedPoint.x,
                                this.mLastTouchedPoint.y,
                                Color.argb(0x80, Color.red(this.accentColor), Color.green(this.accentColor),
                                                Color.blue(this.accentColor)), 0, REVEAL_DURATION,
                                new Animator.AnimatorListener()
                                {

                                    @Override
                                    public void onAnimationStart(Animator animation)
                                    {
                                        // don't care
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation)
                                    {
                                        MaterialTab.this.mBackground.reveal(MaterialTab.this.mLastTouchedPoint.x,
                                                        MaterialTab.this.mLastTouchedPoint.y,
                                                        MaterialTab.this.primaryColor, 0, HIDE_DURATION, null);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation)
                                    {
                                        // don't care
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation)
                                    {
                                        // don't care
                                    }
                                });
            }

            // Set the click
            if (this.mListener != null)
            {
                if (this.isActive)
                {
                    this.mListener.onTabReselected(this);
                }
                else
                {
                    this.mListener.onTabSelected(this);
                }
            }

            // If the tab is not activated, it will be active
            if (!this.isActive)
            {
                this.enableTab();
            }

            return true;
        }

        return false;
    }

    public View getView()
    {
        return this.mView;
    }

    public MaterialTab setTabListener(final MaterialTabListener listener)
    {
        this.mListener = listener;
        return this;
    }

    public MaterialTabListener getTabListener()
    {
        return this.mListener;
    }

    public int getPosition()
    {
        return this.position;
    }

    public void setPosition(final int position)
    {
        this.position = position;
    }

    private void setIconAlpha(final int paramInt)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            this.mIcon.setImageAlpha(paramInt);
            return;
        }

        this.mIcon.setColorFilter(Color.argb(paramInt, Color.red(this.iconColor), Color.green(this.iconColor), Color.blue(this.iconColor)));
    }

    private int getTitleLength()
    {
        String title = this.mTitle.getText().toString();
        Rect bound = new Rect();
        Paint titlePaint = this.mTitle.getPaint();
        titlePaint.getTextBounds(title, 0, title.length(), bound);

        return bound.width();
    }

    private int getIconWidth()
    {
        return (int) (this.density * 24);
    }

    public int getTabMinWidth()
    {
        if(this.hasIcon)
        {
            return this.getIconWidth();
        }
        else
        {
            return this.getTitleLength();
        }
    }
}

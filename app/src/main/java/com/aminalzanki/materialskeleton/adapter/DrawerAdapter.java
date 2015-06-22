package com.aminalzanki.materialskeleton.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aminalzanki.materialskeleton.R;
import com.aminalzanki.materialskeleton.ui.Items;

/**
 * Created by nikmuhammad on 6/22/15.
 */
public class DrawerAdapter extends BaseAdapter
{
    private Context          mContext;
    private ArrayList<Items> mDrawerItems;

    public DrawerAdapter(Context context, ArrayList<Items> drawerItems)
    {
        this.mContext = context;
        this.mDrawerItems = drawerItems;
    }

    @Override
    public int getCount()
    {
        return this.mDrawerItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.mDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_list_item, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.item_icon);
        TextView title = (TextView) convertView.findViewById(R.id.item_title);

        image.setImageResource(this.mDrawerItems.get(position).getIcon());
        title.setText(this.mDrawerItems.get(position).getTitle());

        return convertView;
    }
}

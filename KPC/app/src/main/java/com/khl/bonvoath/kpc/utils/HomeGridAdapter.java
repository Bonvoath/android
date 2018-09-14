package com.khl.bonvoath.kpc.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.khl.bonvoath.kpc.R;
import com.khl.bonvoath.kpc.entities.GridMenu;

import java.util.List;

public class HomeGridAdapter extends BaseAdapter {
    private List<GridMenu> data;
    public HomeGridAdapter(Context context, List<GridMenu> _data){
        data = _data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;

        if(convertView == null)
            gridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_item_view, parent, false);

        GridMenu menu = data.get(position);
        TextView title = gridView.findViewById(R.id.grid_item_title);
        ImageView icon = gridView.findViewById(R.id.grid_item_icon);
        title.setText(menu.getTitle());
        icon.setImageResource(menu.getIcon());
        return gridView;
    }
}

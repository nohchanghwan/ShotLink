package com.test.sy.shotlinkone;

/**
 * Created by jaemoon on 2016-11-24.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

    Context context;
    int layout;
    SQLManager.content data[];
    LayoutInflater inf;

    public GridViewAdapter(Context context, int layout, SQLManager.content data[]) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }   //position 은 배열의 넘버

    @Override
    public long getItemId(int position) {
        return position;
    }


/*    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.grid_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }



        holder.image.setImageBitmap(ImageHelper.getRoundedCornerBitmap(ImageRotater.SafeDecodeBitmapFile(data[position].filepath), 100));
        return row;
    }*/


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(R.layout.grid_item_layout, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.grid_image);
        iv.setImageBitmap(ImageHelper.getRoundedCornerBitmap(ImageRotater.SafeDecodeBitmapFile(data[position].filepath),100));

        return convertView;
    }


    static class ViewHolder {
        ImageView image;
    }

}
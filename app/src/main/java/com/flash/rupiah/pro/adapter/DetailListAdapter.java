package com.flash.rupiah.pro.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flash.rupiah.pro.R;

import java.util.List;

/**
 * Created by heise on 2018/6/11.
 */

public  class DetailListAdapter extends BaseAdapter {

    private Context context;
    private List<String> listValue;
    private String[] listName;
    ViewHolder viewHolder = null;

    public DetailListAdapter(Context context, List<String> listValue, String[] listName) {
        this.context = context;
        this.listValue = listValue;
        this.listName = listName;
    }

    @Override
    public int getCount() {
        return listName == null ? 0 : listName.length;
    }

    @Override
    public Object getItem(int position) {
        return listName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_center_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tv_center_name);
            viewHolder.tvValue = convertView.findViewById(R.id.tv_center_value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(listName [position]);
        viewHolder.tvValue.setText(listValue.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvValue;
    }

}

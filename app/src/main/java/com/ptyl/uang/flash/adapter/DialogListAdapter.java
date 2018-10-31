package com.ptyl.uang.flash.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ptyl.uang.flash.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heise on 2018/5/23.
 */

public class DialogListAdapter extends BaseAdapter {
    List<String> list =new ArrayList<>();
    Context mContext;
    ViewHolder viewHolder;
    public DialogListAdapter(List<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list == null? 0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.dialog_list_item,null);
            viewHolder.tvItem = convertView.findViewById(R.id.tv_item);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_dialog_item_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0){
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tvTitle.setVisibility(View.GONE);
        }
        viewHolder.tvItem.setText(list.get(position));
        return convertView;
    }

    static class ViewHolder{
        TextView tvItem;
        TextView tvTitle;
    }
}

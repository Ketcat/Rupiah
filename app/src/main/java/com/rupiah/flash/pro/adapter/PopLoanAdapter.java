package com.rupiah.flash.pro.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rupiah.flash.pro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heise on 2018/6/12.
 */

public class PopLoanAdapter extends BaseAdapter {
    List<String> list = new ArrayList<>();
    Context context;
    ViewHolder viewHolder = null;

    public PopLoanAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pop_loan_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvValue = convertView.findViewById(R.id.tv_pop_loan_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvValue.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tvValue;
    }
}

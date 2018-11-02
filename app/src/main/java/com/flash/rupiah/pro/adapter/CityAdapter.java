package com.flash.rupiah.pro.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.bean.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heise on 2018/6/6.
 */

public class CityAdapter  extends BaseAdapter{
    List<ProvinceBean.RegionsBean> list = new ArrayList<>();
    Activity activity;
    ViewHolder viewHolder = null;
    public CityAdapter(List<ProvinceBean.RegionsBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
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
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.city_list_item,null);
            viewHolder.tvCityName = convertView.findViewById(R.id.tv_item_city);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCityName.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        TextView tvCityName;
    }
}

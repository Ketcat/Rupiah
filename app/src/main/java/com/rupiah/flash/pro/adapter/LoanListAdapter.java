package com.rupiah.flash.pro.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rupiah.flash.pro.R;
import com.rupiah.flash.pro.bean.LoanListBean;

import java.util.List;

/**
 * Created by heise on 2018/6/11.
 */

public class LoanListAdapter extends BaseAdapter {
    Context context;
    List<LoanListBean> loanListBean;
    ViewHolder viewHolder = null;

    public LoanListAdapter(Context context, List<LoanListBean> loanListBean) {
        this.context = context;
        this.loanListBean = loanListBean;
    }

    @Override
    public int getCount() {
        return loanListBean == null ? 0 : loanListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return loanListBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(context, R.layout.loan_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tvCreateTime = convertView.findViewById(R.id.tv_time_create);
            viewHolder.tvOrderCode = convertView.findViewById(R.id.tv_order_code);
            viewHolder.tvLoanMoney = convertView.findViewById(R.id.tv_value1);
            viewHolder.tvDate = convertView.findViewById(R.id.tv_value2);
            viewHolder.tvState = convertView.findViewById(R.id.tv_value3);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoanListBean loanListBean = this.loanListBean.get(position);
        if (loanListBean == null){
            return null;
        }
        viewHolder.tvCreateTime.setText(loanListBean.getCreateTime());
        viewHolder.tvOrderCode.setText(loanListBean.getLoanAppId()+"");
        viewHolder.tvLoanMoney.setText(loanListBean.getAmount()+"");
        viewHolder.tvDate.setText(loanListBean.getPeriod() + (loanListBean.getPeriodUnit().equals
                ("D") ? "hari" : loanListBean.getPeriodUnit().equals("W") ? "minggu" :
                loanListBean.getPeriodUnit().equals("M") ? "bulan" : "tahun"));
        viewHolder.tvState.setText(loanListBean.getStatus());
        return  convertView;
    }

    class ViewHolder {
        TextView tvCreateTime;
        TextView tvOrderCode;
        TextView tvLoanMoney;
        TextView tvDate;
        TextView tvState;
    }
}

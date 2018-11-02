package com.flash.rupiah.pro.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flash.rupiah.pro.R;
import com.flash.rupiah.pro.bean.PayLoanListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heise on 2018/6/11.
 */

public class PayListAdapter extends BaseAdapter {
    Context context;
    List<PayLoanListBean> payLoanListBean;
    ViewHolder viewHolder = null;
    Map<Integer, Integer> maps = new HashMap<>();

    public PayListAdapter(Context context, List<PayLoanListBean> payLoanListBean) {
        this.context = context;
        this.payLoanListBean = payLoanListBean;
        for (int i = 0; i < payLoanListBean.size(); i++) {
            maps.put(i, 0);
        }
    }

    @Override
    public int getCount() {
        return payLoanListBean == null ? 0 : payLoanListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return payLoanListBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_pay_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_pay_item_order);
            viewHolder.ivDown = convertView.findViewById(R.id.iv_down);
            viewHolder.linearLayout = convertView.findViewById(R.id.ll_pay_gone);
            viewHolder.tvValue1 = convertView.findViewById(R.id.tvValue1);
            viewHolder.tvValue2 = convertView.findViewById(R.id.tvValue2);
            viewHolder.tvValue3 = convertView.findViewById(R.id.tvValue3);
            viewHolder.tvValue4 = convertView.findViewById(R.id.tvValue4);
            viewHolder.tvValue5 = convertView.findViewById(R.id.tvValue5);
            viewHolder.tvValue6 = convertView.findViewById(R.id.tvValue6);
            viewHolder.tvValue7 = convertView.findViewById(R.id.tvValue7);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PayLoanListBean payBean = this.payLoanListBean.get(position);
        if (payBean == null) {
            return null;
        }
        if (maps.get(position) == 0) {
            viewHolder.linearLayout.setVisibility(View.GONE);
            viewHolder.ivDown.setImageResource(R.mipmap.check_down);
        } else {
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
            viewHolder.ivDown.setImageResource(R.mipmap.check_up);
        }
        if ("TELAH_DIBUAT".equals(payBean.getStatus()) || "SUKSES".equals(payBean.getStatus())) {
            viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            viewHolder.tvValue1.setTextColor(context.getResources().getColor(R.color.light_grey));
        }
        viewHolder.tvTitle.setText(payBean.getId() + "");
        viewHolder.tvValue1.setText(payBean.getStatus() == null ? "" : payBean.getStatus());
        viewHolder.tvValue2.setText("Rp." + payBean.getDepositAmount());
        viewHolder.tvValue3.setText(payBean.getCurrency() == null ? "" : payBean.getCurrency());
        viewHolder.tvValue4.setText(payBean.getPayType() == null ? "" : payBean.getPayType());
        viewHolder.tvValue5.setText(payBean.getMsisdn() == null ? "" : payBean.getMsisdn());
        viewHolder.tvValue6.setText(payBean.getBankType() == null ? "" : payBean.getBankType());
        viewHolder.tvValue7.setText(payBean.getPaymentCode() == null ? "" : payBean
                .getPaymentCode());
        viewHolder.ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maps.get(position) == 0) {
                    maps.put(position, 1);
                } else {
                    maps.put(position, 0);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        ImageView ivDown;
        LinearLayout linearLayout;
        TextView tvValue1;
        TextView tvValue2;
        TextView tvValue3;
        TextView tvValue4;
        TextView tvValue5;
        TextView tvValue6;
        TextView tvValue7;
    }
}

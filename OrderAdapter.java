package com.food.foodonroad;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderAdapter  extends BaseAdapter {
    private Activity activity;
    private List<orderclass> OrderList;
    private LayoutInflater inflater;

    public OrderAdapter(Activity activity, List<orderclass> Items) {
        this.activity = activity;
        this.OrderList = Items;

    }
    @Override
    public int getCount() {
        return OrderList.size();
    }

    @Override
    public Object getItem(int position) {
        return OrderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.orderitem, null);

        TextView nfood = (TextView) convertView.findViewById(R.id.txtnfood1);
        TextView time1 = (TextView) convertView.findViewById(R.id.txttime1);
        TextView price1 = (TextView) convertView.findViewById(R.id.txtprice1);
        TextView qyt1 = (TextView) convertView.findViewById(R.id.txtqyt);

        orderclass m1= OrderList.get(position);
        nfood.setText(m1.Getnfood());
        qyt1.setText(Integer.toString(m1.Getqyt()));
        time1.setText(m1.Getmaxtime()+" M");
        price1.setText(Double.toString(m1.Getprice()) + " RS");

        return convertView;
    }

}
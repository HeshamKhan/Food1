package com.food.foodonroad;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodAdapter  extends BaseAdapter {
    private Activity activity;
    private List<foodclass> FoodList;
    private LayoutInflater inflater;
    public ImageLoader imageLoader;
    public FoodAdapter(Activity activity, List<foodclass> Items) {
        this.activity = activity;
        this.FoodList = Items;
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    @Override
    public int getCount() {
        return FoodList.size();
    }

    @Override
    public Object getItem(int position) {
        return FoodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint("InflateParams") @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.itemrow, null);

        TextView nfood = (TextView) convertView.findViewById(R.id.txttitle);
        TextView description = (TextView) convertView.findViewById(R.id.txtdesc);
        TextView maxtime = (TextView) convertView.findViewById(R.id.txttime);
        TextView price = (TextView) convertView.findViewById(R.id.txtprice);
        ImageView image=(ImageView)convertView.findViewById(R.id.imageView1);
        // getting movie data for the row
        foodclass m1= FoodList.get(position);
        nfood.setText(m1.Getnfood());
        description.setText(m1.Getdescription());
        maxtime.setText(m1.Getmaxtime()+" M");
        price.setText(Double.toString(m1.Getprice())+" RS");
        imageLoader.DisplayImage(publicdata.server+"/FOR/image/"+m1.Getimage(),image);
        return convertView;
    }

}


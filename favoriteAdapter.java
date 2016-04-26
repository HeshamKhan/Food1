package com.food.foodonroad;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class favoriteAdapter extends BaseAdapter {
    private Activity activity;
    private List<favoriteclass> favoriteList;
    private LayoutInflater inflater;

    public favoriteAdapter(Activity activity, List<favoriteclass> Items) {
        this.activity = activity;
        this.favoriteList = Items;

    }
    @Override
    public int getCount() {
        return favoriteList.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteList.get(position);
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
            convertView = inflater.inflate(R.layout.favrow, null);

        TextView nrest = (TextView) convertView.findViewById(R.id.txtnrest);
        TextView adress = (TextView) convertView.findViewById(R.id.txtaddress);
        favoriteclass m1= favoriteList.get(position);
        nrest.setText(m1.Getnamerest());
        adress.setText(m1.Getaddress());

        return convertView;
    }

}

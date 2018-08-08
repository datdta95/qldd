package com.dvt.tiendat.quanlydinhduong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TienDat on 4/22/2018.
 */

public class ListKienThucAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ListKienThuc> listKienThucs;

    public ListKienThucAdapter(Context context, int layout, List<ListKienThuc> listKienThucs) {
        this.context = context;
        this.layout = layout;
        this.listKienThucs = listKienThucs;
    }

    @Override

    public int getCount() {
        return listKienThucs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView tvTittle;
    }

    public void setListKienThucs(List<ListKienThuc> list) {
        this.listKienThucs = list;
    }

    public List<ListKienThuc> getListKienThucs() {
        return this.listKienThucs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.tvTittle = (TextView) convertView.findViewById(R.id.tvTittle);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        ListKienThuc listKienThuc = listKienThucs.get(position);
        viewHolder.tvTittle.setText(listKienThuc.getTittle());

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.slide_down);
        convertView.startAnimation(animation);

        return convertView;
    }
}

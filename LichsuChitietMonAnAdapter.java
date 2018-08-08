package com.dvt.tiendat.quanlydinhduong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dthtg on 12/14/2017.
 */

public class LichsuChitietMonAnAdapter extends BaseAdapter {
    private LichsuChitietActivity context;
    private int layout;
    private List<LichsuChitietMonAn> lichsuChitietMonAnList;

    public LichsuChitietMonAnAdapter(LichsuChitietActivity context, int layout, List<LichsuChitietMonAn> lichsuChitietMonAnList) {
        this.context = context;
        this.layout = layout;
        this.lichsuChitietMonAnList = lichsuChitietMonAnList;
    }

    @Override
    public int getCount() {
        return lichsuChitietMonAnList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtvTenMA_LS, txtvTrongLuong_LS, txtvNLHT;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.txtvTenMA_LS = (TextView) convertView.findViewById(R.id.tvTenMonAn_LS);
            viewHolder.txtvTrongLuong_LS = (TextView) convertView.findViewById(R.id.tvTrongLuong);
            viewHolder.txtvNLHT = (TextView) convertView.findViewById(R.id.txtvNLHT);

            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        final LichsuChitietMonAn chitietMonAn = lichsuChitietMonAnList.get(position);
        viewHolder.txtvTenMA_LS.setText(chitietMonAn.getTenMonAn());
        viewHolder.txtvTrongLuong_LS.setText(chitietMonAn.getTrongLuong() + "g");
        viewHolder.txtvNLHT.setText(chitietMonAn.getNLHT() + "Kcal");

        //gán animation
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.sequential_animation);
        convertView.startAnimation(animation);

        return convertView;
    }

}

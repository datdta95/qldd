package com.dvt.tiendat.quanlydinhduong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by dthtg on 12/15/2017.
 */

public class LichsuChitietMonTheThaoAdapter extends BaseAdapter {
    private LichsuChitietActivity context;
    private int layout;
    private List<LichsuChitietMonTheThao> lichsuChitietMonTheThaoList;

    public LichsuChitietMonTheThaoAdapter(LichsuChitietActivity context, int layout, List<LichsuChitietMonTheThao> lichsuChitietMonTheThaoList) {
        this.context = context;
        this.layout = layout;
        this.lichsuChitietMonTheThaoList = lichsuChitietMonTheThaoList;
    }

    @Override

    public int getCount() {
        return lichsuChitietMonTheThaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView txtvTenMonTT_LS,txtvThoiGian_LS,txtvNLTH;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.txtvTenMonTT_LS=(TextView) convertView.findViewById(R.id.tvTenMonTT_LS);
            viewHolder.txtvThoiGian_LS=(TextView) convertView.findViewById(R.id.tvThoiGian_LS);
            viewHolder.txtvNLTH=(TextView) convertView.findViewById(R.id.tvNLTH);
            convertView.setTag(viewHolder);
        }
        else viewHolder=(ViewHolder) convertView.getTag();

        final LichsuChitietMonTheThao lichsuChitietMonTheThao = lichsuChitietMonTheThaoList.get(position);
        viewHolder.txtvTenMonTT_LS.setText(lichsuChitietMonTheThao.getTenMonTT());
        viewHolder.txtvThoiGian_LS.setText(lichsuChitietMonTheThao.getThoiGian() +"h");
        viewHolder.txtvNLTH.setText("- "+ lichsuChitietMonTheThao.getNLTH()+"kcal");

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_list);
        convertView.startAnimation(animation);


        return convertView;
    }

}

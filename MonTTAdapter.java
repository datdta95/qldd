package com.dvt.tiendat.quanlydinhduong;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dthtg on 11/23/2017.
 */

public class MonTTAdapter extends BaseAdapter implements Filterable {
    Context context;
    int layout;
    CustomFiler customFiler;
    List<MonTheThao> monTheThaoList;
    List<MonTheThao> filteredData;

    public MonTTAdapter(Context context, int layout, List<MonTheThao> monTheThaoList) {
        this.context = context;
        this.layout = layout;
        this.monTheThaoList = monTheThaoList;
        this.filteredData=monTheThaoList;
    }

    @Override
    public int getCount() {
        return monTheThaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Filter getFilter() {
        if (customFiler==null){
            customFiler=new CustomFiler();
        }
        return customFiler;
    }

    private class ViewHolder
    {
        ImageView imgMonTT;
        TextView txtvTenMonTT,txtvMoTaMTT,tvNLMonTT;
    }
    public void setMonTheThaoList(List<MonTheThao> list){
        this.monTheThaoList = list;
    }
    public List<MonTheThao> getMonTheThaoList(){
       return this.monTheThaoList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.imgMonTT=(ImageView) convertView.findViewById(R.id.imgMonTT);
            viewHolder.txtvTenMonTT=(TextView) convertView.findViewById(R.id.txtvTenMonTT);
            viewHolder.tvNLMonTT=(TextView) convertView.findViewById(R.id.tvNLMonTT);


            convertView.setTag(viewHolder);
        }
        else viewHolder=(ViewHolder) convertView.getTag();

        MonTheThao monTheThao=monTheThaoList.get(position);
        viewHolder.txtvTenMonTT.setText(monTheThao.getTenMonTT());
        viewHolder.tvNLMonTT.setText(monTheThao.getNangLuong()+" Kcal/h");

        Glide.with(context)
                .load(monTheThao.getHinhAnh())
                .into(viewHolder.imgMonTT);

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.slide_down);
        convertView.startAnimation(animation);

        return convertView;
    }

    class CustomFiler extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<MonTheThao> filters = new ArrayList<MonTheThao>();
                for (int i = 0; i < filteredData.size(); i++) {
                    if (filteredData.get(i).getTenMonTT().toUpperCase().contains(constraint)) {
                        MonTheThao monTheThao = new MonTheThao(filteredData.get(i).getId(), filteredData.get(i).getTenMonTT(),filteredData.get(i).getMoTa(),filteredData.get(i).getHinhAnh(),filteredData.get(i).getNangLuong());
                        filters.add(monTheThao);
                        Log.d("id333", String.valueOf(filteredData.get(i).getId()));
                        Log.d("id334", String.valueOf(filteredData.size()));

                    }
                }
                filterResults.count = filters.size();
                filterResults.values = filters;
            } else {
                filterResults.count = filteredData.size();
                filterResults.values = filteredData;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            monTheThaoList=(List<MonTheThao>) results.values;
            setMonTheThaoList(monTheThaoList);
            notifyDataSetChanged();
        }
    }
}

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
 * Created by dthtg on 11/20/2017.
 */

public class MonAnAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<MonAn> monAnList;
    private List<MonAn> filteredData;
    private CustomFiler customFiler;

    public MonAnAdapter(Context context, int layout, List<MonAn> monAnList) {
        this.context = context;
        this.layout = layout;
        this.monAnList = monAnList;
        this.filteredData=monAnList;
    }

    @Override

    public int getCount() {
        return monAnList.size();
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

    private class ViewHolder {
        ImageView imgMonAn;
        TextView txtvTenMA, txtvMoTa,tvNLMonAn;
    }

    public void setMonAnList(List<MonAn> list){
        this.monAnList = list;
    }
    public List<MonAn> getMonAnList(){
        return this.monAnList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.imgMonAn = (ImageView) convertView.findViewById(R.id.imgMonAn);
            viewHolder.txtvTenMA = (TextView) convertView.findViewById(R.id.txtvTenMA);
//            viewHolder.txtvMoTa = (TextView) convertView.findViewById(R.id.txtvMoTa);
            viewHolder.tvNLMonAn=(TextView) convertView.findViewById(R.id.tvNLMonAn);

            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        MonAn monAn = monAnList.get(position);
        viewHolder.txtvTenMA.setText(monAn.getTenMA());
//        viewHolder.txtvMoTa.setText(monAn.getMoTa());
        viewHolder.tvNLMonAn.setText(monAn.getNangLuong()+" Kcal/100g");
        Glide.with(context)
                .load(monAn.getHinhAnh())
                .into(viewHolder.imgMonAn);

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
                ArrayList<MonAn> filters = new ArrayList<MonAn>();
                for (int i = 0; i < filteredData.size(); i++) {
                    if (filteredData.get(i).getTenMA().toUpperCase().contains(constraint)) {
                        MonAn monAn = new MonAn(filteredData.get(i).getId(), filteredData.get(i).getTenMA(),filteredData.get(i).getMoTa(),filteredData.get(i).getHinhAnh(),filteredData.get(i).getNangLuong(),filteredData.get(i).getIDNhomMA());
                        filters.add(monAn);
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
            monAnList=(List<MonAn>) results.values;
            setMonAnList(monAnList);
            notifyDataSetChanged();
        }
    }
}

package com.dvt.tiendat.quanlydinhduong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dthtg on 11/20/2017.
 */

public class NhomMonAnAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    CustomFiler customFiler;
    private List<NhomMonAn> nhomMonAnList;
    private List<NhomMonAn> filteredData;


    public NhomMonAnAdapter(Context context, int layout, List<NhomMonAn> nhomMonAnList) {
        this.context = context;
        this.layout = layout;
        this.nhomMonAnList = nhomMonAnList;
        this.filteredData = nhomMonAnList;
    }

    @Override

    public int getCount() {
        return nhomMonAnList.size();
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
            customFiler =new CustomFiler();
        }
        return customFiler;
    }

    public void setNhomMonAnList(List<NhomMonAn> list){
        this.nhomMonAnList = list;
    }
    public List<NhomMonAn> getNhomMonAnList(){
        return this.nhomMonAnList;
    }
    private class ViewHolder {
        TextView txtvTenNhomMA;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.txtvTenNhomMA = (TextView) convertView.findViewById(R.id.txtvTenNhomMA);

            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        NhomMonAn nhomMonAn = nhomMonAnList.get(position);
        viewHolder.txtvTenNhomMA.setText(nhomMonAn.getTenNhomMA());

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
                ArrayList<NhomMonAn> filters = new ArrayList<NhomMonAn>();
                for (int i = 0; i < filteredData.size(); i++) {
                    if (filteredData.get(i).getTenNhomMA().toUpperCase().contains(constraint)) {
                        NhomMonAn nhomMonAn = new NhomMonAn(filteredData.get(i).getId(i), filteredData.get(i).getTenNhomMA());
                        filters.add(nhomMonAn);
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
            nhomMonAnList=(List<NhomMonAn>) results.values;
            notifyDataSetChanged();
        }
    }

}
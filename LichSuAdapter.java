package com.dvt.tiendat.quanlydinhduong;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by TienDat on 4/22/2018.
 */

public class LichSuAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<LichSu> lichSuList;

    public LichSuAdapter(Context context, int layout, List<LichSu> lichSuList) {
        this.context = context;
        this.layout = layout;
        this.lichSuList = lichSuList;
    }

    @Override

    public int getCount() {
        return lichSuList.size();
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
        TextView tvNgay;
        ImageButton imgBtn;
    }

    public void setLichSuList(List<LichSu> lichSuList) {
        this.lichSuList = lichSuList;
    }

    public List<LichSu> getLichSuList() {
        return this.lichSuList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.tvNgay = (TextView) convertView.findViewById(R.id.tvNgay);
//            viewHolder.imgBtn = (ImageButton) convertView.findViewById(R.id.imageButton);
//            viewHolder.tvNLHT = (TextView) convertView.findViewById(R.id.tvNLHT);
//            viewHolder.tvNLTH = (TextView) convertView.findViewById(R.id.tvNLTH);
//            viewHolder.tvSum = (TextView) convertView.findViewById(R.id.tvSum);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        LichSu lichSu = lichSuList.get(position);
        String date = lichSu.getNgay();
//        viewHolder.imgBtn.setImageResource(R.drawable.collapse);
        String thu, thang;


        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        Date d = new Date();
        try {
            d = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(d);
            Calendar c = Calendar.getInstance();
            c.setTime(d); // yourdate is an object of type Date

            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            int monthOfYear = c.get(Calendar.MONTH);
            thang = String.valueOf(monthOfYear + 1);
            Log.d("dayo", String.valueOf(dayOfWeek));

            switch (dayOfWeek) {
                case 2:
                    thu = "Thứ Hai";
                    break;
                case 3:
                    thu = "Thứ Ba";
                    break;
                case 4:
                    thu = "Thứ Tư";
                    break;
                case 5:
                    thu = "Thứ Năm";
                    break;
                case 6:
                    thu = "Thứ Sáu";
                    break;
                case 7:
                    thu = "Thứ Bảy";
                    break;
                case 1:
                    thu = "Chủ nhật";
                    break;
                default:
                    thu = "Lỗi";
            }

            viewHolder.tvNgay.setText(thu + ", Ngày " + c.get(Calendar.DAY_OF_MONTH) + " Tháng " + thang + " Năm " + c.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        viewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (viewHolder.tvNLHT.getVisibility() == v.GONE && viewHolder.tvNLTH.getVisibility() == v.GONE && viewHolder.tvSum.getVisibility() == v.GONE) {
//                    viewHolder.tvNLHT.setVisibility(v.VISIBLE);
//                    viewHolder.tvNLTH.setVisibility(v.VISIBLE);
//                    viewHolder.tvSum.setVisibility(v.VISIBLE);
//                } else {
//                    viewHolder.tvNLHT.setVisibility(v.GONE);
//                    viewHolder.tvNLTH.setVisibility(v.GONE);
//                    viewHolder.tvSum.setVisibility(v.GONE);
//                }
//            }
//        });

//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
//        convertView.startAnimation(animation);


//        viewHolder.tvNgay.setText(lichSu.getNgay());
        return convertView;
    }


}

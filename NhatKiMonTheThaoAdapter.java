package com.dvt.tiendat.quanlydinhduong;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by dthtg on 12/15/2017.
 */

public class NhatKiMonTheThaoAdapter extends BaseAdapter {
    private NhatKiActivity context;
    private int layout;
    private List<NhatKiMonTheThao> nhatKiMonTheThaoList;

    public NhatKiMonTheThaoAdapter(NhatKiActivity context, int layout, List<NhatKiMonTheThao> nhatKiMonTheThaoList) {
        this.context = context;
        this.layout = layout;
        this.nhatKiMonTheThaoList = nhatKiMonTheThaoList;
    }

    @Override

    public int getCount() {
        return nhatKiMonTheThaoList.size();
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
        TextView txtvTenMonTT_NK,txtvThoiGian_NK,txtvNLTH;
        ImageButton btnUpdateHDTT,btnDeleteHDTT;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.txtvTenMonTT_NK=(TextView) convertView.findViewById(R.id.txtvTenMonTT_NK);
            viewHolder.txtvThoiGian_NK=(TextView) convertView.findViewById(R.id.txtvThoiGian_NK);
            viewHolder.txtvNLTH=(TextView) convertView.findViewById(R.id.txtvNLTH);
            viewHolder.btnUpdateHDTT=(ImageButton)convertView.findViewById(R.id.btnUpdateHDTT);
            viewHolder.btnDeleteHDTT=(ImageButton)convertView.findViewById(R.id.btnDeleteHDTT);
            convertView.setTag(viewHolder);
        }
        else viewHolder=(ViewHolder) convertView.getTag();

        final NhatKiMonTheThao nhatKiMonTheThao = nhatKiMonTheThaoList.get(position);
        viewHolder.txtvTenMonTT_NK.setText(nhatKiMonTheThao.getTenMonTT());
        viewHolder.txtvThoiGian_NK.setText(nhatKiMonTheThao.getThoiGian() +"h");
        viewHolder.txtvNLTH.setText("- "+ nhatKiMonTheThao.getNLTH()+"kcal");
        Glide.with(context)
                .load("https://cdn1.iconfinder.com/data/icons/flat-web-browser/100/edit-button-512.png")
                .into(viewHolder.btnUpdateHDTT);
        Glide.with(context)
                .load("https://image.flaticon.com/icons/png/128/61/61391.png")
                .into(viewHolder.btnDeleteHDTT);
        viewHolder.btnDeleteHDTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(nhatKiMonTheThao.getTenMonTT(), nhatKiMonTheThao.getIdhd());
            }
        });
        viewHolder.btnUpdateHDTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UpdateNKMTTActivity.class);
                intent.putExtra("nhatKiMonTheThao", nhatKiMonTheThao);
                context.startActivity(intent);
            }
        });

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.slide_down);
        convertView.startAnimation(animation);

        return convertView;
    }
    private  void XacNhanXoa(String ten, final int id){
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa hoạt động "+ten+" không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteHDTT(id);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
}

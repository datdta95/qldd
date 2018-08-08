package com.dvt.tiendat.quanlydinhduong;

import android.app.AlertDialog;
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

import java.util.List;

/**
 * Created by dthtg on 12/14/2017.
 */

public class NhatKiMonAnAdapter extends BaseAdapter {
    private NhatKiActivity context;
    private int layout;
    private List<NhatKiMonAn> nhatKiMonAnList;

    public NhatKiMonAnAdapter(NhatKiActivity context, int layout, List<NhatKiMonAn> nhatKiMonAnList) {
        this.context = context;
        this.layout = layout;
        this.nhatKiMonAnList = nhatKiMonAnList;
    }

    @Override
    public int getCount() {
        return nhatKiMonAnList.size();
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
        TextView txtvTenMA_NK, txtvTrongLuong_NK, txtvNLHT;
        ImageButton btnUpdateHDAU, btnDeleteHDAU;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.txtvTenMA_NK = (TextView) convertView.findViewById(R.id.txtvTenMA_NK);
            viewHolder.txtvTrongLuong_NK = (TextView) convertView.findViewById(R.id.txtvTrongLuong_NK);
            viewHolder.txtvNLHT = (TextView) convertView.findViewById(R.id.txtvNLHT);
            viewHolder.btnUpdateHDAU = (ImageButton) convertView.findViewById(R.id.btnUpdateHDAU);
            viewHolder.btnDeleteHDAU = (ImageButton) convertView.findViewById(R.id.btnDeleteHDAU);

            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        final NhatKiMonAn nhatKiMonAn = nhatKiMonAnList.get(position);
        viewHolder.txtvTenMA_NK.setText(nhatKiMonAn.getTenMonAn());
        viewHolder.txtvTrongLuong_NK.setText(nhatKiMonAn.getTrongLuong() + "g");
        viewHolder.txtvNLHT.setText(nhatKiMonAn.getNLHT() + "Kcal");
        viewHolder.btnUpdateHDAU.setImageResource(R.drawable.edit);
        viewHolder.btnDeleteHDAU.setImageLevel(R.drawable.delete);
        viewHolder.btnUpdateHDAU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, nhatKiMonAn.getTenMonAn(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdateNKMAActivity.class);
                intent.putExtra("nhatKiMonAn", nhatKiMonAn);
                context.startActivity(intent);
            }
        });
        viewHolder.btnDeleteHDAU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(nhatKiMonAn.getTenMonAn(), nhatKiMonAn.getIdhd());
            }
        });

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.slide_down);
        convertView.startAnimation(animation);

        return convertView;
    }

    private void XacNhanXoa(String ten, final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa hoạt động ăn " + ten + " không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteHDAU(id);
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

package com.dvt.tiendat.quanlydinhduong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;

public class DanhMuc extends AppCompatActivity {
    ImageButton ibtTT,ibtMA,ibtLS,ibtNK,ibtTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        AnhXa();
        Glide.with(DanhMuc.this)
                .load("http://www.garmin.co.in/minisite/vivo/vivoactive/images/vivoactive-features-icon-large-sport.png")
                .into(ibtTT);
        Glide.with(DanhMuc.this)
                .load("http://www.green4solutions.com/media/img/icon-food-and-beverage.png")
                .override(310,310)
                .into(ibtMA);
        Glide.with(DanhMuc.this)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREv_yUTRF7RaF0vA_c4WZfz7W1Vz-u1yegLYx9GOvjHPPqdvhc")
                .into(ibtNK);
        Glide.with(DanhMuc.this)
                .load("http://www.pvhc.net/img40/ebxrqqkbbfzjyraclmzt.png")
                .override(410,410)
                .into(ibtLS);
        Glide.with(DanhMuc.this)
                .load("https://lh3.googleusercontent.com/gQOeBKQxsJVWR5aAPqFv2K-VtMugayNXbOcnojFwEMNyb8r4tliwa69tqb6qOo36K-k=w300")
                .into(ibtTS);
        ibtMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DanhMuc.this,MonAnActivity.class));
            }
        });
        ibtTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DanhMuc.this,MonTTActivity.class));
            }
        });
        ibtTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DanhMuc.this,ThongSoCoTheActivity.class));
            }
        });
    }

    private  void AnhXa()
    {
        ibtTT=(ImageButton) findViewById(R.id.ibtTT);
        ibtMA=(ImageButton) findViewById(R.id.ibtMA);
        ibtTS=(ImageButton) findViewById(R.id.ibtTS);
        ibtNK=(ImageButton) findViewById(R.id.ibtNK);
        ibtLS=(ImageButton) findViewById(R.id.ibtLS);
    }
}

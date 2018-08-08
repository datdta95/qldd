package com.dvt.tiendat.quanlydinhduong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MonTTChiTietActivity extends AppCompatActivity {
    String url = "http://192.168.56.1:8080/QLDD/themhdtt.php";

    TextInputEditText tInputEdit_ThoiGian;
    TextInputLayout tInputLayout_ThoiGian;
    Button btnTinhNLTH, btnXNMTT;
    ImageView imgMonTT_CT;
    ScrollView scrollView;
    TextView txtvTenMonTT_CT, txtvMoTaMTT_CT, txtvNangLuongMTT;
    Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_tt);
        final MonTheThao monTheThao = (MonTheThao) getIntent().getSerializableExtra("monthethao");
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", 0);
        Log.d("idUSer", String.valueOf(idUser));
        AnhXa();

        txtvTenMonTT_CT.setText(monTheThao.getTenMonTT());
        txtvMoTaMTT_CT.setText(monTheThao.getMoTa());
        txtvNangLuongMTT.setText("Năng Lượng: " + monTheThao.getNangLuong() + " Kcal");

        tInputEdit_ThoiGian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tInputLayout_ThoiGian.setError("Mời bạn nhập trọng lượng");
                } else tInputLayout_ThoiGian.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Glide.with(MonTTChiTietActivity.this)
                .load(monTheThao.getHinhAnh())
                .into(imgMonTT_CT);
        btnTinhNLTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Float.parseFloat(tInputEdit_ThoiGian.getText().toString())== 0)
                    Toast.makeText(MonTTChiTietActivity.this, "Vui lòng nhập thời gian tập luyện.", Toast.LENGTH_SHORT).show();
                else {
                    float NL = monTheThao.getNangLuong();
                    float TG = Float.parseFloat(tInputEdit_ThoiGian.getText().toString());
                    float TNL = NL * TG;
                    Toast.makeText(MonTTChiTietActivity.this, "Năng Lượng Tiêu Hao : " + TNL + " Kcal", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnXNMTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Float.parseFloat(tInputEdit_ThoiGian.getText().toString())> 0){
                Themhdtt(url, monTheThao,idUser);}
                else {
                    Toast.makeText(MonTTChiTietActivity.this, "Vui lòng nhập thời gian tập luyện", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Themhdtt(String url, final MonTheThao monTheThao, final int idUser) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int status = 0;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getInt("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (status == 1) {
                            Toast.makeText(MonTTChiTietActivity.this, "Đã thêm hoạt động thể thao", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MonTTChiTietActivity.this, MonTTActivity.class));
                            onBackPressed();
                        } else
                            Toast.makeText(MonTTChiTietActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MonTTChiTietActivity.this, "AAA", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idMTT", monTheThao.getId() + "");
                params.put("idUser", String.valueOf(idUser));
                params.put("ThoiGian", tInputEdit_ThoiGian.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menuNhomMonAn :
//                startActivity(new Intent(this,NhomMonAnActivity.class));
//                break;
//            case R.id.menuTheThao :
//                startActivity(new Intent(this,MonTTActivity.class));
//                break;
            case R.id.menuNhatKi:
                startActivity(new Intent(this, NhatKiActivity.class));
                break;
            case R.id.menuLichSu:
                startActivity(new Intent(this, LichSuActivity.class));
                break;
            case R.id.menuThongSoCT:
                startActivity(new Intent(this, ThongSoCoTheActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void AnhXa() {
        tInputEdit_ThoiGian = (TextInputEditText) findViewById(R.id.tInputEdit_ThoiGian);
        tInputLayout_ThoiGian = (TextInputLayout) findViewById(R.id.tInputLayout_ThoiGian);
        btnTinhNLTH = (Button) findViewById(R.id.btnTinhNLTH);
        imgMonTT_CT = (ImageView) findViewById(R.id.imgMonTT_CT);
        txtvTenMonTT_CT = (TextView) findViewById(R.id.txtvTenMonTT_CT);
        txtvMoTaMTT_CT = (TextView) findViewById(R.id.txtvMoTaMTT_CT);
        txtvNangLuongMTT = (TextView) findViewById(R.id.txtvNangLuongMTT);
        btnXNMTT = (Button) findViewById(R.id.btnXNMTT);
        scrollView=(ScrollView) findViewById(R.id.scrollViewMTT);
    }
}

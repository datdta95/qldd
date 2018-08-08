package com.dvt.tiendat.quanlydinhduong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

public class MonAnChiTietActivity extends AppCompatActivity {
    String url = "http://192.168.56.1:8080/QLDD/themhdau.php";
    TextInputEditText tInputEdit_TrongLuong;
    TextInputLayout tInputLayout_TrongLuong;
    Button btnTinhNL, btnXNMA;
    ImageView imgMA;
    TextView txtvTenMA_CT, txtvMoTa_CT, txtvNangLuong;
    ScrollView scrollView;
    Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        final MonAn monAn = (MonAn) getIntent().getSerializableExtra("monan");
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", 0);
        AnhXa();
        txtvTenMA_CT.setText(monAn.getTenMA());
        txtvMoTa_CT.setText(monAn.getMoTa());
        txtvNangLuong.setText("Năng Lượng: " + monAn.getNangLuong() + " Kcal");

        tInputEdit_TrongLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tInputLayout_TrongLuong.setError("Mời bạn nhập trọng lượng");
                } else tInputLayout_TrongLuong.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Glide.with(MonAnChiTietActivity.this)
                .load(monAn.getHinhAnh())
                .into(imgMA);
        btnTinhNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Float.parseFloat(tInputEdit_TrongLuong.getText().toString()) == 0)
                    Toast.makeText(MonAnChiTietActivity.this, "Vui lòng nhập trọng lượng món ăn", Toast.LENGTH_SHORT).show();
                else {
                    float NL = monAn.getNangLuong();
                    float TL = Float.parseFloat(tInputEdit_TrongLuong.getText().toString());
                    float TNL = NL * TL / 100;
                    Toast.makeText(MonAnChiTietActivity.this, "Năng Lượng Hấp Thu : " + TNL + " Kcal", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnXNMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Float.parseFloat(tInputEdit_TrongLuong.getText().toString()) > 0) {
                    Themhdau(url, monAn, idUser);
                } else {
                    Toast.makeText(MonAnChiTietActivity.this, "Vui lòng nhập trọng lượng món ăn", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Themhdau(String url, final MonAn monAn, final int idUser) {
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
                            Toast.makeText(MonAnChiTietActivity.this, "Đã thêm hoạt động ăn uống", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MonAnChiTietActivity.this,MonAnActivity.class));
                            onBackPressed();
                        } else
                            Toast.makeText(MonAnChiTietActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idMA", monAn.getId() + "");
                params.put("idUser", String.valueOf(idUser));
                params.put("TrongLuong", tInputEdit_TrongLuong.getText().toString().trim());
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
        tInputEdit_TrongLuong = (TextInputEditText) findViewById(R.id.tInputEdit_TrongLuong);
        tInputLayout_TrongLuong = (TextInputLayout) findViewById(R.id.tInputLayout_TrongLuong);
        btnTinhNL = (Button) findViewById(R.id.btnTinhNL);
        imgMA = (ImageView) findViewById(R.id.imgMA);
        txtvTenMA_CT = (TextView) findViewById(R.id.txtvTenMA_CT);
        txtvMoTa_CT = (TextView) findViewById(R.id.txtvMoTa_CT);
        txtvNangLuong = (TextView) findViewById(R.id.txtvNangLuong);
        btnXNMA = (Button) findViewById(R.id.btnXNMA);
        scrollView = (ScrollView) findViewById(R.id.scrollViewMA);
    }
}

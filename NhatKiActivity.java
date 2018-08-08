package com.dvt.tiendat.quanlydinhduong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NhatKiActivity extends AppCompatActivity {
    ListView lvhdau, lvhdtt;
    TextView txtvCLNL, tViewBMR;
    Button btnAddMonAn, btnAddMonTheThao;
    TextClock textClock;
    NhatKiMonTheThao nhatKiMonTheThao;
    NhatKiMonAn nhatKiMonAn;
    public float TongNLHT = 0, TongNLTH = 0;
    Integer idUser;
    String date;


    String urlGethdau = "http://192.168.56.1:8080/QLDD/GetHDAU.php",
            urlGethdtt = "http://192.168.56.1:8080/QLDD/GetHDTT.php",
            urlDeleteHDTT = "http://192.168.56.1:8080/QLDD/DeleteHDTT.php",
            urlDeleteHDAU = "http://192.168.56.1:8080/QLDD/DeleteHDAU.php",
            urlInsertLichSu = "http://192.168.56.1:8080/QLDD/GetLichSu.php";
    ArrayList<NhatKiMonAn> nhatKiMonAnArrayList;
    NhatKiMonAnAdapter adapter;
    NhatKiMonTheThaoAdapter adapter2;
    ArrayList<NhatKiMonTheThao> nhatKiMonTheThaoArrayList;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nk);
        AnhXa();

        String formatdate = "d-M-yyyy";
        textClock.setFormat12Hour(formatdate);
        textClock.setFormat24Hour(formatdate);
        date = (String) textClock.getText();

        SharedPreferences sharedPreferences_1 = getSharedPreferences("BMR_data", MODE_PRIVATE);
        float BMR = sharedPreferences_1.getFloat("BMR", 0.0f);
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", 0);
        Log.d("idUSer2", String.valueOf(idUser));


//        Intent intent = getIntent();
//        float BMR = 0;
//        BMR = intent.getFloatExtra("BMR", BMR);
        tViewBMR.setText("Chỉ số BMR: " + BMR + " Kcal");
        nhatKiMonAnArrayList = new ArrayList<>();
        adapter = new NhatKiMonAnAdapter(NhatKiActivity.this, R.layout.dong_nkma, nhatKiMonAnArrayList);
        lvhdau.setAdapter(adapter);
        GetHDAU(idUser);

        nhatKiMonTheThaoArrayList = new ArrayList<>();
        adapter2 = new NhatKiMonTheThaoAdapter(NhatKiActivity.this, R.layout.dong_nkmtt, nhatKiMonTheThaoArrayList);
        lvhdtt.setAdapter(adapter2);
        //GetHDTT(urlGethdtt);

        btnAddMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhatKiActivity.this, NhomMonAnActivity.class);
                startActivity(intent);
            }
        });

        btnAddMonTheThao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhatKiActivity.this, MonTTActivity.class);
                startActivity(intent);
            }
        });
// uk
        Log.d("aaa", String.valueOf(TongNLHT));


        lvhdau.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menuNhomMonAn:
//                startActivity(new Intent(this, NhomMonAnActivity.class));
//                break;
//            case R.id.menuTheThao:
//                startActivity(new Intent(this, MonTTActivity.class));
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


    private void GetHDAU(final int idUser) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGethdau,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        nhatKiMonAnArrayList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    nhatKiMonAnArrayList.add(new NhatKiMonAn(
                                            object.getInt("idhd"),
                                            object.getInt("idMA"),
                                            object.getString("TenMonAn"),
                                            object.getInt("TrongLuong"),
                                            object.getString("Ngay"),
                                            object.getInt("NLHT"),
                                            object.getInt("id_user")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("aaaa", String.valueOf(adapter.getCount()));
                        for (int i = 0; i < adapter.getCount(); i++) {
                            nhatKiMonAn = nhatKiMonAnArrayList.get(i);
                            TongNLHT = TongNLHT + nhatKiMonAn.getNLHT();
                        }
                        GetHDTT(idUser);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUser", String.valueOf(idUser));
                Log.d("idMA", String.valueOf(idUser));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetHDTT(final int idUser) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGethdtt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        nhatKiMonTheThaoArrayList.clear();
                        Log.d("res",response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    nhatKiMonTheThaoArrayList.add(new NhatKiMonTheThao(
                                            object.getInt("idhd"),
                                            object.getInt("idMTT"),
                                            object.getString("TenMonTT"),
                                            (float) object.getDouble("ThoiGian"),
                                            object.getString("Ngay"),
                                            object.getInt("NLTH"),
                                            object.getInt("id_user")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter2.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < adapter2.getCount(); i++) {
                            nhatKiMonTheThao = nhatKiMonTheThaoArrayList.get(i);
                            TongNLTH = TongNLTH + nhatKiMonTheThao.getNLTH();
                        }
                        SharedPreferences sharedPreferences = getSharedPreferences("BMR_data", MODE_PRIVATE);
                        float BMR = sharedPreferences.getFloat("BMR", 0.0f);
                        if (TongNLHT >= TongNLTH + BMR) {
                            txtvCLNL.setText("Bạn đang dư thừa " + (TongNLHT - (TongNLTH + BMR)) + " Kcal.");
                        } else {
                            txtvCLNL.setText("Bạn đang thiếu hụt " + (-(TongNLHT - (TongNLTH + BMR))) + " Kcal" + "\n" + "Hãy Nạp thêm năng lượng");
                        }
//                        postSumNangLuong(TongNLHT - (TongNLTH + BMR), idUser,date);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUser", String.valueOf(idUser));
                Log.d("idMA", String.valueOf(idUser));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        lvhdau = (ListView) findViewById(R.id.lvhdau);
        lvhdtt = (ListView) findViewById(R.id.lvhdtt);
        txtvCLNL = (TextView) findViewById(R.id.txtvCLNL);
        tViewBMR = (TextView) findViewById(R.id.tViewBMR);
        btnAddMonAn = (Button) findViewById(R.id.btnAddMonAn);
        btnAddMonTheThao = (Button) findViewById(R.id.btnAddMonTheThao);
        textClock = (TextClock) findViewById(R.id.textclock);

    }

    public void DeleteHDAU(final int idhd) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlDeleteHDAU,
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
                            Toast.makeText(NhatKiActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            GetHDAU(idUser);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        } else
                            Toast.makeText(NhatKiActivity.this, "Lỗi Xóa", Toast.LENGTH_SHORT).show();

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
                params.put("idhd", String.valueOf(idhd));
//                params.put("idUser", String.valueOf(idUser));
                Log.d("idhd", String.valueOf(idhd));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void DeleteHDTT(final int idhd) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, urlDeleteHDTT,
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
                            Toast.makeText(NhatKiActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            GetHDTT(idUser);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        } else
                            Toast.makeText(NhatKiActivity.this, "Lỗi Xóa", Toast.LENGTH_SHORT).show();

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
                params.put("idhd", String.valueOf(idhd));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

//    private void postSumNangLuong(final float sum, final int idUser , final String date) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertLichSu,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
////                        if (response.trim().equals("success")) {
////                            Toast.makeText(NhatKiActivity.this, "Hoạt động thể thao cập nhật thành công", Toast.LENGTH_SHORT).show();
////                        } else
////                            Toast.makeText(NhatKiActivity.this, "Hoạt động thể thao cập nhật không thành công", Toast.LENGTH_SHORT).show();
////                        int status = 0;
////                        try {
////                            JSONObject jsonObject = new JSONObject(response);
////                            status = jsonObject.getInt("status");
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
////                        if (status == 1) {
////                            Toast.makeText(UpdateNKMAActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
////                            startActivity(new Intent(UpdateNKMAActivity.this, NhatKiActivity.class));
////                        } else
////                            Toast.makeText(UpdateNKMAActivity.this, "Sửa không thành công", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("SUM", String.valueOf(sum));
//                params.put("id_user", String.valueOf(idUser));
//                params.put("date",date);
//                Log.d("date",date);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
}

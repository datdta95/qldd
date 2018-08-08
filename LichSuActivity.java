package com.dvt.tiendat.quanlydinhduong;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

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

/**
 * Created by TienDat on 4/22/2018.
 */

public class LichSuActivity extends AppCompatActivity {
    ListView listView;
    TextView tvTittleLS, tv_BMR, tv_NLHT, tv_NLTH, tv_Sum;
    ArrayList<LichSu> lichSuArrayList;
    LichSuAdapter adapter;
    Integer idUser;
    TextClock textClock;

    String date;
    String NLHT, NLTH;
    Float BMR, Sum;

    String urlInsertLichSu = "http://192.168.56.1:8080/QLDD/GetLichSu.php",
            urlGethdau = "http://192.168.56.1:8080/QLDD/GetNLHT.php",
            urlGethdtt = "http://192.168.56.1:8080/QLDD/GetNLTH.php";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsu);

        AnhXa();

        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", 0);
        SharedPreferences sharedPreferences_2 = getSharedPreferences("BMR_data", MODE_PRIVATE);
        BMR = sharedPreferences_2.getFloat("BMR", 0.0f);

        tv_BMR.setVisibility(View.GONE);
        tv_NLHT.setVisibility(View.GONE);
        tv_NLTH.setVisibility(View.GONE);
        tv_Sum.setVisibility(View.GONE);
//        tv_BMR.setVisibility(View.VISIBLE);
//        tv_NLHT.setVisibility(View.VISIBLE);
//        tv_NLTH.setVisibility(View.VISIBLE);
//        tv_Sum.setVisibility(View.VISIBLE);

        tv_BMR.setText("Năng lượng BMR: " + BMR.toString());

        String formatdate = "yyyy-M-d";
        textClock.setFormat12Hour(formatdate);
        textClock.setFormat24Hour(formatdate);


        GetLichSu(idUser);

        lichSuArrayList = new ArrayList<>();
        adapter = new LichSuAdapter(LichSuActivity.this, R.layout.dong_lichsu, lichSuArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_BMR.setVisibility(View.VISIBLE);
                tv_NLHT.setVisibility(View.VISIBLE);
                tv_NLTH.setVisibility(View.VISIBLE);
                tv_Sum.setVisibility(View.VISIBLE);
                view.setSelected(true);
//                for (int i = 0; i < listView.getChildCount(); i++) {
//                    if (position == i) {
//                        listView.getChildAt(i).setBackgroundColor(R.color.bg_login);
//                    } else {
//                        listView.getChildAt(i).setBackgroundColor(Color.WHITE);
//                    }
//                }
                LichSu lichSu = lichSuArrayList.get(position);
                date = lichSu.getNgay();
                GetNLHT(idUser, date);
//                tv_BMR.setText(BMR.toString());
//                Sum = NLHT - (NLTH + BMR);
//                if (Sum >= 0) {
//                    tv_Sum.setText("Bạn đã thừa: " + Sum.toString() + " kcal");
//                } else {
//                    tv_Sum.setText("Bạn đã thiếu: " + Sum.toString() + " kcal");
//                }

//                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                LichSu lichSu = lichSuArrayList.get(position);
//                SendIdNhomMA(nhomMonAn.getId(position));
//                Log.d("AAA", String.valueOf(nhomMonAn.getId(position)));
                date = lichSu.getNgay();
                Intent intent = new Intent(LichSuActivity.this, LichsuChitietActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
                return false;
            }
        });

    }

    private void GetNLHT(final int idUser, final String date) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGethdau,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                NLHT = object.getString("NLHT");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (NLHT == null) {
                            NLHT = "0";
                            tv_NLHT.setText("Năng lượng hấp thụ: -" + NLHT + " kcal");
                        } else {
                            tv_NLHT.setText("Năng lượng hấp thụ: " + NLHT + " kcal");
                        }
                        GetNLTH(idUser, date);

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
                params.put("date", date);
                Log.d("idMA", String.valueOf(date));
                return params;
            }
        };
        requestQueue.add(stringRequest);
//        return NLHT;
    }

    private void GetNLTH(final int idUser, final String date) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGethdtt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                NLTH = object.getString("NLTH");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (NLTH == null) {
                            NLTH = "0";
                            tv_NLTH.setText("Năng lượng tiêu hao: -" + NLTH + " kcal");
                        } else {
                            tv_NLTH.setText("Năng lượng tiêu hao: -" + NLTH + " kcal");
                        }
                        Sum = Float.valueOf(NLHT) - (Float.valueOf(NLTH) + BMR);
                        if (Sum >= 0) {
                            tv_Sum.setText("Bạn đã thừa: " + Sum.toString() + " kcal");
                        } else {
                            tv_Sum.setText("Bạn đã thiếu: " + Sum.toString() + " kcal");
                        }

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
                params.put("date", date);
                return params;
            }
        };
        requestQueue.add(stringRequest);
//        return NLTH;
    }


    private void AnhXa() {
        tvTittleLS = (TextView) findViewById(R.id.tvLichSu);
        listView = (ListView) findViewById(R.id.lvLichSu);
        textClock = (TextClock) findViewById(R.id.textclock);
        tv_BMR = (TextView) findViewById(R.id.tv_BMR);
        tv_NLHT = (TextView) findViewById(R.id.tv_NLHT);
        tv_NLTH = (TextView) findViewById(R.id.tv_NLTH);
        tv_Sum = (TextView) findViewById(R.id.tv_Sum);
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

    private void GetLichSu(final int idUser) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertLichSu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MonAnActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("res", String.valueOf(response));
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    lichSuArrayList.add(new LichSu(
                                            object.getString("Ngay")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
//                Log.d("idMA", String.valueOf(idUser));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}

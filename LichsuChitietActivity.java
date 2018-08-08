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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LichsuChitietActivity extends AppCompatActivity {
    ListView lvhdau, lvhdtt;
    TextView tvTittle, tvDateTime, tvTittleMA, tvTittleMTT, tvSumMA, tvSumMTT, tvSum;
    TextClock textClock;
    LichsuChitietMonAn lichsuChitietMonAn;
    LichsuChitietMonTheThao lichsuChitietMonTheThao;
    public float TongNLHT = 0, TongNLTH = 0;
    Integer idUser;
    String date, thu, thang;


    String urlGethdau = "http://192.168.56.1:8080/QLDD/GetLSMonAn.php",
            urlGethdtt = "http://192.168.56.1:8080/QLDD/getlsmontt.php";
    ArrayList<LichsuChitietMonAn> lichsuChitietMonAnArrayList;
    LichsuChitietMonAnAdapter adapter;
    LichsuChitietMonTheThaoAdapter adapter2;
    ArrayList<LichsuChitietMonTheThao> lichsuChitietMonTheThaoArrayList;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_chitiet_lichsu);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        AnhXa();
        XulyDateTime();

//        String dtStart = "2010-10-15T09:27:37Z";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date dateAfter = format.parse(date);
//            System.out.println(dateAfter);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        tvTittle.setText(tvTittle.getText() + "\n" + dả );
//        String formatdate = "yyyy-M-d";
//        textClock.setFormat12Hour(formatdate);
//        textClock.setFormat24Hour(formatdate);
////        date = (String) textClock.getText();

        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
//        float BMR = sharedPreferences.getFloat("BMR", 0.0f);
        idUser = sharedPreferences.getInt("idUser", 0);
//        date = sharedPreferences.getString("date","");
//        Log.d("idUSer2", String.valueOf(idUser));


//        Intent intent = getIntent();
//        float BMR = 0;
//        BMR = intent.getFloatExtra("BMR", BMR);
//        tViewBMR.setText("Chỉ số BMR: " + BMR + " Kcal");
        lichsuChitietMonAnArrayList = new ArrayList<>();
        adapter = new LichsuChitietMonAnAdapter(LichsuChitietActivity.this, R.layout.dong_lichsu_monan, lichsuChitietMonAnArrayList);
        lvhdau.setAdapter(adapter);
        lichsuChitietMonTheThaoArrayList = new ArrayList<>();
        adapter2 = new LichsuChitietMonTheThaoAdapter(LichsuChitietActivity.this, R.layout.dong_lichsu_monthethao, lichsuChitietMonTheThaoArrayList);
        lvhdtt.setAdapter(adapter2);
        GetLSAU(idUser, date);

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


    private void GetLSAU(final int idUser, final String date) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGethdau,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        lichsuChitietMonAnArrayList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    lichsuChitietMonAnArrayList.add(new LichsuChitietMonAn(
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
//                        Log.d("aaaa", String.valueOf(adapter.getCount()));
                        for (int i = 0; i < adapter.getCount(); i++) {
                            lichsuChitietMonAn = lichsuChitietMonAnArrayList.get(i);
                            TongNLHT = TongNLHT + lichsuChitietMonAn.getNLHT();
                        }
                        tvSumMA.setText("Tổng: " + TongNLHT+ " kcal");
                        GetLSTT(idUser, date);
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
    }

    private void GetLSTT(final int idUser, final String date) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGethdtt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        lichsuChitietMonTheThaoArrayList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("res", response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    lichsuChitietMonTheThaoArrayList.add(new LichsuChitietMonTheThao(
                                            object.getInt("idhd"),
                                            object.getInt("idMTT"),
                                            object.getString("TenMonTT"),
                                            object.getInt("ThoiGian"),
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
                            lichsuChitietMonTheThao = lichsuChitietMonTheThaoArrayList.get(i);
                            TongNLTH = TongNLTH + lichsuChitietMonTheThao.getNLTH();
                        }
                        tvSumMTT.setText("Tổng: -" + TongNLTH + " kcal");
                        SharedPreferences sharedPreferences = getSharedPreferences("BMR_data", MODE_PRIVATE);
                        float BMR = sharedPreferences.getFloat("BMR", 0.0f);
                        if (TongNLHT >= TongNLTH + BMR) {
                            tvSum.setText("Bạn đã dư thừa " + (TongNLHT - (TongNLTH + BMR)) + " kcal");
                        } else {
                            tvSum.setText("Bạn đã thiếu hụt " + (-(TongNLHT - (TongNLTH + BMR))) + " kcal");
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
                params.put("date", date);
                Log.d("idMA", String.valueOf(idUser));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        lvhdau = (ListView) findViewById(R.id.lvhdau);
        lvhdtt = (ListView) findViewById(R.id.lvhdtt);
        tvTittle = (TextView) findViewById(R.id.tvTittle);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        tvTittleMA = (TextView) findViewById(R.id.tvTittleMA);
        tvTittleMTT = (TextView) findViewById(R.id.tvTittleMTT);

        tvSumMA = (TextView) findViewById(R.id.tvSumMA);
        tvSumMTT = (TextView) findViewById(R.id.tvSumTT);
        tvSum = (TextView) findViewById(R.id.tvSum);

    }

    private void XulyDateTime() {
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

//            switch (monthOfYear) {
//                case 0:
//                    thang = "Tháng 1";
//                    break;
//                case 1:
//                    thang = "Tháng 2";
//                    break;
//                case 2:
//                    thang = "Tháng 3";
//                    break;
//                case 3:
//                    thang = "Tháng 4";
//                    break;
//                case 4:
//                    thang = "Tháng 5";
//                    break;
//                case 5:
//                    thang = "Tháng 6";
//                    break;
//                case 6:
//                    thang = "Tháng 7";
//                    break;
//                case 7:
//                    thang = "Tháng 8";
//                    break;
//                case 8:
//                    thang = "Tháng 9";
//                    break;
//                case 9:
//                    thang = "Tháng 10";
//                    break;
//                case 10:
//                    thang = "Tháng 11";
//                    break;
//                case 11:
//                    thang = "Tháng 12";
//                default:
//                    thu = "Lỗi";
//            }

            tvDateTime.setText(thu + ", Ngày " + c.get(Calendar.DAY_OF_MONTH) + " Tháng " + thang + " Năm " + c.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}

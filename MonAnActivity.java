package com.dvt.tiendat.quanlydinhduong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class MonAnActivity extends AppCompatActivity {
    ListView lvMonAn;
    TextView tvTittle;
    Button btnAddHDTT;
    EditText edtSearch;
    String urlGetMA = "http://192.168.56.1:8080/QLDD/GetMonAn.php";
    ArrayList<MonAn> monAnArrayList;
    MonAnAdapter adapter;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsmon_an);
        AnhXa();
        final NhomMonAn nhomMonAn = (NhomMonAn) getIntent().getSerializableExtra("nhommonan");
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        Log.d("idd", String.valueOf(id));
        GetMonAn(id);


        monAnArrayList = new ArrayList<>();
        adapter = new MonAnAdapter(MonAnActivity.this, R.layout.dong_monan, monAnArrayList);
        lvMonAn.setAdapter(adapter);


        lvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MonAnActivity.this, MonAnChiTietActivity.class);
                ArrayList<MonAn> list = (ArrayList<MonAn>) adapter.getMonAnList();
                MonAn monAn = list.get(position);
                Log.d("id335", String.valueOf(monAn.getId()));
                intent.putExtra("monan", monAn);

                startActivity(intent);
            }
        });

        btnAddHDTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonAnActivity.this, MonTTActivity.class);
                startActivity(intent);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    private void AnhXa() {
        lvMonAn = (ListView) findViewById(R.id.lvMonAn);
        tvTittle = (TextView) findViewById(R.id.textViewDSMA);
        btnAddHDTT = (Button) findViewById(R.id.buttonDSMTT);
        edtSearch = (EditText) findViewById(R.id.inputSearch);
    }

//    public void SendIdNhomMA(final int id) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetMA,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MonAnActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("idNhomMA", String.valueOf(id));
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//        Log.d("a2", String.valueOf(id));
//    }

    private void GetMonAn(final int idMA) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetMA,
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
                                    monAnArrayList.add(new MonAn(
                                            object.getInt("IDMA"),
                                            object.getString("TenMonAn"),
                                            object.getString("MoTa"),
                                            object.getString("HinhAnh"),
                                            object.getInt("NangLuong"),
                                            object.getInt("IDNhomMA")
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

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idNhomMA", String.valueOf(idMA));
                Log.d("idMA", String.valueOf(idMA));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

//    private void GetMonAn(final int id) {
//        final RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urlGetMA, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d("a3", String.valueOf(response));
//                        try {
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject json = response.getJSONObject(i);
//                                monAnArrayList.add(new MonAn(
//                                        json.getInt("IDMA"),
//                                        json.getString("TenMonAn"),
//                                        json.getString("MoTa"),
//                                        json.getString("HinhAnh"),
//                                        json.getInt("NangLuong"),
//                                        json.getInt("IDNhomMA")
//                                ));
//                                Log.e("kien", json.getString("TenMonAn"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        adapter.notifyDataSetChanged();
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.getMessage();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("idNhomMA", String.valueOf(id));
//                return params;
//            }
//        };
//        requestQueue.add(jsonArrayRequest);
//    }
}


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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MonTTActivity extends AppCompatActivity {
    String urlGetMTT = "http://192.168.56.1:8080/QLDD/GetMonTT.php";
    ListView LvMTT;
    TextView tvTittle;
    Button btnAddHDAU;
    EditText edtSearch;
    ArrayList<MonTheThao> monTheThaoArrayList;


    MonTTAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsmon_tt);
        AnhXa();
        monTheThaoArrayList = new ArrayList<>();
        adapter = new MonTTAdapter(MonTTActivity.this, R.layout.dong_monthethao, monTheThaoArrayList);
        LvMTT.setAdapter(adapter);
        GetMonTT(urlGetMTT);
        LvMTT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MonTTActivity.this, MonTTChiTietActivity.class);
                ArrayList<MonTheThao> list = (ArrayList<MonTheThao>) adapter.getMonTheThaoList();
                MonTheThao monTheThao = list.get(position);
                Log.d("id336", String.valueOf(adapter.getItemId(position)));
                Log.d("id335", String.valueOf(monTheThao.getId()));
                intent.putExtra("monthethao", monTheThao);
                startActivity(intent);
            }
        });

        btnAddHDAU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MonTTActivity.this,NhomMonAnActivity.class);
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
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void AnhXa() {
        LvMTT = (ListView) findViewById(R.id.lvMonTT);
        tvTittle = (TextView) findViewById(R.id.textViewDSMTT);
        btnAddHDAU=(Button) findViewById(R.id.buttonDSMA);
        edtSearch=(EditText) findViewById(R.id.inputSearch);
    }

    @Override
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

    private void GetMonTT(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("A1", String.valueOf(response));
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                monTheThaoArrayList.add(new MonTheThao(
                                        object.getInt("IDMTT"),
                                        object.getString("TenMonTT"),
                                        object.getString("MoTa"),
                                        object.getString("HinhAnh"),
                                        object.getInt("NangLuong")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}

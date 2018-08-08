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
import android.widget.EditText;
import android.widget.ListView;

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

public class NhomMonAnActivity extends AppCompatActivity {
    ListView lvNhomMonAn;
    EditText edtSearch;
    String urlGetNhomMA = "http://192.168.56.1:8080/QLDD/GetNhomMonAn.php";
    ArrayList<NhomMonAn> nhomMonAnArrayList;
    NhomMonAnAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsnhommon_an);
        AnhXa();
        nhomMonAnArrayList = new ArrayList<>();
        adapter = new NhomMonAnAdapter(NhomMonAnActivity.this, R.layout.dong_nhommonan, nhomMonAnArrayList);
        lvNhomMonAn.setAdapter(adapter);

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

        GetNhomMonAn(urlGetNhomMA);
        lvNhomMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NhomMonAn nhomMonAn = nhomMonAnArrayList.get(position);
//                SendIdNhomMA(nhomMonAn.getId(position));
                Log.d("AAA", String.valueOf(nhomMonAn.getId(position)));
                Intent intent = new Intent(NhomMonAnActivity.this, MonAnActivity.class);
                ArrayList<NhomMonAn> list = (ArrayList<NhomMonAn>) adapter.getNhomMonAnList();
                NhomMonAn nhomMA =list.get(position);
                intent.putExtra("nhommonan", nhomMonAn);
                intent.putExtra("id",nhomMA.getId(position));
                startActivity(intent);


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
        lvNhomMonAn = (ListView) findViewById(R.id.lvNhomMonAn);
        edtSearch =(EditText) findViewById(R.id.inputSearch);
    }

    private void GetNhomMonAn(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                nhomMonAnArrayList.add(new NhomMonAn(
                                        object.getInt("IdNhomMA"),
                                        object.getString("TenNhomMA")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
//                        Toast.makeText(NhomMonAnActivity.this,response.toString(), Toast.LENGTH_SHORT).show();
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

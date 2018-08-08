package com.dvt.tiendat.quanlydinhduong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TienDat on 4/22/2018.
 */

public class ListKienThucActivity extends AppCompatActivity {
    ListView listView;
    TextView tvTittle;
    ArrayList<ListKienThuc> listKienThucArrayList;
    ListKienThucAdapter adapter;

    String urlInsertLichSu = "http://192.168.56.1:8080/QLDD/GetKienthuc.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kienthuc);

        AnhXa();


        listKienThucArrayList = new ArrayList<>();
        adapter = new ListKienThucAdapter(ListKienThucActivity.this, R.layout.dong_kienthuc, listKienThucArrayList);
        listView.setAdapter(adapter);

        GetListKienThuc(urlInsertLichSu);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListKienThuc listKienThuc = listKienThucArrayList.get(position);
                Intent intent = new Intent(ListKienThucActivity.this, KienthucActivity.class);
                intent.putExtra("link",listKienThuc.getLink());
                startActivity(intent);


            }
        });


    }

    private void AnhXa() {
        tvTittle = (TextView) findViewById(R.id.tvTittle);
        listView = (ListView) findViewById(R.id.lvKienthuc);
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

    private void GetListKienThuc(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.d("res2", String.valueOf(response));
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listKienThucArrayList.add(new ListKienThuc(
                                        object.getInt("id"),
                                        object.getString("tittle"),
                                        object.getString("link")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
//                        Toast.makeText(ListKienThucActivity.this,response.toString(), Toast.LENGTH_SHORT).show();
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

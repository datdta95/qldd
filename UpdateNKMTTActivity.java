package com.dvt.tiendat.quanlydinhduong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateNKMTTActivity extends AppCompatActivity {
    String urlUpdateHDTT = "http://192.168.56.1:8080/QLDD/UpdateHDTT.php";
    TextView txtvTenMTT_UD, txtvNLTH_UD;
    EditText edtThoiGian_UD;
    Button btnTinhNLTH_UD, btnUDHDTT;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nkmtt);
        Intent intent = getIntent();
        final NhatKiMonTheThao nhatKiMonTheThao = (NhatKiMonTheThao) intent.getSerializableExtra("nhatKiMonTheThao");
        id = nhatKiMonTheThao.getIdhd();
        Log.d("id", String.valueOf(id));
        AnhXa();

        txtvTenMTT_UD.setText(nhatKiMonTheThao.getTenMonTT());
        edtThoiGian_UD.setText(nhatKiMonTheThao.getThoiGian() + "");
        btnTinhNLTH_UD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtThoiGian_UD.getText().toString().isEmpty())
                    Toast.makeText(UpdateNKMTTActivity.this, "Yêu cầu nhập thời gian hoạt động thể thao", Toast.LENGTH_SHORT).show();
                else
                    txtvNLTH_UD.setText((nhatKiMonTheThao.getNLTH() / nhatKiMonTheThao.getThoiGian() * Float.parseFloat(edtThoiGian_UD.getText().toString()) + " Kcal"));
            }
        });
        btnUDHDTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtThoiGian_UD.getText().toString().isEmpty())
                    Toast.makeText(UpdateNKMTTActivity.this, "Yêu cầu nhập trọng lượng món ăn", Toast.LENGTH_SHORT).show();
                else {
                    UpdateHDTT();
                    Intent intent = new Intent(UpdateNKMTTActivity.this, NhatKiActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void UpdateHDTT() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateHDTT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(UpdateNKMTTActivity.this, "Hoạt động thể thao cập nhật thành công", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(UpdateNKMTTActivity.this, "Hoạt động thể thao cập nhật không thành công", Toast.LENGTH_SHORT).show();
                        int status = 0;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getInt("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        if (status == 1) {
//                            Toast.makeText(UpdateNKMAActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(UpdateNKMAActivity.this, NhatKiActivity.class));
//                        } else
//                            Toast.makeText(UpdateNKMAActivity.this, "Sửa không thành công", Toast.LENGTH_SHORT).show();
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
                String idhdtt = String.valueOf(id);
                String thoigian = edtThoiGian_UD.getText().toString();
                params.put("idhdtt", idhdtt);
                Log.d("id2", String.valueOf(id));
                params.put("ThoiGian", thoigian);
                Log.d("kg", edtThoiGian_UD.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        txtvTenMTT_UD = (TextView) findViewById(R.id.txtvTenMTT_UD);
        txtvNLTH_UD = (TextView) findViewById(R.id.txtvNLTH_UD);
        edtThoiGian_UD = (EditText) findViewById(R.id.edtThoiGian_UD);
        btnTinhNLTH_UD = (Button) findViewById(R.id.btnTinhNLTH_UD);
        btnUDHDTT = (Button) findViewById(R.id.btnUDHDTT);
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

}

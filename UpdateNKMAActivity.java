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

public class UpdateNKMAActivity extends AppCompatActivity {
    String urlUpdateHDAA = "http://192.168.56.1:8080/QLDD/UpdateHDAU.php";
    TextView txtvTenMA_UD, txtvNLHT_UD;
    EditText edtTrongLuong_UD;
    Button btnTinhNLHT_UD, btnUDHDAU;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nkm);
        Intent intent = getIntent();
        final NhatKiMonAn nhatKiMonAn = (NhatKiMonAn) intent.getSerializableExtra("nhatKiMonAn");
        id = nhatKiMonAn.getIdhd();
        Log.d("id", String.valueOf(id));
        AnhXa();

        txtvTenMA_UD.setText(nhatKiMonAn.getTenMonAn());
        edtTrongLuong_UD.setText(nhatKiMonAn.getTrongLuong() + "");
        btnTinhNLHT_UD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTrongLuong_UD.getText().toString().isEmpty())
                    Toast.makeText(UpdateNKMAActivity.this, "Yêu cầu nhập trọng lượng món ăn", Toast.LENGTH_SHORT).show();
                else
                    txtvNLHT_UD.setText((nhatKiMonAn.getNLHT() / nhatKiMonAn.getTrongLuong() * Integer.parseInt(edtTrongLuong_UD.getText().toString()) + " Kcal"));
            }
        });
        btnUDHDAU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTrongLuong_UD.getText().toString().isEmpty())
                    Toast.makeText(UpdateNKMAActivity.this, "Yêu cầu nhập trọng lượng món ăn", Toast.LENGTH_SHORT).show();
                else {
                    UpdateHDAU();
                    Intent intent = new Intent(UpdateNKMAActivity.this, NhatKiActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void UpdateHDAU() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateHDAA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(UpdateNKMAActivity.this, "Hoạt động ăn uống cập nhật thành công", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(UpdateNKMAActivity.this, "Hoạt động ăn uống cập nhật không thành công", Toast.LENGTH_SHORT).show();
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
                String idhdau = String.valueOf(id);
                String trongluong = edtTrongLuong_UD.getText().toString();
                params.put("idhdau", idhdau);
                Log.d("id2", String.valueOf(id));
                params.put("trongluongMA", trongluong);
                Log.d("kg", edtTrongLuong_UD.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        txtvTenMA_UD = (TextView) findViewById(R.id.txtvTenMA_UD);
        txtvNLHT_UD = (TextView) findViewById(R.id.txtvNLHT_UD);
        edtTrongLuong_UD = (EditText) findViewById(R.id.edtTrongLuong_UD);
        btnTinhNLHT_UD = (Button) findViewById(R.id.btnTinhNLHT_UD);
        btnUDHDAU = (Button) findViewById(R.id.btnUDHDAU);
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

package com.dvt.tiendat.quanlydinhduong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DangKy extends AppCompatActivity {
    String urlDangKy = "http://192.168.56.1:8080/QLDD/signin.php";
    EditText edtUserName, edtPassWord, edtEmail;
    Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin(urlDangKy);
            }
        });
    }

    private void Signin(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int status = 2;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getInt("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (status == 1) {
                            Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DangKy.this, MainActivity.class));
                        }
                        if (status == 0) {
                            Toast.makeText(DangKy.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKy.this, "System error.", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", edtUserName.getText().toString().trim());
                params.put("password", edtPassWord.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        edtUserName = (EditText) findViewById(R.id.edtUserName_DK);
        edtPassWord = (EditText) findViewById(R.id.edtPassWord_DK);
        edtEmail = (EditText) findViewById(R.id.edtEmail_DK);
        btnDangKy = (Button) findViewById(R.id.btnDangKy_DK);
    }
}

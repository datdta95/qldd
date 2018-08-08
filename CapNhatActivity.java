package com.dvt.tiendat.quanlydinhduong;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TienDat on 4/28/2018.
 */

public class CapNhatActivity extends AppCompatActivity {
    TextView tv_tittle_mk, tv_show_mk_old, tv_mk_old, tv_mk_new, tv_mk_again;
    EditText edt_mk_new, edt_mk_again;
    Button btn_get_mk, btn_change_mk, btn_capnhat;
    TextInputLayout tInputLayout_Username;
    TextInputEditText tInputEdit_Username;
    String user;
    String pass;

    String urlGetPass = "http://192.168.56.1:8080/QLDD/getusername.php";
    String urlUpdatePass = "http://192.168.56.1:8080/QLDD/updatematkhau.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_matkhau);

        AnhXa();

        tv_mk_old.setVisibility(View.GONE);
        tv_show_mk_old.setVisibility(View.GONE);
        tv_mk_new.setVisibility(View.GONE);
        tv_mk_again.setVisibility(View.GONE);
        btn_capnhat.setVisibility(View.GONE);
        edt_mk_again.setVisibility(View.GONE);
        edt_mk_new.setVisibility(View.GONE);

        btn_get_mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tInputEdit_Username.getText().toString().trim() != null) {
                    user = String.valueOf(tInputEdit_Username.getText());
                    Log.d("user", user);
                    GetMonAn(user);
                    tv_mk_old.setVisibility(View.VISIBLE);
                    tv_show_mk_old.setVisibility(View.VISIBLE);
                }else Toast.makeText(CapNhatActivity.this, "Mời bạn nhập Username", Toast.LENGTH_SHORT).show();


            }
        });

        btn_change_mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_mk_new.getVisibility() == v.GONE && tv_mk_again.getVisibility() == v.GONE && btn_capnhat.getVisibility() == v.GONE && edt_mk_new.getVisibility() == v.GONE && edt_mk_again.getVisibility() == v.GONE) {
                    tv_mk_new.setVisibility(v.VISIBLE);
                    tv_mk_again.setVisibility(v.VISIBLE);
                    btn_capnhat.setVisibility(v.VISIBLE);
                    edt_mk_again.setVisibility(v.VISIBLE);
                    edt_mk_new.setVisibility(v.VISIBLE);
                }else {
                    tv_mk_new.setVisibility(v.GONE);
                    tv_mk_again.setVisibility(v.GONE);
                    btn_capnhat.setVisibility(v.GONE);
                    edt_mk_again.setVisibility(v.GONE);
                    edt_mk_new.setVisibility(v.GONE);
                }
            }
        });

        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = String.valueOf(edt_mk_new.getText());
                String pass_again = String.valueOf(edt_mk_again.getText());
                if (pass.equals(pass_again) && pass != null && pass_again != null) {
                    user = String.valueOf(tInputEdit_Username.getText());
                    Log.d("userr", user);
                    Log.d("pass", pass);
                    UpdatePass(user, pass);

                } else
                    Toast.makeText(CapNhatActivity.this, "Mật khẩu bạn nhập không khớp", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void UpdatePass(final String user, final String pass) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdatePass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(CapNhatActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(CapNhatActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
//                        int status = 0;
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            status = jsonObject.getInt("status");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
                    }
//                        if (status == 1) {
//                            Toast.makeText(UpdateNKMAActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(UpdateNKMAActivity.this, NhatKiActivity.class));
//                        } else
//                            Toast.makeText(UpdateNKMAActivity.this, "Sửa không thành công", Toast.LENGTH_SHORT).show();

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pass", pass);
                params.put("username", user);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void GetMonAn(final String user) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetPass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MonAnActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        if (response.trim().equals("error")) {
                            Toast.makeText(CapNhatActivity.this, "Sai tên tài khoản. Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    pass = object.getString("password");
                                    Log.d("obj", object.getString("password"));

                                }
                                tv_show_mk_old.setText(pass);
                                Log.d("pass", pass);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("res", String.valueOf(response));

//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                try {
//                                    JSONObject object = jsonArray.getJSONObject(i);
//                                    monAnArrayList.add(new MonAn(
//                                            object.getInt("IDMA"),
//                                            object.getString("TenMonAn"),
//                                            object.getString("MoTa"),
//                                            object.getString("HinhAnh"),
//                                            object.getInt("NangLuong"),
//                                            object.getInt("IDNhomMA")
//                                    ));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            adapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        tv_tittle_mk = (TextView) findViewById(R.id.tv_tittle_mk);
        tv_show_mk_old = (TextView) findViewById(R.id.tv_show_mk_old);
        tv_mk_old = (TextView) findViewById(R.id.tv_mk_old);
        tv_mk_new = (TextView) findViewById(R.id.tv_mk_new);
        tv_mk_again = (TextView) findViewById(R.id.tv_mk_again);
        edt_mk_new = (EditText) findViewById(R.id.edt_mk_new);
        edt_mk_again = (EditText) findViewById(R.id.edt_mk_again);
        btn_get_mk = (Button) findViewById(R.id.btn_get_mk);
        btn_change_mk = (Button) findViewById(R.id.btn_change_mk);
        btn_capnhat = (Button) findViewById(R.id.btn_capnhat);
        tInputLayout_Username = (TextInputLayout) findViewById(R.id.tInputLayout_Username);
        tInputEdit_Username = (TextInputEditText) findViewById(R.id.tInputEdit_Username);
    }
}

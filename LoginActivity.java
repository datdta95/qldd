package com.dvt.tiendat.quanlydinhduong;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TienDat on 4/10/2018.
 */

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edtUserName;
    private EditText edtPassWord;
    private Button btnLogin;
    private Button btnRegister;
    private ProgressDialog pDialog;
    private CheckBox cbRemember;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private Button btnQuenMK;

    /**
     * URL : URL_LOGIN
     * param : KEY_USERNAME KEY_PASSWORD
     */
    public static final String URL_LOGIN = "http://192.168.56.1:8080/QLDD/login_demo.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";

//    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_demo);
        addControl();
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        loginPrefsEditor = sharedPreferences.edit();

        saveLogin = sharedPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            edtUserName.setText(sharedPreferences.getString(KEY_USERNAME, ""));
            edtPassWord.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
            cbRemember.setChecked(true);
        }


        addEvent();

        btnQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,CapNhatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edtUserName.getText().toString().trim();
                String password = edtPassWord.getText().toString().trim();

                if (cbRemember.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString(KEY_USERNAME, username);
                    loginPrefsEditor.putString(KEY_PASSWORD, password);
                    loginPrefsEditor.commit();
                    loginAccount(username, password);
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                    loginAccount(username, password);
                }
//                loginAccount(username, password);

//                doSomethingElse();
                //nếu người dùng có chọn ghi nhớ
//                if (cbRemember.isChecked()) {
//                    //lưu lại thông tin đăng nhập
//                    String username = edtUserName.getText().toString().trim();
//                    String password = edtPassWord.getText().toString().trim();
//
//                    saveData(username, password);
//                    loginAccount(username, password);
//                } else {
//                    clearData();//xóa thông tin đã lưu
//                    //Get value input
//                    String username = edtUserName.getText().toString().trim();
//                    String password = edtPassWord.getText().toString().trim();
//                    // Call method
//                    loginAccount(username, password);
//                }
//                //nếu thông tin đăng nhập đúng thì đến màng hình home
//                if (edtUserName.getText().toString().equals(userLogin) && edtPassWord.getText().toString().equals(passLogin)) {
//                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                } else
//                    Toast.makeText(LoginActivity.this, "Thông tin đăng nhập không đúng", Toast.LENGTH_SHORT).show();
            }

        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        edtUserName = (EditText) findViewById(R.id.editUsername);
        edtPassWord = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng nhập...");
        pDialog.setCanceledOnTouchOutside(false);
        btnQuenMK = (Button) findViewById(R.id.btnQuenMK);
    }

    /**
     * Method login
     *
     * @param username
     * @param password result json
     */
    public void loginAccount(final String username, final String password) {

        if (checkEditText(edtUserName) && checkEditText(edtPassWord)) {
            pDialog.show();
            StringRequest requestLogin = new StringRequest(Request.Method.POST, URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            String message = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getInt("success") == 1) {
                                    Account account = new Account();
                                    account.setId(jsonObject.getInt("id"));
                                    account.setUserName(jsonObject.getString("user_name"));
                                    account.setEmail(jsonObject.getString("email"));
                                    message = jsonObject.getString("message");
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginActivity.this, ThongSoCoTheActivity.class);
//                                    intent.putExtra("login", account.getUserName());
                                    startActivity(intent);

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user", account.getUserName());
                                    editor.putInt("idUser", account.getId());
                                    editor.commit();

                                } else {
                                    message = jsonObject.getString("message");
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            pDialog.dismiss();
                        }
                    }) {
                /**
                 * set paramater
                 * */
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(KEY_USERNAME, username);
                    params.put(KEY_PASSWORD, password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(requestLogin);
        }
    }

    /**
     * Check input
     */
    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }
//
//    private void clearData() {
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();
//    }
//
//    private void saveData(String username, String Pass) {
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(KEY_USERNAME, username);
//        editor.putString(KEY_PASSWORD, Pass);
//        editor.putBoolean(REMEMBER, cbRemember.isChecked());
//        editor.commit();
//    }
//
//    private void loadData() {
//        SharedPreferences sharedPreferences= getSharedPreferences("my_data", MODE_PRIVATE);
//
//        if (sharedPreferences.getBoolean(REMEMBER, false)) {
//            edtUserName.setText(sharedPreferences.getString(KEY_USERNAME, ""));
//            edtPassWord.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
//            cbRemember.setChecked(true);
//        } else
//            cbRemember.setChecked(false);
//    }
}

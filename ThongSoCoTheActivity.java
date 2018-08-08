package com.dvt.tiendat.quanlydinhduong;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ThongSoCoTheActivity extends AppCompatActivity {
    TextView txtvBMR, tViewHello;
    RadioGroup rdgGioiTinh;
    RadioButton radioButton, radioButton2;
    //    EditText edtChieuCao, edtCanNang, edtPhanTramMo, edtDoTuoi;
    Button btnBMR, btnInfo;
    TextInputLayout tInputLayout_h, tInputLayout_w, tInputLayout_a, tInputLayout_f;
    TextInputEditText tInputEdit_h, tInputEdit_w, tInputEdit_a, tInputEdit_f;
    float BMR;
    String user = "";
    private Handler mHandler = new Handler();

    Float high, weight, fat;
    Integer age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_so_ct);

        AnhXa();
        SharedPreferences sharedPreferences = getSharedPreferences("BMR_data", MODE_PRIVATE);
        BMR = sharedPreferences.getFloat("BMR", 0.0f);
        age = sharedPreferences.getInt("AGE", 0);

//        SharedPreferences sharedPreferences_2 = getSharedPreferences("data_thongso", MODE_PRIVATE);
        high = sharedPreferences.getFloat("HIGH", 0.0f);
        weight = sharedPreferences.getFloat("WEIGHT", 0.0f);
        fat = sharedPreferences.getFloat("FAT", 0.0f);

        tInputEdit_h.setText(Float.toString(high));
        tInputEdit_w.setText(Float.toString(weight));
        tInputEdit_a.setText(Integer.toString(age));
        tInputEdit_f.setText(Float.toString(fat));


        txtvBMR.setText("BMR: " + Float.toString(BMR) + " kcal");
        SharedPreferences sharedPreferences_2 = getSharedPreferences("my_data", MODE_PRIVATE);
        user = sharedPreferences_2.getString("user", "user");
        tViewHello.setText("Hi, " + user + "!");

//        hiUser();
        tViewHello.setText("Hi, " + user + "!");

        tInputEdit_h.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tInputLayout_h.setError("Bạn phải bắt buộc nhập chiều cao");
                } else tInputLayout_h.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tInputEdit_w.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tInputLayout_w.setError("Bạn phải bắt buộc nhập cân nặng");
                } else tInputLayout_w.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tInputEdit_a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tInputLayout_a.setError("Bạn phải bắt buộc nhập chiều cao");
                } else tInputLayout_a.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnBMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tInputEdit_h.getText().length() == 0 || tInputEdit_w.getText().length() == 0 || tInputEdit_a.getText().length() == 0) {
                    Toast.makeText(ThongSoCoTheActivity.this, "Yêu cầu nhập đầy đu thông tin chiều cao, cân nặng và độ tuổi", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ThongSoCoTheActivity.this);
                    alertDialog.setTitle("Xác nhận");
                    alertDialog.setMessage("Đã kiểm tra thông tin?");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TinhBMR();
                            txtvBMR.setText("BMR: " + BMR + " kcal");
                            high = Float.valueOf(tInputEdit_h.getText().toString());
                            weight = Float.valueOf(tInputEdit_w.getText().toString());
                            age = Integer.valueOf(tInputEdit_a.getText().toString());
                            fat = Float.valueOf(tInputEdit_f.getText().toString());

                            SharedPreferences sharedPreferences = getSharedPreferences("BMR_data", MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.clear();
                            editor.putFloat("BMR", BMR);
                            editor.putInt("AGE", age);
                            editor.putFloat("HIGH", high);
                            editor.putFloat("WEIGHT", weight);
                            editor.putFloat("FAT", fat);

                            editor.commit();

//                            SharedPreferences sharedPreferences_2 = getSharedPreferences("data_thongso", MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor_2 = sharedPreferences.edit();
//
//
//                            editor_2.commit();


                            Toast.makeText(ThongSoCoTheActivity.this, "Chỉ số BMR của bạn: " + BMR, Toast.LENGTH_SHORT).show();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ThongSoCoTheActivity.this, NhatKiActivity.class);
                                    intent.putExtra("BMR", BMR);
                                    startActivity(intent);
                                }
                            }, 4000);
                        }
                    });
                    alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            }

        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongSoCoTheActivity.this, ListKienThucActivity.class);
                startActivity(intent);
            }
        });
    }

//    private String hiUser() {
//        Intent intent = getIntent();
//        user = intent.getStringExtra("login");
//        return user;
//    }

    private float TinhBMR() {
        if (Float.parseFloat(tInputEdit_f.getText().toString()) != 0.0) {
            if (tInputEdit_h.getText().length() == 0 || tInputEdit_w.getText().length() == 0)
                Toast.makeText(ThongSoCoTheActivity.this, "Yêu cầu nhập thêm chiều cao,cân nặng", Toast.LENGTH_SHORT).show();
            else {
                float CC = Float.parseFloat(tInputEdit_h.getText().toString());
                float CN = Float.parseFloat(tInputEdit_w.getText().toString());
                float PTM = Float.parseFloat(tInputEdit_f.getText().toString());
                BMR = (float) (370 + (21.6 * (100 - PTM)));
//                txtvBMR.setText("BMR: " + BMR + " kcal");
            }

        } else {
            if (tInputEdit_h.getText().length() == 0 || tInputEdit_w.getText().length() == 0 || tInputEdit_a.getText().length() == 0)
                Toast.makeText(ThongSoCoTheActivity.this, "Yêu cầu nhập thêm chiều cao,cân nặng,độ tuổi", Toast.LENGTH_SHORT).show();
            else {
                if (radioButton.isChecked()) {
                    float CC = Float.parseFloat(tInputEdit_h.getText().toString());
                    float CN = Float.parseFloat(tInputEdit_w.getText().toString());
                    float DT = Float.parseFloat(tInputEdit_a.getText().toString());
                    BMR = (float) (10 * CN + 6.25 * CC - 5 * DT + 5);
                } else {
                    float CC = Float.parseFloat(tInputEdit_h.getText().toString());
                    float CN = Float.parseFloat(tInputEdit_w.getText().toString());
                    float DT = Float.parseFloat(tInputEdit_a.getText().toString());
                    BMR = (float) (10 * CN + 6.25 * CC - 5 * DT - 161);
                }
//                txtvBMR.setText("BMR: " + BMR + " kcal");
            }

        }
        return BMR;

    }


    private void AnhXa() {
//        edtDoTuoi = (EditText) findViewById(R.id.edtDoTuoi);
//        rdgGioiTinh = (RadioGroup) findViewById(R.id.rdgGioiTinh);
//        edtChieuCao = (EditText) findViewById(R.id.edtChieuCao);
//        edtCanNang = (EditText) findViewById(R.id.edtCanNang);
//        edtPhanTramMo = (EditText) findViewById(R.id.edtPhanTramMo);
        btnBMR = (Button) findViewById(R.id.btnBMR);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        txtvBMR = (TextView) findViewById(R.id.txtvBMR);
        tViewHello = (TextView) findViewById(R.id.tViewHello);
        tInputEdit_h = (TextInputEditText) findViewById(R.id.tInputEdit_High);
        tInputLayout_h = (TextInputLayout) findViewById(R.id.tInputLayout_High);
        tInputEdit_w = (TextInputEditText) findViewById(R.id.tInputEdit_Weight);
        tInputLayout_w = (TextInputLayout) findViewById(R.id.tInputLayout_Weight);
        tInputEdit_a = (TextInputEditText) findViewById(R.id.tInputEdit_Age);
        tInputLayout_a = (TextInputLayout) findViewById(R.id.tInputLayout_Age);
        tInputEdit_f = (TextInputEditText) findViewById(R.id.tInputEdit_Fat);
        tInputLayout_f = (TextInputLayout) findViewById(R.id.tInputLayout_Fat);

        btnInfo = (Button) findViewById(R.id.btnInfo);
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

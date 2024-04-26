package com.example.giuaky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin, btnSignup;

    FirebaseAuth myAuth;

    DatabaseLogin dbLogin;

    CheckBox cbLuuTaiKhoan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        myAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        dbLogin= new DatabaseLogin(this);

        setControl();
        setEvent();
    }

    private void setControl(){
        edtUsername = (EditText) findViewById(R.id.username);
        edtPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup= (Button) findViewById(R.id.btn_sign_up);
        cbLuuTaiKhoan= (CheckBox) findViewById(R.id.cbLuuTK);
    }

    private void setEvent(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUserName, getPassword;
                getUserName = edtUsername.getText().toString();
                getPassword = edtPassword.getText().toString();

                boolean checkLogin= dbLogin.checkuserNamePassword(getUserName, getPassword);

//                if(getUserName.isEmpty() || getPassword.isEmpty()){
//                    Toast.makeText(MainActivity.this, "Vui long nhap day du  \nusername va password", Toast.LENGTH_SHORT).show();
//                }
                if(checkInput() == false){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ \nusername va password", Toast.LENGTH_SHORT).show();
                }
                else if(checkLogin){
                    if(cbLuuTaiKhoan.isChecked()){
                        writePassword();
                    }
                    else {
                        notWritePassword();
                    }
                    Intent homeLogin = new Intent(MainActivity.this, HomeLoginSuccess.class);
                    homeLogin.putExtra("username", getUserName);
                    startActivity(homeLogin);
                    finish();
//                    Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_layout= new Intent(MainActivity.this, DangKyActivity.class);
                startActivity(register_layout);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("SaveTK", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username",  "").toString();
        String password = sharedPreferences.getString("password", "").toString();

        edtUsername.setText(username);
        edtPassword.setText(password);
    }

    private void writePassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("SaveTK", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", edtUsername.getText().toString());
        editor.putString("password", edtPassword.getText().toString());
        editor.apply();
    }
    private void notWritePassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("SaveTK", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    private void showError(EditText edtText, String s){
        edtText.setError(s);
        edtText.requestFocus();
    }

    private boolean checkInput(){
        String getUserName, getPassword;
        getUserName = edtUsername.getText().toString();
        getPassword = edtPassword.getText().toString();

        if(getUserName.isEmpty()){
            showError(edtUsername, "Vui lòng nhập trường này");
            return false;
        }
        else if(getPassword.isEmpty()){
            showError(edtPassword, "Vui lòng nhập trường này");
            return false;
        }
        return true;
    }

}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivityBai01 extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau, edtSDT, edtEmail;

    Button btnDangKy, btnNhapLai;

    TextView tvThongTin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bai01);
        setControll();
        setEvent();
    }

    private void setControll() {
        edtTaiKhoan= findViewById(R.id.edtTaiKhoan);
        edtMatKhau= findViewById(R.id.edtMatKhau);
        edtSDT= findViewById(R.id.edtSDT);
        edtEmail= findViewById(R.id.edtEmail);

        btnDangKy= findViewById(R.id.btnDangKy);
        btnNhapLai= findViewById(R.id.btnNhapLai);

        tvThongTin= findViewById(R.id.tvThongTin);
    }

    private void setEvent(){
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy();
            }
        });

        btnNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhapLai();
            }
        });
    }

    private void nhapLai() {
        edtTaiKhoan.setText("");
        edtMatKhau.setText("");
        edtSDT.setText("");
        edtEmail.setText("");
    }
    private void showError(EditText edtText, String s){
        edtText.setError(s);
        edtText.requestFocus();
    }
    private void dangKy() {
        String msg= "";
        msg+= "Thông tin";
        msg += "\nTài khoản: " + edtTaiKhoan.getText().toString();
        msg+= "\nMật khảu: " + edtMatKhau.getText().toString();
        msg+= "\nSố điện thoại: " + edtSDT.getText().toString();
        msg+= "\nEmail: " + edtEmail.getText().toString();
        if(edtTaiKhoan.getText().toString().isEmpty()){
            showError(edtTaiKhoan, "Nhập tài khoản");
        }else if(edtMatKhau.getText().toString().isEmpty()){
            showError(edtMatKhau, "Nhập mật khẩu");
        } else if (edtSDT.getText().toString().isEmpty()) {
            showError(edtSDT, "Nhập số điện thoại");
        } else if (edtEmail.getText().toString().isEmpty()) {
            showError(edtEmail, "Nhập email");
        }
        else {
            tvThongTin.setText(msg);
            tvThongTin.setBackgroundColor(Color.GREEN);
        }
    }
}
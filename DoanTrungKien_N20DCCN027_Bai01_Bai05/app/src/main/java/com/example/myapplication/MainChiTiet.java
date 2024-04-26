package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainChiTiet extends AppCompatActivity {

    EditText maSP, tenSP, giaSP;
    Button btnXoa, btnSua, btnBack;

    DatabaseSP databaseSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chi_tiet);

        setControl();
        setEvent();
    }

    private void setControl(){
        maSP= findViewById(R.id.edtProductCode);
        tenSP= findViewById(R.id.edtProductName);
        giaSP= findViewById(R.id.edtProductPrice);

        btnXoa= findViewById(R.id.btnDelete);
        btnSua= findViewById(R.id.btnUpdate);
        btnBack= findViewById(R.id.btnExit);
    }

    private void setEvent(){
        databaseSP= new DatabaseSP(this);
        SanPham sanPham= (SanPham) getIntent().getSerializableExtra("item");
        maSP.setText(sanPham.getMaSP());
        tenSP.setText(sanPham.getTenSP());
        giaSP.setText(sanPham.getGiaSP());

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseSP.xoaDuLieu(sanPham);
                Toast.makeText(MainChiTiet.this, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sanPham.setTenSP(tenSP.getText().toString());
                sanPham.setGiaSP(giaSP.getText().toString());
                databaseSP.suaDuLieu(sanPham);
                Toast.makeText(MainChiTiet.this, "Sua Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
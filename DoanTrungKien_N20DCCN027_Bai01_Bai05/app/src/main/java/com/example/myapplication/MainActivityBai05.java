package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivityBai05 extends AppCompatActivity {

    EditText edtMaSP, edtTenSP, edtGiaSP;

    Spinner spLoaiSP;

    List<String> data_lsp= new ArrayList<>();
    List<SanPham> data_sp= new ArrayList<>();

    ArrayAdapter adapter_lsp;
    CustomAdapterSP adapter_sp;

    int index= -1;

    ImageView ivHinh;

    Button btnThem, btnXoa, btnSua, btnThoat;

    ListView lvDanhSach;

    DatabaseSP databaseSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bai05);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTao();
        databaseSP= new DatabaseSP(this);

        adapter_lsp= new ArrayAdapter(this, android.R.layout.simple_list_item_1, data_lsp);
        spLoaiSP.setAdapter(adapter_lsp);


        spLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spLoaiSP.getSelectedItem().equals("SamSung")){
                    ivHinh.setImageResource(R.drawable.samsung);
                }
                if(spLoaiSP.getSelectedItem().equals("Iphone")){
                    ivHinh.setImageResource(R.drawable.iphone);
                }
                if(spLoaiSP.getSelectedItem().equals("Nokia")){
                    ivHinh.setImageResource(R.drawable.nokia);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivityBai05.this, "Chua chon SP", Toast.LENGTH_SHORT).show();
            }
        });

        adapter_sp= new CustomAdapterSP(this, R.layout.layout_item_sp, data_sp);
        lvDanhSach.setAdapter(adapter_sp);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themDL();
                docDuLieu();
            }
        });

        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanPham sp= data_sp.get(position);
                edtMaSP.setText(sp.getMaSP());
                edtTenSP.setText(sp.getTenSP());
                edtGiaSP.setText(sp.getGiaSP());
                if(sp.getLoaiSP().equals("SamSung")){
                    spLoaiSP.setSelection(0);
                }
                if(sp.getLoaiSP().equals("Iphone")){
                    spLoaiSP.setSelection(1);
                }
                if(sp.getLoaiSP().equals("Nokia")){
                    spLoaiSP.setSelection(2);
                }
                index= position;
            }
        });

        lvDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                data_sp.remove(position);
                adapter_sp.notifyDataSetChanged();
                return false;
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                data_sp.remove(index);
//                adapter_sp.notifyDataSetChanged();
                SanPham sanPham= new SanPham();
                sanPham.setMaSP(edtMaSP.getText().toString());
                databaseSP.xoaDuLieu(sanPham);

                docDuLieu();
                Toast.makeText(MainActivityBai05.this, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SanPham sp = data_sp.get(index);
                SanPham sp= new SanPham();
                sp.setMaSP(edtMaSP.getText().toString());
                sp.setTenSP(edtTenSP.getText().toString());
                sp.setGiaSP(edtGiaSP.getText().toString());
                sp.setLoaiSP(spLoaiSP.getSelectedItem().toString());

                databaseSP.suaDuLieu(sp);
                docDuLieu();

                adapter_sp.notifyDataSetChanged();
                Toast.makeText(MainActivityBai05.this, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private  void themDL(){
        SanPham sp = new SanPham();
        sp.setMaSP(edtMaSP.getText().toString());
        sp.setTenSP(edtTenSP.getText().toString());
        sp.setGiaSP(edtGiaSP.getText().toString());
        sp.setLoaiSP(spLoaiSP.getSelectedItem().toString());
//        data_sp.add(sp);
//        adapter_sp.notifyDataSetChanged();

        databaseSP.themDuLieu(sp);
        Toast.makeText(this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
    }

    private void docDuLieu(){
        data_sp.clear();
        data_sp.addAll(databaseSP.docDuLieu());
        adapter_sp.notifyDataSetChanged();
    }




    private void khoiTao(){
        data_lsp.add("SamSung");
        data_lsp.add("Iphone");
        data_lsp.add("Nokia");
    }

    @Override
    protected void onResume() {
        super.onResume();
        docDuLieu();
    }

    private void setControl(){
        edtMaSP= findViewById(R.id.edtProductCode);
        edtTenSP= findViewById(R.id.edtProductName);
        edtGiaSP= findViewById(R.id.edtProductPrice);
        spLoaiSP= findViewById(R.id.spLoaiSP);
        ivHinh= findViewById(R.id.ivHinh);
        btnThem= findViewById(R.id.btnAdd);
        btnXoa= findViewById(R.id.btnDelete);
        btnSua= findViewById(R.id.btnUpdate);
        btnThoat= findViewById(R.id.btnExit);
        lvDanhSach= findViewById(R.id.lvDisplay);
    }

}
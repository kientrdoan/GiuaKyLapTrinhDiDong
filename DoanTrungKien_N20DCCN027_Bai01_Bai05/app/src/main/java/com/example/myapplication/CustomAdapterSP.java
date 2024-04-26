package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapterSP extends ArrayAdapter {
    Context context;

    int resource;

    List<SanPham> data;

    public CustomAdapterSP(@NonNull Context context, int resource,  List<SanPham> data) {
        super(context, resource, data);
        this.context= context;
        this.data= data;
        this.resource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(resource, null);
        ImageView ivHinh= convertView.findViewById(R.id.ivHinh);
        TextView tvTenSP= convertView.findViewById(R.id.tvTenSP);
        Button btnChiTiet= convertView.findViewById(R.id.btnChiTiet);

        SanPham sp= data.get(position);
        tvTenSP.setText(sp.getTenSP());
        if(sp.getLoaiSP().equals("SamSung")){
            ivHinh.setImageResource(R.drawable.samsung);
        }
        if(sp.getLoaiSP().equals("Iphone")){
            ivHinh.setImageResource(R.drawable.iphone);
        }
        if(sp.getLoaiSP().equals("Nokia")){
            ivHinh.setImageResource(R.drawable.nokia);
        }

        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MainChiTiet.class);
                intent.putExtra("item", sp);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

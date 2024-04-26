package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSP extends SQLiteOpenHelper {
    public DatabaseSP(@Nullable Context context) {
        super(context, "SanPham", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table tbSanPham(masp Text, tenSP Text, giaSP Text, loaiSP Text)";
        db.execSQL(sql);
    }

    public void themDuLieu(SanPham sanPham){
        SQLiteDatabase db= getWritableDatabase();
        String sql= "insert into tbSanPham values(?, ?, ?, ?)";
        db.execSQL(sql, new String[]{sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getGiaSP(), sanPham.getLoaiSP()});
    }


    public List<SanPham> docDuLieu(){
        SQLiteDatabase db = getReadableDatabase();
        String sql= "Select * from tbSanPham";
        List<SanPham> data= new ArrayList<>();
        Cursor cursor= db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                SanPham sanPham = new SanPham();
                sanPham.setMaSP(cursor.getString(0));
                sanPham.setTenSP(cursor.getString(1));
                sanPham.setGiaSP(cursor.getString(2));
                sanPham.setLoaiSP(cursor.getString(3));
                data.add(sanPham);
            }while (cursor.moveToNext());
        }

        return data;
    }

    public void xoaDuLieu(SanPham sanPham){
        SQLiteDatabase db= getWritableDatabase();
        String sql= "Delete from tbSanPham where masp=?";
        db.execSQL(sql, new String[]{sanPham.getMaSP()});
    }

    public void suaDuLieu(SanPham sanPham){
        SQLiteDatabase db= getWritableDatabase();
        String sql= "Update tbSanPham set tenSP = ?, giaSP = ?, loaiSP = ? where masp = ?";
        db.execSQL(sql, new String[]{sanPham.getTenSP(), sanPham.getGiaSP(), sanPham.getLoaiSP(), sanPham.getMaSP()});
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

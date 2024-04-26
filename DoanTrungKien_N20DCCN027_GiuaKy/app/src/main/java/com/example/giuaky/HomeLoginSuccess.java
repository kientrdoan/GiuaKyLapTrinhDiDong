package com.example.giuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeLoginSuccess extends AppCompatActivity {

    TextView tvThongTin;

    DatabaseLogin dbLogin;

    String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_login_success);
        dbLogin= new DatabaseLogin(this);
        userName = getIntent().getExtras().getString("username");
        setControll();
        hienThiThongTin();
    }

    private void setControll(){
        tvThongTin= (TextView) findViewById(R.id.tvThongTin);
    }

    private void hienThiThongTin(){
        User user = dbLogin.getUser(userName);
        String msg= "";
        msg += "Họ và tên: " + user.getHo() + " " + user.getTen();
        msg += "\n\nGiới tính: " + user.getGender();
        msg += "\n\nUsername: " + user.getUsername();
        msg += "\n\nSố điện thoại: " + user.getSdt();
        tvThongTin.setText(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mLogout) {
            Intent login= new Intent(HomeLoginSuccess.this, MainActivity.class);
            startActivity(login);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

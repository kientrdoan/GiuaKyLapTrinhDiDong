package com.example.giuaky;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class DangKyActivity extends AppCompatActivity {

    EditText edtLastName, edtFirstName, edtUsername, edtSignUpSDT, edtSignUpPassword, edtSignUpPasswordComfirm;

    ImageButton btnBack;

    RadioButton rdNam, rdNu;

    Button btnRegister;

    DatabaseLogin dbLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.dangky);
        dbLogin = new DatabaseLogin(this);

        setControl();
        setEvent();
    }

    private void setControl(){
        edtLastName= (EditText) findViewById(R.id.signup_last_name);
        edtFirstName= (EditText) findViewById(R.id.signup_first_name);
        rdNam= (RadioButton) findViewById(R.id.sign_up_nam);
        rdNu= (RadioButton) findViewById(R.id.sign_up_nu);
        edtUsername = (EditText) findViewById(R.id.signup_username);
        edtSignUpSDT= (EditText) findViewById(R.id.signup_email);
        edtSignUpPassword= (EditText) findViewById(R.id.signup_password);
        edtSignUpPasswordComfirm= (EditText) findViewById(R.id.signup_confirm);
        btnBack= (ImageButton) findViewById(R.id.back);
        btnRegister= (Button) findViewById(R.id.btn_register);
    }

    private void setEvent(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
                String ho, ten, userName, sdt, password, comfirmPassword;
                String gender= "";
                ho = edtLastName.getText().toString().trim();
                ten= edtFirstName.getText().toString().trim();
                userName= edtUsername.getText().toString().trim();
                sdt= edtSignUpSDT.getText().toString().trim();
                password= edtSignUpPassword.getText().toString().trim();
                comfirmPassword= edtSignUpPasswordComfirm.getText().toString().trim();

                if(ho.isEmpty() || ten.isEmpty() || userName.isEmpty() || sdt.isEmpty()
                   || password.isEmpty() || comfirmPassword.isEmpty()){
                    Toast.makeText(DangKyActivity.this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    return;
                }else if(rdNam.isChecked()== false && rdNu.isChecked() == false){
                    Toast.makeText(DangKyActivity.this, "Vui long chon gioi tinh", Toast.LENGTH_SHORT).show();
                    return;
                }else if(sdt.length() != 10){
                    Toast.makeText(DangKyActivity.this, "So dien thoai khong hop le", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!password.equals(comfirmPassword)){
                    Toast.makeText(DangKyActivity.this, "Mat khau xac thuc khong khop", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(rdNam.isChecked()){
                    gender = "Nam";
                }else if (rdNu.isChecked()){
                    gender = "Nu";
                }

                boolean checkExistUser = dbLogin.checkUsername(userName);
                if(!checkExistUser){
                    Intent vertify_layout= new Intent(DangKyActivity.this, VertifyOPT.class);
                    vertify_layout.putExtra("phone", edtSignUpSDT.getText().toString().trim());
                    vertify_layout.putExtra("ho", ho);
                    vertify_layout.putExtra("ten", ten);
                    vertify_layout.putExtra("gender", gender);
                    vertify_layout.putExtra("username", userName);
                    vertify_layout.putExtra("password", password);
                    startActivity(vertify_layout);
                    finish();
                }else {
                    Toast.makeText(DangKyActivity.this, "Username đã tồn tại", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(DangKyActivity.this);
                builder.setTitle("Exit");
                builder.setMessage("Are you want to exit");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent register_layout= new Intent(DangKyActivity.this, MainActivity.class);
                        startActivity(register_layout);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void showError(EditText edtText, String s){
        edtText.setError(s);
        edtText.requestFocus();
    }

    private void checkInput(){
        String ho, ten, userName, sdt, password, comfirmPassword;
        String gender= "";
        ho = edtLastName.getText().toString().trim();
        ten= edtFirstName.getText().toString().trim();
        userName= edtUsername.getText().toString().trim();
        sdt= edtSignUpSDT.getText().toString().trim();
        password= edtSignUpPassword.getText().toString().trim();
        comfirmPassword= edtSignUpPasswordComfirm.getText().toString().trim();
        if(ho.isEmpty()){
            showError(edtLastName, "Vui lòng nhập trường này");
        }
        else if(ten.isEmpty()){
            showError(edtFirstName, "Vui lòng nhập trường này");
        }
        else if(userName.isEmpty()){
            showError(edtUsername, "Vui lòng nhập trường này");
        }
        else if(sdt.isEmpty()){
            showError(edtSignUpSDT, "Vui lòng nhập trường này");
        }
        else if(password.isEmpty()){
            showError(edtSignUpPassword, "Vui lòng nhập trường này");
        }
        else if(comfirmPassword.isEmpty()){
            showError(edtSignUpPasswordComfirm, "Vui lòng nhập trường này");
        }
    }

}

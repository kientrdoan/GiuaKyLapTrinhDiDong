package com.example.giuaky;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VertifyOPT extends AppCompatActivity {

    FirebaseAuth myAuth;

    EditText codeInput1, codeInput2, codeInput3, codeInput4, codeInput5, codeInput6;

    String vertificationID;

    ImageButton btnBack;

    Button btnVertify, btnResendOPT;

    String ho, ten, gender, userName, phoneInput, password;
    String codeInput;

    DatabaseLogin dbLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.opt_vertify);
        dbLogin= new DatabaseLogin(this);
        myAuth= FirebaseAuth.getInstance();

        ho = getIntent().getExtras().getString("ho");
        ten = getIntent().getExtras().getString("ten");
        gender = getIntent().getExtras().getString("gender");
        userName = getIntent().getExtras().getString("username");
        phoneInput = getIntent().getExtras().getString("phone");
        password = getIntent().getExtras().getString("password");

        sendOtp(phoneInput);

        setControl();
        setEvent();
        setupOPTinputs();
    }
    private void setControl(){
        codeInput1= (EditText)findViewById(R.id.code_input_1);
        codeInput2= (EditText)findViewById(R.id.code_input_2);
        codeInput3= (EditText)findViewById(R.id.code_input_3);
        codeInput4= (EditText)findViewById(R.id.code_input_4);
        codeInput5= (EditText)findViewById(R.id.code_input_5);
        codeInput6= (EditText)findViewById(R.id.code_input_6);
        btnBack = (ImageButton) findViewById(R.id.back);
        btnVertify= (Button) findViewById(R.id.btnVertify);
        btnResendOPT= (Button) findViewById(R.id.btnResendOPT);
    }

    private void setEvent(){
        btnVertify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(codeInput1.getText().toString().trim().isEmpty() || codeInput2.getText().toString().trim().isEmpty() ||
                        codeInput3.getText().toString().trim().isEmpty() || codeInput4.getText().toString().trim().isEmpty() ||
                        codeInput5.getText().toString().trim().isEmpty() || codeInput6.getText().toString().trim().isEmpty()){
                    Toast.makeText(VertifyOPT.this, "Vui lòng nhập đầy đủ mã OPT", Toast.LENGTH_SHORT).show();
                    return;
                }

                codeInput= codeInput1.getText().toString().trim() +
                                   codeInput2.getText().toString().trim() +
                                   codeInput3.getText().toString().trim() +
                                   codeInput4.getText().toString().trim() +
                                   codeInput5.getText().toString().trim() +
                                   codeInput6.getText().toString().trim();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vertificationID, codeInput);

                signinbyCredentials(credential);
            }
        });

        btnResendOPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp(phoneInput);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(VertifyOPT.this);
                builder.setTitle("Exit");
                builder.setMessage("Are you want to exit");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent dangky_layout= new Intent(VertifyOPT.this, DangKyActivity.class);
                        startActivity(dangky_layout);
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

    private void sendOtp(String phoneNumber){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(myAuth)
                        .setPhoneNumber("+84"+ phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
//            signinbyCredentials(credential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VertifyOPT.this, "Vertification failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            vertificationID = s;
            Toast.makeText(VertifyOPT.this, "sent OPT success", Toast.LENGTH_SHORT).show();
        }
    };

    private void signinbyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(VertifyOPT.this, "Verity Succuess", Toast.LENGTH_SHORT).show();


                    boolean checkInsert= dbLogin.insertData(ho, ten, gender, userName, phoneInput, password);
                    if(checkInsert){
                        Toast.makeText(VertifyOPT.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(VertifyOPT.this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }


                    Intent layout_dangnhap= new Intent(VertifyOPT.this, MainActivity.class);
                    startActivity(layout_dangnhap);
                    finish();
                }else{
                    Toast.makeText(VertifyOPT.this, "Vertify Failed" + "\n" +vertificationID + "\n" + codeInput, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupOPTinputs(){
        codeInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeInput3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeInput4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeInput5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    codeInput6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}

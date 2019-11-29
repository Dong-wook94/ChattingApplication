package com.example.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.Data.AppDataInfo;
import com.example.chatapp.Data.User;
import com.example.chatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Intent intent;
    private Button loginBtn, joinBtn;
    public EditText idText, pwText;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private ProgressDialog progressDialog;
    //auto login
    private SharedPreferences auto;
    private CheckBox checkBoxAutoLogin;
    
    private String deviceToken;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        idText = (EditText) findViewById(R.id.emailEditText);
        pwText = (EditText) findViewById(R.id.passwordEditText);
        auto = getSharedPreferences(AppDataInfo.Login.key, AppCompatActivity.MODE_PRIVATE);

        checkBoxAutoLogin = findViewById(R.id.autoLogin);

        progressDialog = new ProgressDialog(this);

        loginBtn = findViewById(R.id.loginBtn);
        joinBtn = findViewById(R.id.joinBtn);
        loginBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        deviceToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()

                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        boolean checkState = auto.getBoolean(AppDataInfo.Login.checkbox,false);

        checkBoxAutoLogin.setChecked(checkState);
        if(checkBoxAutoLogin.isChecked()) {
            SharedPreferences.Editor autoLogin = auto.edit();
            autoLogin.putBoolean(AppDataInfo.Login.checkbox, true);
            autoLogin.commit();

            autoLogin();
        }
        else if(!checkBoxAutoLogin.isChecked()){
            SharedPreferences.Editor autoLogin = auto.edit();
            autoLogin.putBoolean(AppDataInfo.Login.checkbox, false);
            autoLogin.commit();
        }
    }
    private void autoLogin() {
        String loginId = auto.getString(AppDataInfo.Login.userID, null);
        String loginPwd = auto.getString(AppDataInfo.Login.userPwd,null);

        idText.setText(loginId);
        pwText.setText(loginPwd);
        Toast.makeText(LoginActivity.this, loginId + "계정으로 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
        loginUser(idText.getText().toString(),pwText.getText().toString());
        /*if(loginId != null && loginPwd != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                loginUser(idText.getText().toString(),pwText.getText().toString());

                break;
            case R.id.joinBtn:
                intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
        }
    }
    // 로그인
    private void loginUser(final String id, final String pw) {
        progressDialog.setMessage("로그인 중 입니다.. 기다려 주세요...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(id, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor autoLogin = auto.edit();
                            autoLogin.putBoolean(AppDataInfo.Login.checkbox,checkBoxAutoLogin.isChecked());
                            autoLogin.putString(AppDataInfo.Login.userID, id);
                            autoLogin.putString(AppDataInfo.Login.userPwd, pw);
                            autoLogin.commit();


                            updateFCMToken();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            // 로그인 실패
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //finish();
                        progressDialog.dismiss();

                    }
                });
    }
    private void updateFCMToken() {
        db.collection("users").document(idText.getText().toString())
                .update("deviceToken",deviceToken)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("firestore", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("firestore", "Error writing document", e);
                    }
                });

    }
}

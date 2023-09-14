package com.example.healthyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername,edEmail, edPassword, edConfPassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextAppFullName);
        edEmail = findViewById(R.id.editTextTextEmail);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfPassword = findViewById(R.id.editTextRegConfirmPassword);
        btn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textViewExistingUser);

    tv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    });

    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = edUsername.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            String confPassword = edConfPassword.getText().toString();

            DBdata db = new DBdata(getApplicationContext(),"Healthy me", null, 1);

            if (username.length()==0 || email.length()==0 || password.length()==0 || confPassword.length()==0){
                Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();

            }else {

                if (password.compareTo(confPassword)==0) {
                    if (isValid(password)) {
                        db.register(username,email,password);
                        Toast.makeText(getApplicationContext(), "Record inserted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Password Should contain 8 Characters, letter, number and special character", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Password did not Match", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });
    }
    public static boolean isValid(String passwordHere){
        int f1=0, f2=0, f3=0;
        if (passwordHere.length() < 8){
            return false;
        }else{
            for(int p =0; p < passwordHere.length(); p++){ //check if it's a letter
                if (Character.isLetter(passwordHere.charAt(p))){
                    f1=1;
                }
            }
            for (int r = 0; r < passwordHere.length(); r++){ //check is there any digits
                if (Character.isDigit(passwordHere.charAt(r))){
                    f2=1;
                }
            }
            for (int s = 0; s < passwordHere.length(); s++){
                char c = passwordHere.charAt(s); //check for special character
                if (c >= 33 && c <= 46 || c==64) //ascii letters
                    f3=1;
                }
            }
            if (f1==1 && f2==1 && f3==1) //check if all facts are met
                return true;
            return false;
        }

    }


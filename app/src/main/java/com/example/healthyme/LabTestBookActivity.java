package com.example.healthyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LabTestBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact,edpincode;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

        edname =findViewById(R.id.editTextLTBFullName);
        edaddress=findViewById(R.id.editTextLTBAddress);
        edcontact=findViewById(R.id.editTextLTBContact);
        edpincode=findViewById(R.id.editTextLTBPinCode);
        btnBooking=findViewById(R.id.buttonLTBBooking);

        Intent intent =getIntent();
        //passing first string parameters to intent
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));//divide by the column to split the text and price
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE); //like a small memory to store some data
                String username = sharedPreferences.getString("username","").toString();

                DBdata db = new DBdata(getApplicationContext(),"Healthy me", null, 1);//database object

                db.addOrder(username, edname.getText().toString(),edaddress.getText().toString(),
                        edcontact.getText().toString(),
                        Integer.parseInt(edpincode.getText().toString()), date.toString(),
                        time.toString(), Float.parseFloat(price[1].toString()),"lab");
                db.removeCart(username,"lab"); //remove data after the booking
                Toast.makeText(getApplicationContext(), "Your Booking is Done Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LabTestBookActivity.this,HomeActivity.class));
            }
        });

    }
}
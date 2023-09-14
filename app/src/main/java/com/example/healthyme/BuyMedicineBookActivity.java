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

public class BuyMedicineBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact,edpincode;
    Button btnBooking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        edname =findViewById(R.id.editTextBMBFullName);
        edaddress=findViewById(R.id.editTextBMBAddress);
        edcontact=findViewById(R.id.editTextBMBContact);
        edpincode=findViewById(R.id.editTextBMBPinCode);
        btnBooking=findViewById(R.id.buttonBMBBooking);

        Intent intent =getIntent();
        //passing first string parameters to intent
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));//divide by the column to split the text and price
        String date = intent.getStringExtra("date");


        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE); //like a small memory to store some data
                String username = sharedPreferences.getString("username","").toString();

                DBdata db = new DBdata(getApplicationContext(),"Healthy me", null, 1);//database object

                db.addOrder(username, edname.getText().toString(),edaddress.getText().toString(),edcontact.getText().toString(),
                        Integer.parseInt(edpincode.getText().toString()), date.toString(), "", Float.parseFloat(price[1].toString()),"medicine");
                db.removeCart(username,"medicine"); //remove data after the booking
                Toast.makeText(getApplicationContext(), "Your Booking is Done Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BuyMedicineBookActivity.this,HomeActivity.class));
            }
        });

    }
}
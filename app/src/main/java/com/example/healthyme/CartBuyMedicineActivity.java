package com.example.healthyme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartBuyMedicineActivity extends AppCompatActivity {

    ArrayList list;
    SimpleAdapter sa;
    HashMap<String,String> item;
    TextView tvTotal;
    ListView lst;

    private DatePickerDialog dateDialog;

    private Button btnDate, btnCheckout, btnBack;
    private String[][] packages={}; //two dimensional string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buy_medicine);

        btnDate = findViewById(R.id.buttonBMCDate);

        btnCheckout = findViewById(R.id.buttonBMCCheckout);
        btnBack = findViewById(R.id.buttonBMCBack);
        tvTotal=findViewById(R.id.textViewBMCTotalPrice);
        lst= findViewById(R.id.listViewBuyMedicineCart);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE); //like a small memory to store some data
        String username = sharedPreferences.getString("username","").toString();

        DBdata db = new DBdata(getApplicationContext(),"Healthy me", null, 1);

        float totalAmount =0;
        ArrayList dbData = db.getCartData(username,"medicine"); //to store the cart data. otype==lab
        //Toast.makeText(getApplicationContext(), ""+dbData, Toast.LENGTH_SHORT).show();

        packages = new String[dbData.size()][];// 1st string total length of array list
        for (int i=0;i<packages.length;i++){
            packages[i] = new String[5]; //2nd string 5 lab test packages
        }
        for (int i=0; i<dbData.size(); i++){
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$")); //split the string
            packages[i][0] =strData[0];//package name
            packages[i][4] ="Cost : "+strData[1]+"/="; //cost
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }
        tvTotal.setText("Total Cost :"+totalAmount); //view cost

        list = new ArrayList<>(); //to store the data in list view
        for (int i=0; i<packages.length; i++){
            item = new HashMap<String,String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", packages[i][4]);
            list.add(item);
        }
//adding data to the multi line view using adapter
        sa = new SimpleAdapter(this,list, R.layout.multi_lines,
                new String[]{"line1", "line2","line3","line4","line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}); //put, convert data from list to lines

        //view in the list view
        lst.setAdapter(sa);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartBuyMedicineActivity.this, BuyMedicineActivity.class));
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CartBuyMedicineActivity.this, BuyMedicineBookActivity.class);//object of the activity
                //put the data to that activity
                it.putExtra("price",tvTotal.getText());
                it.putExtra("date",btnDate.getText());

                startActivity(it);
            }
        });


        //initiate date
        initDatePicker();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });

    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                btnDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        dateDialog = new DatePickerDialog(this,style, dateSetListener,year,month,day);
        dateDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);

    }
}

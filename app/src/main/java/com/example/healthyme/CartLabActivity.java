package com.example.healthyme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {
    ArrayList list;
    SimpleAdapter sa;
    HashMap<String,String> item;
    TextView tvTotal;
    ListView lst;

    private DatePickerDialog dateDialog;
    private TimePickerDialog timeDialog;
    private Button btnDate, btnTime, btnCheckout, btnBack;
    private String[][] packages={}; //two diementional string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

        btnDate = findViewById(R.id.buttonCartDate);
        btnTime = findViewById(R.id.buttonCartTime);
        btnCheckout = findViewById(R.id.buttonCartCheckout);
        btnBack = findViewById(R.id.buttonCartBack);
        tvTotal=findViewById(R.id.textViewCartTotalPrice);
        lst= findViewById(R.id.listViewCart);


        SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE); //like a small memory to store some data
        String username = sharedPreferences.getString("username","").toString();

        DBdata db = new DBdata(getApplicationContext(),"Healthy me", null, 1);

        float totalAmount =0;
        ArrayList dbData = db.getCartData(username,"lab"); //to store the cart data. otype==lab
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
                startActivity(new Intent(CartLabActivity.this, LabTestActivity.class));
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CartLabActivity.this, LabTestBookActivity.class);//object of the activity
                //put the data to that activity
                it.putExtra("price",tvTotal.getText());
                it.putExtra("date",btnDate.getText());
                it.putExtra("time", btnTime.getText());
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
        //initiate time
        initTimePicker();
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
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
    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                btnTime.setText(hourOfDay+":"+minute);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timeDialog = new TimePickerDialog(this,style, timeSetListener,hrs,mins, true
        );
        dateDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);

    }
}
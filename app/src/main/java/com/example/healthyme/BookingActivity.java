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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    private DatePickerDialog dateDialog;
    private TimePickerDialog timeDialog;

    private Button dateBtn, timeBtn, btnBook, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        tv = findViewById(R.id.textViewAppTitle);
        ed1 =findViewById(R.id.editTextAppFullName);
        ed2 =findViewById(R.id.editTextAppContact);
        ed3 =findViewById(R.id.editTextAppAddress);
        ed4 =findViewById(R.id.editTextAppFees);

        dateBtn =findViewById(R.id.buttonAppDate);
        timeBtn =findViewById(R.id.buttonAppTime);
        btnBook=findViewById(R.id.buttonAppBook);
        btnBack=findViewById(R.id.buttonAppBack);

        ed1.setKeyListener(null); //making values non editable
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent(); //need to fetch data to intent
        String title = it.getStringExtra("text1");
        String fullname = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        //display all the data
        tv.setText(title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText("Consulant fees: "+fees+"/=");

        //initiate date
        initDatePicker();
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
    //initiate time
        initTimePicker();
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
            }
        });
     btnBack.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(BookingActivity.this,FindDoctorActivity.class));
         }
     });

     btnBook.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             SharedPreferences sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE); //like a small memory to store some data
             String username = sharedPreferences.getString("username","").toString();

             DBdata db = new DBdata(getApplicationContext(),"Healthy me", null, 1);
            if (db.CheckAppointmentExists(username,title+"=>",address,contact, 
                    dateBtn.getText().toString(), timeBtn.getText().toString())==1){
                Toast.makeText(getApplicationContext(), "Appointment Already Booked", Toast.LENGTH_SHORT).show();
            }else{
                db.addOrder(username,title+"=>",address,contact,0,
                        dateBtn.getText().toString(), timeBtn.getText().toString(),
                        Float.parseFloat(fees),"appointment");
                Toast.makeText(getApplicationContext(), "Your Appointment is Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BookingActivity.this,HomeActivity.class));
            }

         }
     });
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                dateBtn.setText(dayOfMonth+"/"+month+"/"+year);
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
                timeBtn.setText(hourOfDay+":"+minute);
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
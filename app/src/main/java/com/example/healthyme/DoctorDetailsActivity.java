package com.example.healthyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
//creating array list of doctors available foe=r each type
    private String[][]doctor_details1 ={
            {"Doctor Name : Emily Johnson", "Hospital Address : 123 Main Street, Cityville, State", "Experience : 5years","Contact : 94758982690", "800"},
            {"Doctor Name : Michael Patel", "Hospital Address :  456 Oak Avenue, Townsville", "Experience : 4years","Contact : 94709862556", "900"},
            {"Doctor Name : Sarah Thompson", "Hospital Address :  789 Elm Road, Sunnyville", "Experience : 2years","Contact : 94735870927", "700"},
            {"Doctor Name : David Lee", "Hospital Address : 987 Maple Lane, Metropolis", "Experience : 8years","Contact : 94747975212", "1000"},
            {"Doctor Name : Jessica Davis", "Hospital Address :  321 Lakeview Drive, Laketown", "Experience : 12years","Contact : 94718562936", "1500"}
    };
    private String[][]doctor_details2 ={
            {"Doctor Name : Christopher Wilson", "Hospital Address : 876 Meadow Way, Greenfield", "Experience : 15years","Contact : 94709855463", "2000"},
            {"Doctor Name : Samantha Adams", "Hospital Address : 654 River Street, Riverdale, ", "Experience : 8years","Contact : 947124365428", "1000"},
            {"Doctor Name : Benjamin Taylor", "Hospital Address : 210 West Avenue, Westville", "Experience : 3years","Contact : 94775633402", "800"},
            {"Doctor Name : Olivia Mitchell", "Hospital Address :  987 Central Square, Centerville, State", "Experience : 4years","Contact : 94756476252", "1100"},
            {"Doctor Name : Ethan Roberts", "Hospital Address : 543 Hilltop Road, Hillside, State", "Experience : 9years","Contact : 94756748366", "1400"}
    };

    private String[][]doctor_details3 ={
            {"Doctor Name :  James Wilson", "Hospital Address : 543 Pine Street, Graceville, State", "Experience : 2years","Contact : 9475898269", "700"},
            {"Doctor Name : Harper Thompson", "Hospital Address : 987 Sunrise Avenue, Sunville", "Experience : 20years","Contact : 94777976563", "2500"},
            {"Doctor Name : Logan Davis", "Hospital Address : 789 Central Street, Central City", "Experience : 16years","Contact : 94716472541", "1500"},
            {"Doctor Name : Chloe Adams", "Hospital Address :  654 Maple Lane, Maple Grove", "Experience : 8years","Contact : 94718764557", "1000"},
            {"Doctor Name :  Samuel Mitchell", "Hospital Address :  876 Hillside Road, Hillville", "Experience : 3years","Contact : 94788756544", "900"}
    };

    private String[][]doctor_details4 ={
            {"Doctor Name : Sophia Roberts", "Hospital Address :  210 Riverside Avenue, Rivertown", "Experience : 10years","Contact : 94757689947", "1500"},
            {"Doctor Name : Ethan Nelson", "Hospital Address : 321 Oakwood Drive, Oakville", "Experience : 7years","Contact : 94777776544", "1200"},
            {"Doctor Name :  Olivia Cooper", "Hospital Address : 987 Westside Street, Westville, State", "Experience : 14years","Contact : 94786453728", "1800"},
            {"Doctor Name : William Anderson", "Hospital Address :  876 Cityscape Road, Cityville", "Experience : 5years","Contact : 94709877765", "1000"},
            {"Doctor Name : Ava Martinez", "Hospital Address : 543 Meadowview Lane, Meadowville", "Experience : 18years","Contact : 94765482918", "2000"}
    };

    private String[][]doctor_details5 ={
            {"Doctor Name : James Wilson", "Hospital Address : 123 Hope Street, Hopetown", "Experience : 3years","Contact : 94779583623", "900"},
            {"Doctor Name : Harper Thompson", "Hospital Address : 456 Serene Avenue, Tranquilville", "Experience : 9years","Contact : 94713645289", "1400"},
            {"Doctor Name : Logan Davis", "Hospital Address : 987 Maplewood Lane, Mapleville", "Experience : 12years","Contact : 94723561588", "1600"},
            {"Doctor Name :  Chloe Adams", "Hospital Address : 321 Oakwood Drive, Oakville", "Experience : 5years","Contact : 94731158942", "1100"},
            {"Doctor Name : Samuel Mitchell", "Hospital Address : 789 Central Street, Central City", "Experience : 22years","Contact : 94759886613", "2000"}
    };
    TextView tv;
    Button btn;

    String[][] doctor_details = {}; //array to store particular doctor details

    ArrayList list;
    SimpleAdapter sa;
    HashMap<String,String> item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewDDTitle);
        btn = findViewById(R.id.buttonLTBack);

        Intent in = getIntent();
        String title = in.getStringExtra("title");
        tv.setText(title); //get the title of physician form find doctor

        if(title.compareTo("Family Physician")==0) //check if the title is the same
            doctor_details = doctor_details1; //assign the array list
        else
        if(title.compareTo("Dietician")==0)
            doctor_details = doctor_details2;
        else
        if(title.compareTo("Dentist")==0)
            doctor_details = doctor_details3;
        else
        if(title.compareTo("Surgeon")==0)
            doctor_details = doctor_details4;
        else
            doctor_details = doctor_details5;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i=0; i<doctor_details.length; i++){
            item = new HashMap<String,String>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Consult Fee: "+doctor_details[i][4]+"/=");
            list.add(item);
        }

        sa = new SimpleAdapter(this,list, R.layout.multi_lines,
                new String[]{"line1", "line2","line3","line4","line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}); //put, convert data from list to lines

    ListView lst = findViewById(R.id.listViewCart);
    lst.setAdapter(sa);

    lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent it = new Intent(DoctorDetailsActivity.this,BookingActivity.class);

            it.putExtra("text1", title);
            it.putExtra("text2", doctor_details[position][0]);
            it.putExtra("text3", doctor_details[position][1]);
            it.putExtra("text4", doctor_details[position][3]);
            it.putExtra("text5", doctor_details[position][4]);
            startActivity(it);
        }
    });
    }




}


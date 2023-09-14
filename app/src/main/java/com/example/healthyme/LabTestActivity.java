package com.example.healthyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity { //hard code strings
    private String[][] packages = {
            {"Package 1: Full Body Checkup", "","","","9500"},
            {"Package 2: Cardiac Risk Assessment", "","","","13000"},
            {"Package 3: Digestive Health", "","","","5400"},
            {"Package 4: Diabetes Monitoring ", "","","","3200"},
            {"Package 5: Women's Health Screening", "","","","6800"},
    }; //we only need two form that multiline .xml

    private String[] package_details ={
            "Blood test\n" +
                    "Blood count\n" +
                    "Urinalysis\n" +
                    "Cholesterol test\n" +
                    "CT scan\n" +
                    "Biopsy\n",
                    "Electrocardiography\n" +
                    "Magnetic resonance imaging\n" +
                    "Electromyography\n"+
                    "Liver function tests\n" +
                    "Ultrasonography\n"+
                            "Electroencephalography\n" +
                    "Endoscopy\n",
                    "Hemoglobin A1C\n" +
                    "Genetic testing\n" +
                    "Pap smear\n",
                    "Thyroid function tests\n" +
                    "Colonoscopy\n" +
                    "Blood sugar test\n" +
                    "Amniocentesis\n" +
                    "Chest radiograph\n",
                    "Cardiac stress test\n" +
                    "Positron emission tomography\n" +
                    "Echocardiogram"
    }; //5 string details as in package

    ArrayList list;
    SimpleAdapter sa;
    HashMap<String,String> item;
    Button btnGoToCart, btnBack;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        btnGoToCart = findViewById(R.id.buttonLTGoToCart);
        btnBack = findViewById(R.id.buttonLTBack);
        listView = findViewById(R.id.listViewLT);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i=0; i<packages.length; i++){
            item = new HashMap<String,String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total"+packages[i][4]+"/=");
            list.add(item);
    }

        sa = new SimpleAdapter(this,list, R.layout.multi_lines,
                new String[]{"line1", "line2","line3","line4","line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}); //put, convert data from list to lines

        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(LabTestActivity.this,LabTestDetailsActivity.class);

                it.putExtra("text1", packages[position][0]);
                it.putExtra("text2", package_details[position]);
                it.putExtra("text3", packages[position][4]);

                startActivity(it);
            }
        });

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestActivity.this, CartLabActivity.class));
            }
        });
    }
}
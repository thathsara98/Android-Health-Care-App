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

public class BuyMedicineActivity extends AppCompatActivity {

    private String[][] packages = {
            {"Amitriptyline", "", "", "", "330"},
            {"Allopurinol", "", "", "", "200"},
            {"Diclofenac", "", "", "", "340"},
            {"Humira", "", "", "", "170"},
            {"Alendronate", "", "", "", "120"},
            {"Cephalexin", "", "", "", "280"},
            {"Morphine", "", "", "", "230"},
            {"Diazepam", "", "", "", "310"}

    };
    private String[] package_details = {
            "Treat major depressive disorder, a variety of pain syndromes such as neuropathic pain, fibromyalgia, migraine and tension headaches\n",
                    "Prevent gout, prevent specific types of kidney stones and for the high uric acid levels that can occur with chemotherapy.\n",
                    "Treat pain and inflammatory diseases such as gout.\n",
                    "Treat rheumatoid arthritis, psoriatic arthritis, ankylosing spondylitis, Crohn's disease, ulcerative colitis, plaque psoriasis, hidradenitis suppurativa, uveitis, and juvenile idiopathic arthritis. \n",
                    "Treat and prevent osteoporosis (a condition in which the bones become thin and weak and break easily) in women who have undergone menopause (''change of life,'' end of menstrual periods) and to treat osteoporosis in men.\n",
                    "It kills gram-positive and some gram-negative bacteria by disrupting the growth of the bacterial cell wall. Cefalexin is a beta-lactam antibiotic within the class of first-generation cephalosporins.\n",
                    "It is mainly used as a pain medication, and is also commonly used recreationally, or to make other illicit opioids.\n",
                    "It is commonly used to treat a range of conditions, including anxiety, seizures, alcohol withdrawal syndrome, muscle spasms, insomnia, and restless legs syndrome.\n"

    };

    ArrayList list;
    SimpleAdapter sa;
    HashMap<String, String> item;
    ListView listView;
    Button btnBack2, btnGoToCart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        listView = findViewById(R.id.listViewBuyMedicine);
        btnBack2 = findViewById(R.id.buttonBMBack);
        btnGoToCart2 = findViewById(R.id.buttonBMGoToCart);

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });
        btnGoToCart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total Cost: " + packages[i][4] + "/=");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}); //put, convert data from list to lines

        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);

                it.putExtra("text1", packages[position][0]);
                it.putExtra("text2", package_details[position]);
                it.putExtra("text3", packages[position][4]);

                startActivity(it);
            }
        });
        btnGoToCart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class));
            }
        });
    }
}
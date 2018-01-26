package com.example.narathorn.apartmentkunparparradnarjan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView totalText, electricText, waterText, costText;
    Button detailBtn;
    DatabaseReference dbUtils, dbSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Components
        totalText = (TextView) findViewById(R.id.totalText);
        electricText = (TextView) findViewById(R.id.electricText);
        waterText = (TextView) findViewById(R.id.waterText);
        costText = (TextView) findViewById(R.id.costText);
        detailBtn = (Button) findViewById(R.id.detailBtn);

        //Get Current Year
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);

        //SharedPref
        SharedPreferences shared = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        String roomNumber = shared.getString("roomNumber", "000");

        //Database References
        dbUtils = FirebaseDatabase.getInstance().getReference("BILL").child(roomNumber).child(year).child(month);
        dbSetting = FirebaseDatabase.getInstance().getReference("SETTING");

        //Query data from database
        setCost();

        //Clicking
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(i);
            }
        });
    }

    public void setCost() {
        dbUtils.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot utilsSnapshot) {
                dbSetting.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot settingSnapshot) {
                        //Initialize values
                        UnitGetter unitGetter = utilsSnapshot.getValue(UnitGetter.class);
                        SettingGetter settingGetter = settingSnapshot.getValue(SettingGetter.class);
                        int electricCost = (unitGetter.getElectricNew() - unitGetter.getElectricOld()) * settingGetter.getElectric();
                        int waterCost = (unitGetter.getWaterNew() - unitGetter.getWaterOld()) * settingGetter.getWater();
                        int totalCost = electricCost + waterCost + settingGetter.getRoomPrice();

                        //Set TextView
                        totalText.setText(String.valueOf(totalCost)+" บาท");
                        electricText.setText(String.valueOf(electricCost)+" บาท");
                        waterText.setText(String.valueOf(waterCost)+" บาท");
                        costText.setText(String.valueOf(settingGetter.getRoomPrice())+" บาท");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

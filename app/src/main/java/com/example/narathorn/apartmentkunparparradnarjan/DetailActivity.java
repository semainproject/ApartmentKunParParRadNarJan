package com.example.narathorn.apartmentkunparparradnarjan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    TextView oldElec, newElec, unitElec, priceElec;
    TextView oldWater, newWater, unitWater, priceWater;
    DatabaseReference dbUtils, dbSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Components
        oldElec = (TextView) findViewById(R.id.oldElec);
        newElec = (TextView) findViewById(R.id.newElec);
        unitElec = (TextView) findViewById(R.id.unitElec);
        priceElec = (TextView) findViewById(R.id.priceElec);
        oldWater = (TextView) findViewById(R.id.oldWater);
        newWater = (TextView) findViewById(R.id.newWater);
        unitWater = (TextView) findViewById(R.id.unitWater);
        priceWater = (TextView) findViewById(R.id.priceWater);

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

                        //Set TextView

                        //Electric
                        oldElec.setText(String.valueOf(unitGetter.getElectricOld()));
                        newElec.setText(String.valueOf(unitGetter.getElectricNew()));
                        unitElec.setText(String.valueOf(unitGetter.getElectricNew() - unitGetter.getElectricOld()) + " หน่วย");
                        priceElec.setText(String.valueOf(electricCost) + " บาท");

                        //Water
                        oldWater.setText(String.valueOf(unitGetter.getWaterOld()));
                        newWater.setText(String.valueOf(unitGetter.getWaterNew()));
                        unitWater.setText(String.valueOf(unitGetter.getWaterNew() - unitGetter.getWaterOld()) + " หน่วย");
                        priceWater.setText(String.valueOf(waterCost) + " บาท");
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

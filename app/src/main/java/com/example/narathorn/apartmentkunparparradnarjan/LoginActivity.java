package com.example.narathorn.apartmentkunparparradnarjan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText roomNumber,passWord;
    Button signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("INFORMATION");
        roomNumber = (EditText)findViewById(R.id.roomNumber);
        passWord = (EditText) findViewById(R.id.passWord);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        SharedPreferences shared = getSharedPreferences("login_pref",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();
        boolean booleanValue = shared.getBoolean("isLogin", false);
        if(booleanValue == true){
            Toast.makeText(LoginActivity.this,"You are loging in",Toast.LENGTH_LONG).show();
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(roomNumber.getText().equals(""))) {
                    myRef.child(roomNumber.getText().toString()).child("PASSWORD").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String password = dataSnapshot.getValue(String.class);
                            if(password.equals(passWord.getText().toString())){
                                //Intent i = new Intent(LoginActivity.this,)
                                Toast.makeText(LoginActivity.this,"Thank you for loging in",Toast.LENGTH_LONG).show();
                                editor.putString("roomNumber",roomNumber.getText().toString());
                                editor.putString("password", passWord.getText().toString());
                                editor.putBoolean("isLogin", true);
                                editor.commit();
                            }else{
                                Toast.makeText(LoginActivity.this,"Your Password Is Not Correct",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"5555",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

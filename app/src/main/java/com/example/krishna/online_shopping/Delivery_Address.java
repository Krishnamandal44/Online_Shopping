package com.example.krishna.online_shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delivery_Address extends AppCompatActivity {
    EditText input_pin,input_address,input_landmark,input_city,input_state,input_phon;
    Button canc,save;
    private SQLiteDatabase db;
    String emailid;
    EditText editemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery__address);
        createdatabae();
        editemail=(EditText)findViewById(R.id.editemail);
        input_pin=(EditText)findViewById(R.id.input_pin);
        input_address=(EditText)findViewById(R.id.input_address);
        input_landmark=(EditText)findViewById(R.id.input_landmark);
        input_city=(EditText)findViewById(R.id.input_city);
        input_state=(EditText)findViewById(R.id.input_state);
        input_phon=(EditText)findViewById(R.id.input_phon);
        canc=(Button)findViewById(R.id.canc);
        save=(Button)findViewById(R.id.save);
        emailid=getIntent().getExtras().getString("Email_id");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pin=input_pin.getText().toString();
                String address=input_address.getText().toString();
                String landmark=input_landmark.getText().toString();
                String city=input_city.getText().toString();
                String state=input_state.getText().toString();
                String phon= input_phon.getText().toString();
                String email_id=editemail.getText().toString();
                String validname = "[a-zA-Z]+\\.?";

                if (address.equals("")||address.length()<5)
                {
                    input_address.setError("Enter valid address");
                    return;
                }
                if(pin.equals("")||pin.length()<6)
                {
                    input_pin.setError("Enter valid pin");
                    return;
                }
                if (landmark.equals("")||landmark.length()<6)
                {
                    input_landmark.setError("Enter landmark");
                    return;
                }
                if (city.equals("")||!city.matches(validname)||city.length()<5)
                {
                    input_city.setError("Enter valid city");
                    return;
                }
                if (state.equals("")||!state.matches(validname)||state.length()<2)
                {
                    input_state.setError("Enter correct state");
                    return;
                }
                if (phon.equals("")||phon.length()<10)
                {
                    input_phon.setError("Enter valid phone number");
                    return;
                }
                if(emailid.equals(email_id)) {


                    String in = "insert into delivery(email,pin,address,landmark,city,state,phon)values('" + email_id + "','" + pin + "','" + address + "','" + landmark + "','" + city + "','" + state + "','" + phon + "')";
                    db.execSQL(in);
                    //Toast.makeText(Delivery_Address.this, "user details save" + emailid, Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(new Intent(Delivery_Address.this, Login.class));
                }
                else
                {
                    Toast.makeText(Delivery_Address.this, "Enter your register mailid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
   protected  void createdatabae()
   {
       db=openOrCreateDatabase("shopping", Context.MODE_PRIVATE,null);
       db.execSQL("create table if not exists delivery(id integer primary key autoincrement not null,email varchar,pin number,address varchar,landmark varchar,city varchar,state varchar,phon number);");

   }
}


package com.example.krishna.online_shopping;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_delivery_address extends AppCompatActivity {
    private SQLiteDatabase db;
    String ee_id;
EditText editemail,editpin,editphon,editstate,editlmark,editadd,editcity;
    Button save,reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        opendatabase();
        setContentView(R.layout.activity_edit_delivery_address);
        editemail=(EditText)findViewById(R.id.editemail);
        editpin=(EditText)findViewById(R.id.editpin);
        editphon=(EditText)findViewById(R.id.editphon);
        editstate=(EditText)findViewById(R.id.editstate);
        editlmark=(EditText)findViewById(R.id.editlmark);
        editadd=(EditText)findViewById(R.id.editadd);
        editcity=(EditText)findViewById(R.id.editcity);
        save=(Button)findViewById(R.id.save);
        reset=(Button)findViewById(R.id.reset);
        editemail.setFocusable(false);
        show();
reset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        editstate.setText("");
        editlmark.setText("");
        editphon.setText("");
        editcity.setText("");
        editadd.setText("");
        editpin.setText("");
    }
});
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid=editemail.getText().toString();
                String uadd=editadd.getText().toString();
                String ucity=editcity.getText().toString();
                String ustate=editstate.getText().toString();
                String upin=editpin.getText().toString();
                String uphno=editphon.getText().toString();
                String umark=editlmark.getText().toString();

                if(uid.equals("")||uadd.equals("")||ucity.equals("")||ustate.equals("")||upin.equals("")||uphno.equals("")||umark.equals(""))
                {
                    Toast.makeText(Edit_delivery_address.this, "unable to update for blank data", Toast.LENGTH_SHORT).show();
                }
                else {
                    String upa="update delivery set pin='"+upin+"',address='"+uadd+"',landmark='"+umark+"',city='"+ucity+"',state='"+ustate+"',phon='"+uphno+"' where id="+ee_id+"";
                    db.execSQL(upa);
                    Toast.makeText(Edit_delivery_address.this, "Sucessfully update records", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i=new Intent(Edit_delivery_address.this,MainActivity.class);
                    startActivity(i);

                }
            }
        });
    }
    protected void opendatabase()
    {
        db=openOrCreateDatabase("shopping", Context.MODE_PRIVATE,null);
    }
    protected void show()
    {
        ee_id=getIntent().getExtras().getString("pid");
        String e_id=getIntent().getExtras().getString("pemail");
        String e_add=getIntent().getExtras().getString("padd");
        String e_pin=getIntent().getExtras().getString("ppin");
        String e_state=getIntent().getExtras().getString("pstate");
        String e_phno=getIntent().getExtras().getString("pphno");
        String e_mark=getIntent().getExtras().getString("plmark");
        String e_city=getIntent().getExtras().getString("pcity");
        editemail.setText(e_id);
        editadd.setText(e_add);
        editpin.setText(e_pin);
        editstate.setText(e_state);
        editphon.setText(e_phno);
        editlmark.setText(e_mark);
        editcity.setText(e_city);
    }
}

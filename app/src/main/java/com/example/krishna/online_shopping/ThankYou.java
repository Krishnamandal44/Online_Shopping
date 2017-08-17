package com.example.krishna.online_shopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThankYou extends AppCompatActivity {
    TextView tv_thank_you;
    Button bt_continue;
    String msg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        bt_continue=(Button)findViewById(R.id.bt_continue);
        tv_thank_you=(TextView)findViewById(R.id.tv_thank_you);
        msg=getIntent().getExtras().getString("MSG");
        tv_thank_you.setText(msg);
        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThankYou.this,MainActivity.class));
            }
        });
    }
}

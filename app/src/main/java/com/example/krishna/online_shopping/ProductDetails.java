package com.example.krishna.online_shopping;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    ImageView iv_image;
    TextView pro_name,pro_mprice,pro_wprice,pro_availablity;
    String spid="",scid="",siname="",smprice="",swprice="",savai="",simg="";
    Button bt_buy,bt_add;
    private SQLiteDatabase db;
    LinearLayout ln_back,ln_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        iv_image=(ImageView)findViewById(R.id.iv_image);
        pro_name=(TextView) findViewById(R.id.pro_name);
        pro_mprice=(TextView) findViewById(R.id.pro_mprice);
        pro_wprice=(TextView) findViewById(R.id.pro_wprice);
        pro_availablity=(TextView) findViewById(R.id.pro_availablity);
        bt_buy=(Button) findViewById(R.id.bt_buy);
        bt_add=(Button) findViewById(R.id.bt_add);
        ln_cart = (LinearLayout) findViewById(R.id.ln_cart);

        ln_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this,List_View_Show.class));

            }
        });
        ln_back = (LinearLayout) findViewById(R.id.ln_back);

        ln_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        simg=getIntent().getExtras().getString("IMAGE");
        Picasso.with(ProductDetails.this).load(simg).into(iv_image);
        spid=getIntent().getExtras().getString("PID");
        scid=getIntent().getExtras().getString("CID");
        siname=getIntent().getExtras().getString("NAME");
        smprice=getIntent().getExtras().getString("MPRICE");
        swprice=getIntent().getExtras().getString("WPRICE");
        savai=getIntent().getExtras().getString("AVAILABLE");
        pro_name.setText(siname);
        pro_mprice.setText(smprice);
        pro_wprice.setText(swprice);
        pro_availablity.setText(savai);
        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if()
               // startActivity(new Intent(ProductDetails.this,Login.class));
                Intent i=new Intent(ProductDetails.this,Buy.class);
                i.putExtra("pamount",swprice);
                startActivity(i);

            }
        });

        createDatabase();
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDB();
               // startActivity(new Intent(ProductDetails.this,Login.class));
                startActivity(new Intent(ProductDetails.this,List_View_Show.class));

            }
        });
    }
    protected void createDatabase(){
        db=openOrCreateDatabase("shopping", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists customer (id integer primary key autoincrement not null,Product_Id varchar,Category_Id varchar,Item_Name varchar,Market_Price varchar,Web_Price varchar,Availability varchar,Product_Image varchar);");
}

    protected void insertIntoDB(){
      /*  if(name.equals("") || m_price.equals("")|| w_price.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }*/

        String query ="insert into customer(Product_Id,Category_Id,Item_Name,Market_Price,Web_Price,Availability,Product_Image)values('"+spid+"','"+scid+"','"+siname+"','"+smprice+"','"+swprice+"','"+savai+"','"+simg+"');";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
    }


}

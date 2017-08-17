package com.example.krishna.online_shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class List_View_Show extends AppCompatActivity {
    int pri;
    Integer pric=0;
    ListView lv;
    TextView total;
   private static final String sql_sel="select * from customer";
    private Cursor c;
    private SQLiteDatabase db;
    ProgressDialog pd;
    cartmodel cartmodel;
    TextView add;
    ArrayList<cartmodel>arrayList=new ArrayList<>();
    String i_name="",m_price="",a_vail="",i_mg="",p_id="";
    String did="";
    EditText s_id;
    Button buy,bt_home;
int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        opendatabase();
        setContentView(R.layout.activity_list__view__show);
        lv=(ListView)findViewById(R.id.lv);
        add=(TextView)findViewById(R.id.add);
        buy=(Button)findViewById(R.id.bt_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(List_View_Show.this,Login.class));
                Intent i=new Intent(List_View_Show.this,Buy.class);
                i.putExtra("pamount",String.valueOf(pric));
                startActivity(i);
            }
        });
        TextView back=(TextView)findViewById(R.id.bt_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
            }
        });
        bt_home = (Button) findViewById(R.id.bt_home);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(List_View_Show.this,MainActivity.class));

            }
        });
        count();//add cart count
        c.moveToFirst();
        add.setText(String.valueOf(count));
        total=(TextView)findViewById(R.id.total);
        pd=new ProgressDialog(List_View_Show.this);
        pd.setMessage("Please Wait");
        pd.setTitle("Loading");
        pd.setCancelable(false);
        c=db.rawQuery(sql_sel,null);
        c.moveToFirst();
        //for total price calculate....
        check();
        new load().execute();
        c.moveToFirst();
    }
    protected void count()
    {
        c=db.rawQuery(sql_sel,null);
        c.moveToFirst();
        do{
            count++;
        }while (c.moveToNext());


    }
    protected void check()
    {
        c=db.rawQuery(sql_sel,null);
        c.moveToFirst();
        int icount = c.getInt(4);
        if(icount>0)
        {

            do{
                pri=c.getInt(4);
                pric=pric+pri;
            }while (c.moveToNext());
            //c.moveToFirst();
            //Toast.makeText(List_View_Show.this, ""+pric, Toast.LENGTH_SHORT).show();
            Intent i=new Intent(List_View_Show.this,ProductDetails.class);
            i.putExtra("padd",pric);
            total.setText(String.valueOf(pric));
        }

        else
        {
            Toast.makeText(List_View_Show.this, "PLEASE ADD ATLEAST ONE PRODUCT", Toast.LENGTH_SHORT).show();
        }
    }
    public class load extends AsyncTask<String,Integer,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            if(arrayList.size()>0)
            {
                lv.setAdapter(new myadapter());
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try{

                do{
                    p_id=c.getString(1);
                     i_name=c.getString(3);
                    m_price=c.getString(4);
                    a_vail=c.getString(6);
                    i_mg=c.getString(7);
                    cartmodel =new cartmodel();
                    cartmodel.setId(p_id);
                    cartmodel.setIname(i_name);
                    cartmodel.setMprice(m_price);
                    cartmodel.setAvail(a_vail);
                    cartmodel.setImg(i_mg);
                    arrayList.add(cartmodel);
                }while (c.moveToNext());
            }catch (Exception e){e.printStackTrace();}
            return null;
        }
    }

    public class myadapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {

            v=getLayoutInflater().inflate(R.layout.row_after_add_cart,parent,false);
            Button rem=(Button) v.findViewById(R.id.rem);
            rem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p_id=arrayList.get(position).getId();
                    String del="delete from customer where Product_Id="+p_id+"";
                    db.execSQL(del);
                    finish();
                    startActivity(new Intent(List_View_Show.this,List_View_Show.class));
                    Toast.makeText(List_View_Show.this, "Remove sucessfully", Toast.LENGTH_SHORT).show();
                }
            });
            TextView iname=(TextView)v.findViewById(R.id.iname);
            TextView mprice=(TextView)v.findViewById(R.id.mprice);
            TextView avail=(TextView)v.findViewById(R.id.avail);
            ImageView img=(ImageView)v.findViewById(R.id.img);
            iname.setText(arrayList.get(position).getIname());
            mprice.setText(arrayList.get(position).getMprice());
            //avail.setText(arrayList.get(position).getAvail());
            Picasso.with(getApplicationContext()).load(arrayList.get(position).getImg()).into(img);
            return v;
        }
    }



    protected void opendatabase()
    {
        db=openOrCreateDatabase("shopping", Context.MODE_PRIVATE,null);
  }



}

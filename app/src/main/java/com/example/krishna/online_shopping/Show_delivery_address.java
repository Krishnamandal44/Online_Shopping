package com.example.krishna.online_shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Show_delivery_address extends AppCompatActivity {
    ListView lv;
    private SQLiteDatabase db;
    String email_id;
    String sql_sel;
    //private static final String sql_sel="select * from delivery where email="+emailid+";";
    private Cursor c;
    ProgressDialog pd;
    DeliveryModel deliveryModel;
    ArrayList<DeliveryModel> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_delivery_address);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.4));
        opendatabase();
        lv=(ListView)findViewById(R.id.lv);
        pd=new ProgressDialog(Show_delivery_address.this);
        pd.setMessage("Please Wait");
        pd.setTitle("Loading");
        pd.setCancelable(false);

        //c.moveToFirst();
        new load().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
                Intent i=new Intent(Show_delivery_address.this,Edit_delivery_address.class);
                i.putExtra("pid",arrayList.get(position).getId());
                i.putExtra("pemail",arrayList.get(position).getMail());
                i.putExtra("padd",arrayList.get(position).getAdd());
                i.putExtra("ppin",arrayList.get(position).getPin());
                i.putExtra("pstate",arrayList.get(position).getState());
                i.putExtra("pphno",arrayList.get(position).getPhone());
                i.putExtra("plmark",arrayList.get(position).getLmark());
                i.putExtra("pcity",arrayList.get(position).getCity());
                startActivity(i);
            }
        });
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
            try {

                 email_id=(AppData.UN);
                //sql_sel = "select pin,address,landmark,city,state,phon from delivery where email=" +email_id;
                //sql_sel="select * from delivery";
                sql_sel="select * from delivery";
                c = db.rawQuery(sql_sel, null);
                c.moveToFirst();
                do {
                    String ppid=c.getString(0);
                    String idmail=c.getString(1);
                    String pin = c.getString(2);
                    String add = c.getString(3);
                    String lmark = c.getString(4);
                    String city = c.getString(5);
                    String state = c.getString(6);
                    String phno = c.getString(7);
                    if(idmail.equals(email_id)){
                        deliveryModel = new DeliveryModel();
                        deliveryModel.setId(ppid);
                        deliveryModel.setMail(idmail);
                        deliveryModel.setAdd(add);
                        deliveryModel.setPin(pin);
                        deliveryModel.setLmark(lmark);
                        deliveryModel.setCity(city);
                        deliveryModel.setState(state);
                        deliveryModel.setPhone(phno);
                        arrayList.add(deliveryModel);
                    }
                }while (c.moveToNext());
            }catch(Exception e){
                e.printStackTrace();
            }
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

            v=getLayoutInflater().inflate(R.layout.delivery_row,parent,false);
            TextView mail=(TextView)v.findViewById(R.id.mail);
            TextView add=(TextView)v.findViewById(R.id.add);

            TextView pin=(TextView)v.findViewById(R.id.pin);
            TextView lmark=(TextView)v.findViewById(R.id.lmark);
            TextView city=(TextView)v.findViewById(R.id.city);
            TextView state=(TextView)v.findViewById(R.id.state);
            TextView phno=(TextView)v.findViewById(R.id.phno);
            mail.setText(arrayList.get(position).getMail());
            add.setText(arrayList.get(position).getAdd());
            pin.setText(arrayList.get(position).getPin());
            lmark.setText(arrayList.get(position).getLmark());
            city.setText(arrayList.get(position).getCity());
            state.setText(arrayList.get(position).getState());
            phno.setText(arrayList.get(position).getPhone());
            return v;
        }
    }

    protected void opendatabase()
    {
        db=openOrCreateDatabase("shopping", Context.MODE_PRIVATE,null);
    }
}

package com.example.krishna.online_shopping;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetProduct extends AppCompatActivity {
    String PId="",url="";
    String pro_id="",pro_c_id="",pro_i_name="",pro_i_desc="",pro_m_price="",pro_w_price="",pro_availabity="",pro_image="";
    ListView lv;
    JSONParser jp;
    ProgressDialog pd;
    ProModel proModel;
    ArrayList<ProModel>arrayList=new ArrayList<>();
    LinearLayout ln_back,ln_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_product);
        PId=getIntent().getExtras().getString("PROID");
        lv=(ListView) findViewById(R.id.lv) ;
        ln_cart = (LinearLayout) findViewById(R.id.ln_cart);
        ln_back = (LinearLayout) findViewById(R.id.ln_back);
        ln_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ln_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetProduct.this,List_View_Show.class));

            }
        });
        url="http://220.225.80.177/onlineshoppingapp/show.asmx/GetProduct?catId="+PId;
        //Toast.makeText(this, ""+PId, Toast.LENGTH_SHORT).show();


        //JSONParser Object Creation
        jp=new JSONParser();

        //Progress Dialog Object Creation
        pd=new ProgressDialog(GetProduct.this);
        pd.setTitle("Loading.....");
        pd.setMessage("Please Wait..........");
        pd.setCancelable(false);
        new loaddata_prodetails().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(GetProduct.this,ProductDetails.class);
                i.putExtra("IMAGE",arrayList.get(position).getProimage());
                i.putExtra("PID",arrayList.get(position).getProid());
                i.putExtra("CID",arrayList.get(position).getProcid());
                i.putExtra("NAME",arrayList.get(position).getProiname());
                i.putExtra("MPRICE",arrayList.get(position).getPromprice());
                i.putExtra("WPRICE",arrayList.get(position).getProwprice());
                i.putExtra("AVAILABLE",arrayList.get(position).getProavailabity());
                startActivity(i);


            }
        });
    }
    public class loaddata_prodetails extends AsyncTask<String ,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                JSONObject jobj=jp.getJsonFromURL(url);
                JSONArray jrr=jobj.getJSONArray("Products");
                for(int i=0;i<jrr.length();i++){
                    JSONObject jsonObject=jrr.getJSONObject(i);
                    pro_id=jsonObject.getString("Product_Id");
                    pro_c_id=jsonObject.getString("Category_Id");
                    pro_i_name=jsonObject.getString("Item_Name");
                    pro_i_desc=jsonObject.getString("Item_Desc");
                    pro_m_price=jsonObject.getString("Market_Price");
                    pro_w_price=jsonObject.getString("Web_Price");
                    pro_availabity=jsonObject.getString("Availability");
                    pro_image=jsonObject.getString("Product_Image");

                    proModel=new ProModel();
                    proModel.setProid(pro_id);
                    proModel.setProcid(pro_c_id);
                    proModel.setProiname(pro_i_name);
                    proModel.setProidesc(pro_i_desc);
                    proModel.setPromprice(pro_m_price);
                    proModel.setProwprice(pro_w_price);
                    proModel.setProavailabity(pro_availabity);
                    proModel.setProimage(pro_image);
                    arrayList.add(proModel);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if(arrayList.size()>0){
                lv.setAdapter(new MyAdapter());

            }else {
                Toast.makeText(GetProduct.this,"please check internet connection",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }

    }

    public class MyAdapter extends BaseAdapter {

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
        public View getView(int position, View v, ViewGroup parent) {
            v=getLayoutInflater().inflate(R.layout.row_pro_item,parent,false);
            ImageView iv_pro_image=(ImageView)v.findViewById(R.id.iv_pro_image);
            TextView tv_pro_iname=(TextView)v.findViewById(R.id.tv_pro_iname);
            TextView tv_pro_mprice=(TextView)v.findViewById(R.id.tv_pro_mprice);
            TextView tv_pro_wprice=(TextView)v.findViewById(R.id.tv_pro_wprice);
            TextView tv_pro_availabilty=(TextView)v.findViewById(R.id.tv_pro_availabilty);

            tv_pro_iname.setText(arrayList.get(position).getProiname());
            tv_pro_mprice.setText(arrayList.get(position).getPromprice());
            tv_pro_wprice.setText(arrayList.get(position).getProwprice());
            tv_pro_availabilty.setText(arrayList.get(position).getProavailabity());
            Picasso.with(GetProduct.this).load(arrayList.get(position).getProimage()).into(iv_pro_image);
            return v;
        }
    }


}

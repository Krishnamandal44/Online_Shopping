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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetSubCat extends AppCompatActivity {
String PCatId="",url="";
    ListView lv;
    JSONParser jp;
    ProgressDialog pd;
    String c_id="",c_name="",c_image="";
    CatModel catModel;
    ArrayList<CatModel> arrayList=new ArrayList<>();
    LinearLayout ln_back,ln_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sub_cat);
        ln_cart = (LinearLayout) findViewById(R.id.ln_cart);
        ln_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetSubCat.this,List_View_Show.class));

            }
        });
        ln_back = (LinearLayout) findViewById(R.id.ln_back);

        ln_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        PCatId=getIntent().getExtras().getString("CATID");
        url="http://220.225.80.177/onlineshoppingapp/show.asmx/getsubcat?catide="+PCatId;
        lv=(ListView)findViewById(R.id.lv);


        //JSONParser Object Creation
        jp=new JSONParser();

        //Progress Dialog Object Creation
        pd=new ProgressDialog(GetSubCat.this);
        pd.setTitle("Loading..........");
        pd.setMessage("Please Wait..........");
        pd.setCancelable(false);

        //for loaddata from webservice
        new loaddata_subcat().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(GetSubCat.this,""+arrayList.get(position).getCid(),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GetSubCat.this,GetProduct.class).putExtra("PROID",arrayList.get(position).getCid()));
            }
        });
    }

    public class loaddata_subcat extends AsyncTask<String,Integer,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();  //Progrss Dialog On
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            if(arrayList.size()>0){
                lv.setAdapter(new MyAdapter());
            }else{
                Toast.makeText(GetSubCat.this, "please check internet connection", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                //fetch main JSON OBJECT
                JSONObject jobj=jp.getJsonFromURL(url);
                JSONArray jrr=jobj.getJSONArray("Category");        //Fetch Category Array
                for(int i=0;i<jrr.length();i++){                    //Start Loop for fetching data from Category Array
                    JSONObject jsonObject=jrr.getJSONObject(i);     //1st fetch JSONObject in Category Array loop dependent (0,1,2)
                    c_id=jsonObject.getString("Cat_Id");            //Fetch Cat_Id
                    c_name=jsonObject.getString("Cat_Name");        //Fetch Cat_Name
                    c_image=jsonObject.getString("Cat_Image");      //Fetch Cat_Image

                    catModel=new CatModel();                        //Create an Object of CatModel class
                    catModel.setCid(c_id);                          //set catId using model class, sameas catName, CatImage
                    catModel.setCname(c_name);
                    catModel.setCimage(c_image);

                    arrayList.add(catModel);                        //Add this model class object to arraylist
                }
            }catch (Exception e){e.printStackTrace();}
            return null;
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
            v=getLayoutInflater().inflate(R.layout.rowitem,parent,false);
            ImageView iv_cimage=(ImageView)v.findViewById(R.id.iv_cimage);
            TextView tv_cname=(TextView)v.findViewById(R.id.tv_cname);
            tv_cname.setText(arrayList.get(position).getCname());
            Picasso.with(GetSubCat.this).load(arrayList.get(position).getCimage()).into(iv_cimage);
            return v;
        }
    }

}

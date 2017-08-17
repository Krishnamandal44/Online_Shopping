package com.example.krishna.online_shopping;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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


public class MainActivity extends AppCompatActivity {

    final Context context=this;
    ListView lv;
    TextView tv_name;
    String name;
    JSONParser jp;
    ProgressDialog pd;
    String c_id="",c_name="",c_image="";
    String url="http://220.225.80.177/onlineshoppingapp/show.asmx/getcatagory?";
    CatModel catModel;
    ArrayList<CatModel>arrayList=new ArrayList<>();
    DrawerLayout mDrawerLayout;
    FragmentManager fm =getFragmentManager();
    NavigationFragment fragment;

    LinearLayout ln_menue,ln_cart;


    @Override

    public void onBackPressed() {



        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(context);
        alertdialogbuilder.setTitle("Confirm Exist");
        alertdialogbuilder.setMessage("Do you want to Exist?");
        alertdialogbuilder.setCancelable(false);//if click on the outside of alert then no out alert
        //alertdialogbuilder.setCancelable(true); // if click on the outside of alert then out alert
        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                //Toast.makeText(MainActivity.this, "Chandan(1370)", 50000).show();
                //finish();
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                dialog.cancel();


            }
        });

        AlertDialog alertdia=alertdialogbuilder.create();
        alertdia.show();
        //finish();
        //android.os.Process.killProcess(android.os.Process.myPid());



        //super.onBackPressed();

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lv);
        tv_name=(TextView)findViewById(R.id.tv_name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragment = (NavigationFragment)fm.findFragmentById(R.id.fragmentitem);
        ln_menue = (LinearLayout) findViewById(R.id.ln_menue);
        ln_cart = (LinearLayout) findViewById(R.id.ln_cart);
        name=getPreference("user","");
        tv_name.setText(name);
        ln_menue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        ln_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,List_View_Show.class));

            }
        });


        //JSONParser Object Creation
        jp=new JSONParser();

        //Progress Dialog Object Creation
        pd=new ProgressDialog(MainActivity.this);
        pd.setTitle("Loading");
        pd.setMessage("Please Wait..........");
        pd.setCancelable(false);

        //for loaddata from webservice
        new loaddata().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, ""+arrayList.get(position).getCid(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,GetSubCat.class).putExtra("CATID",arrayList.get(position).getCid()));
            }
        });
    }

    public class loaddata extends AsyncTask<String,Integer,String>{
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
                Toast.makeText(MainActivity.this, "please check internet connection", Toast.LENGTH_SHORT).show();
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
    public class MyAdapter extends BaseAdapter{

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
            Picasso.with(MainActivity.this).load(arrayList.get(position).getCimage()).into(iv_cimage);
            return v;
        }
    }
    public String getPreference(String key,String value)
    {
        String pref_value="";
        SharedPreferences sp1=getSharedPreferences("PREF",Context.MODE_PRIVATE);
        pref_value=sp1.getString(key, value);
        return  pref_value;
    }
}

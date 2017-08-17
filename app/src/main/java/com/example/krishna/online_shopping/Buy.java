package com.example.krishna.online_shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class Buy extends AppCompatActivity {
    EditText et_card_no,et_amount,et_cvv,et_email,et_expt_date;
    String card_no="",amount="",cvv="",email="",expt_date="";
    Button bt_pay;
    String url="";
    String msg="";
    JSONParser jp;
    ProgressDialog pd;
    TextView money;
    String s_val,p_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        jp=new JSONParser();
        pd=new ProgressDialog(Buy.this);
        pd.setTitle("Loading......");
        pd.setMessage("Please Wait.......");
        pd.setCancelable(false);

        et_card_no=(EditText)findViewById(R.id.et_card_no);
       money=(TextView) findViewById(R.id.amount);
        amount=getIntent().getExtras().getString("pamount");
         et_cvv=(EditText)findViewById(R.id.et_cvv);
        money.setText(amount);
      //  et_email=(EditText)findViewById(R.id.et_email);
        et_expt_date=(EditText)findViewById(R.id.et_expt_date);
        bt_pay=(Button)findViewById(R.id.bt_pay);
        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_no=et_card_no.getText().toString().trim();
                cvv=et_cvv.getText().toString().trim();
                expt_date=et_expt_date.getText().toString().trim();
                String id_mail=(AppData.UN);
                if(card_no.equals("")||cvv.equals("")||expt_date.equals(""))
                {
                    Toast.makeText(Buy.this, "Please Enter Filed", Toast.LENGTH_SHORT).show();
                }
                else if(card_no.length()<5)
                {
                    et_card_no.setError("Enter valid card number");
                }
                else if(cvv.length()<3)
                {
                    et_cvv.setError("Enter valid cvv number");
                }
                else if(expt_date.length()<4)
                {
                    et_expt_date.setError("Enter valid expiry date");
                }
                else
                {
                url="http://220.225.80.177/apptransaction/WebService.asmx/Transaction?cardNo="+card_no+"&cvvCode="+cvv+"&expdate="+expt_date+"&amount="+amount+"&emailid="+email;
                new buyproduct().execute();
                }
            }
        });

        try {
            s_val = getPreference("username", "");
            p_val=getPreference("pass","");
            if (s_val.equals("")||p_val.equals("")) {
                //Strore in AppData
                //appsdata.UN=username;
                finish();
                startActivity(new Intent(Buy.this,Login.class));
                //Toast.makeText(getApplicationContext(),s_val,Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class buyproduct extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            startActivity(new Intent(Buy.this,ThankYou.class).putExtra("MSG",msg));
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject jsonObject=jp.getJsonFromURL(url);
                JSONObject jobj=jsonObject.getJSONObject("Response");
                msg=jobj.getString("Messagetext");

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }
    public String getPreference(String key,String value)
    {
        String pref_value="";
        SharedPreferences sp1=getSharedPreferences("PREF", Context.MODE_PRIVATE);
        pref_value=sp1.getString(key, value);
        return  pref_value;
    }
}

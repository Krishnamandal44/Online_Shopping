package com.example.krishna.online_shopping;

import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by KRISHNA on 24-05-2017.
 */

public class NavigationFragment extends Fragment {

    LinearLayout lnfashion,ln_login,lnmainlayout;
    LinearLayout lncart;
    LinearLayout lnlogout;
    TextView tv_prof_name;
    String s_val,p_val,u_val,name="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.navigation_fragment, container, false);
        lnfashion=(LinearLayout)rootView.findViewById(R.id.lnfashion);
        lncart=(LinearLayout)rootView.findViewById(R.id.lncart);
        ln_login=(LinearLayout)rootView.findViewById(R.id.ln_login);
        lnlogout=(LinearLayout)rootView.findViewById(R.id.lnlogout);
        lnmainlayout=(LinearLayout)rootView.findViewById(R.id.lnmainlayout);
        tv_prof_name=(TextView)rootView.findViewById(R.id.tv_prof_name);
        name=getPreference("user","");
        tv_prof_name.setText(name);
        lnmainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    s_val = getPreference("username", "");
                    p_val=getPreference("pass","");
                    u_val=getPreference("user","");

                    if (!s_val.equals("")||!p_val.equals("")) {
                        //Strore in AppData
                        AppData.UN=s_val;
                        AppData.uname=u_val;
                Intent i=new Intent(getActivity(),Show_delivery_address.class);
                i.putExtra("emailid",s_val);
                startActivity(i); }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        lnlogout.setOnClickListener(new View.OnClickListener() {
            Boolean f=false;
            @Override
            public void onClick(View v) {
                String nameget=getPreference("username","");
                String passget=getPreference("pass","");
                if(nameget.equals("")||passget.equals(""))
                {
                    Toast.makeText(getActivity(),"Please first Login", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        f = true;
                        Toast.makeText(getActivity(), "Account was sucessfully Logout", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //tv_prof_name.setText(nameget);
        lncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getActivity(), List_View_Show.class);
                getActivity().startActivity(myIntent);

            }
        });
        ln_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getActivity(), Login.class);
                getActivity().startActivity(myIntent);
            }
        });
        lnfashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(myIntent);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public String getPreference(String key,String value)
    {
        String pref_value="";
        SharedPreferences sp1=this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        pref_value=sp1.getString(key, value);
        return  pref_value;
    }
}
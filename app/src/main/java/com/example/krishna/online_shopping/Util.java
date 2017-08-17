package com.example.krishna.online_shopping;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {

	  private static NetworkInfo networkInfo;
 
		 
	  /**
	   * Is there internet connection
	   */
	  public static boolean isConnected(Context con) {
		  ConnectivityManager cm = (ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);
		    
		  try {
				networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			} catch (Exception e) {
				e.printStackTrace();
			}

		    // test for connection for WIFI
		    if (networkInfo != null
		            && networkInfo.isAvailable()
		            && networkInfo.isConnected()) {
		        return true;
		    }

		    networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		    // test for connection for Mobile
		    if (networkInfo != null
		            && networkInfo.isAvailable()
		            && networkInfo.isConnected()) {
		        return true;
		    }
		    
		    return false;
	  }
	  

}

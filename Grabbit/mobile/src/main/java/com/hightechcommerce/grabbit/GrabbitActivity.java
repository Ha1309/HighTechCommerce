package com.hightechcommerce.grabbit;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;


public class GrabbitActivity extends Activity {

    private class SalesforceRestClient {

        private Activity m_parent;
        private AsyncHttpClient m_client = new AsyncHttpClient();

        public SalesforceRestClient(Activity parent) {
            m_parent = parent;
        }

        public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            m_client.get(url, params, responseHandler);
        }

        public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            m_client.post(url, params, responseHandler);
        }

        public void getAccessToken() {
            String url = "https://login.salesforce.com/services/oauth2/token";
            RequestParams params = new RequestParams();
            params.put("grant_type", "password");
            params.put("client_id", "3MVG9Rd3qC6oMalUYpGoMBRpKDnGNj5eGHM3Sr5VgzWOGNSCa0RxtepSVr8ssFjvPpyInlqEXKTlG8nfryaqC");
            params.put("client_secret", "7263624499985818506");
            params.put("username", "thomas.s.kunze@me.com");
            params.put("password", "hightechcommerce123");

            JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline

                    Log.e("INFO:", "Connection established! 2");
                }

                @Override
                public void	onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString) {
                    Log.e("INFO:", "Connection established! 3");
                }


                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) {
                    Log.e("INFO:", "Connection failed! 1");
                }

                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONArray errorResponse) {
                    Log.e("INFO:", "Connection failed! 2");
                }

                @Override
                public void	onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                    Log.e("INFO:", "Statuscode: " + String.valueOf(statusCode) + " " + throwable.getMessage() + " " + errorResponse.toString());
                }



                @Override
                public void onStart() {
                    // called before request is started
                }

                /*@Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    System.out.println("Connection failed!");
                }*/

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            };

            post(url, params, responseHandler);

        }



        public void submitTake(int product) {

        }


    }

    SalesforceRestClient m_SalesforceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabbit);

        m_SalesforceModel = new SalesforceRestClient(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grabbit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connectToSalesforce(View v) {
        m_SalesforceModel.getAccessToken();
    }
}
